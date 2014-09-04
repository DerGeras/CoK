package de.minestar.cok.game;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.Constants.NBT;
import de.minestar.cok.game.profession.Profession;
import de.minestar.cok.game.worlddata.CoKGameWorldData;
import de.minestar.cok.tileentity.TileEntitySocket;
import de.minestar.cok.util.ChatSendHelper;
import de.minestar.cok.util.Color;
import de.minestar.cok.util.ItemStackHelper;
import de.minestar.cok.util.LogHelper;
import de.minestar.cok.util.PlayerHelper;

public class Team {

	private HashSet<UUID> playerUUIDs = new HashSet<UUID>();
	private LinkedList<Profession> availableProfessions = new LinkedList<Profession>();
	
	private static Random rand = new Random();
	
	private String name;
	private char color;
	private ChunkCoordinates spawnLocation;
	private CoKGame currentGame;
	
	public Team (String name, char color){
		this.name = name;
		this.color = color;
	}
	
	
	public Team (NBTTagCompound compound){
		readFromNBT(compound);
	}
	
	public Team(ByteBuf buf){
		readFromBuffer(buf);
	}
	
	public HashSet<CoKPlayer> getAllPlayers(){
		return CoKPlayerRegistry.getPlayersForUUIDs(playerUUIDs);
	}
	
	public char getColor(){
		return color;
	}
	
	public void setColor(char color){
		this.color = color;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getColorAsInt(){
		return color >= 97 ? color - 87 : color - 48;
	}
	
	public boolean hasPlayer(CoKPlayer player){
		return playerUUIDs.contains(player.getUUID());
	}
	
	public ChunkCoordinates getSpawnlocation(){
		return spawnLocation;
	}
	
	public void setSpawnCoordinates(ChunkCoordinates coords){
		this.spawnLocation = coords;
		if(spawnLocation != null){
			for(CoKPlayer player : getAllPlayers()){
				EntityPlayerMP playerEntity = player.getPlayerEntity();
				if(playerEntity != null){
					playerEntity.setSpawnChunk(spawnLocation, true, 0);				
				}
			}
		}
	}
	
	public boolean addPlayer(CoKPlayer player){
		if(CoKGameWorldData.data != null){
			CoKGameWorldData.data.markDirty();
		}
		if(player != null){
			if(player.getTeam() != null){
				player.getTeam().removePlayer(player);
			}
			player.setTeam(this);
			if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
				playerJoined(player);
				if(getGame() != null && getGame().isRunning() && spawnLocation != null){
					EntityPlayerMP playerEntity = player.getPlayerEntity();
					if(playerEntity != null){
						playerEntity.playerNetServerHandler.setPlayerLocation(
								spawnLocation.posX, spawnLocation.posY, spawnLocation.posZ, 0, 0);			
					}
				}
			}
			return playerUUIDs.add(player.getUUID());
		}
		return false;
	}
	
	public boolean addPlayer(String name){
		CoKPlayer player = CoKPlayerRegistry.getPlayerForName(name);		
		return addPlayer(player);
	}
	
	public boolean addPlayer(UUID uuid){
		CoKPlayer player = CoKPlayerRegistry.getPlayerForUUID(uuid);
		return addPlayer(player);
	}
	
	public boolean removePlayer(CoKPlayer player){
		if(CoKGameWorldData.data != null){
			CoKGameWorldData.data.markDirty();
		}
		if(player != null){
			playerLeft(player);
			player.setTeam(null);
		}
		return playerUUIDs.remove(player);
	}
	
	public boolean removePlayer(String name){
		CoKPlayer player = CoKPlayerRegistry.getPlayerForName(name);
		return removePlayer(player);
	}
	
	public boolean removePlayer(UUID uuid){
		CoKPlayer player = CoKPlayerRegistry.getPlayerForUUID(uuid);
		return removePlayer(player);
	}
	
	/**
	 * return all sockets correspondent to this team
	 * @return
	 */
	public HashSet<TileEntitySocket> getSockets(){
		return SocketRegistry.getSockets(getColorAsInt());
	}
	
	public CoKGame getGame(){
		return currentGame;
	}
	
	public void setGame(CoKGame game){
		this.currentGame = game;
	}
	
	/**
	 * should be called whenever a player enters the team/logs back in/revives
	 * @param player
	 */
	public void playerJoined(CoKPlayer player){
		if(spawnLocation != null){
			EntityPlayerMP playerEntity = player.getPlayerEntity();
			if(playerEntity != null){
				playerEntity.setSpawnChunk(spawnLocation, true, 0);				
			}
		}
		distributeProfessions();
	}
	
	/**
	 * should always be called when a player disconnects/dies/leaves the team
	 * @param player
	 */
	public void playerLeft(CoKPlayer player){
		if(player.getProfession() != null){
			availableProfessions.add(player.getProfession());
			player.setProfession(null);
			PlayerHelper.clearGivenItemsFromInventory(player.getPlayerEntity());
			distributeProfessions();
		}
	}
	
	/**
	 * called when the game starts
	 */
	public void onGameStart(){
		if(spawnLocation != null){
			for(CoKPlayer player : getAllPlayers()){
				EntityPlayerMP playerEntity = player.getPlayerEntity();
				if(playerEntity != null){
					PlayerHelper.clearInventory(playerEntity);
					playerEntity.setHealth(20.0f);
					playerEntity.getFoodStats().addStats(20, 20);
					playerEntity.setSpawnChunk(spawnLocation, true, 0);
					playerEntity.playerNetServerHandler.setPlayerLocation(
							spawnLocation.posX, spawnLocation.posY, spawnLocation.posZ, 0, 0);
				}
			}
		}
		availableProfessions.clear();
		availableProfessions.addAll(CoKGameRegistry.registeredProfessions);
		distributeProfessions();
	}
	
	/**
	 * called when the game stops or the team was defeated
	 */
	public void onGameStop(){
		for(CoKPlayer player : getAllPlayers()){
			player.setProfession(null);
			EntityPlayer playerEntity = PlayerHelper.getPlayerForUUID(player.getUUID());
			if(playerEntity != null){
				PlayerHelper.clearGivenItemsFromInventory(playerEntity);
			}
		}
	}
	
	/**
	 * distribute all available professions
	 */
	public void distributeProfessions(){
		if(currentGame != null && currentGame.isRunning()){
			while(availableProfessions.size() > 0){
				CoKPlayer candidate = getProfessionCandidate();
				if(candidate == null){
					break;
				}
				Profession p = availableProfessions.pop();
				candidate.setProfession(p);
				p.giveKit(candidate.getPlayerEntity(), this);
				ChatSendHelper.broadCastMessageToGame(currentGame, String.format("%s is now %s of team %s%s",
						candidate.getUserName(),
						p.getClassName(),
						Color.getColorCodeFromChar(this.color),
						this.name));
			}
		}
	}
	
	/**
	 * 
	 * @return an alive online player without profession
	 */
	public CoKPlayer getProfessionCandidate(){
		ArrayList<CoKPlayer> candidates = new ArrayList<CoKPlayer>();
		for(CoKPlayer player : getAllPlayers()){
			if(player.getProfession() == null){
				EntityPlayerMP playerEntity = player.getPlayerEntity();
				if(playerEntity != null && playerEntity.isEntityAlive()){
					candidates.add(player);
				}
			}
		}
		
		return candidates.size() < 1 ? null : candidates.get(rand.nextInt(candidates.size()));
	}
	
	/**
	 * remove all players from team
	 */
	public void clearPlayers(){
		for(UUID uuid : playerUUIDs){
			CoKPlayerRegistry.getPlayerForUUID(uuid).setTeam(null);
			CoKPlayerRegistry.getPlayerForUUID(uuid).setProfession(null);
		}
		playerUUIDs.clear();
	}
	
	public void readFromNBT(NBTTagCompound compound){
		this.name = compound.getString("name");
		this.color = Character.forDigit(compound.getInteger("color"), 16);
		//read coordinates
		if(compound.hasKey("spawnX")){
			int posX = compound.getInteger("spawnX");
			int posY = compound.getInteger("spawnY");
			int posZ = compound.getInteger("spawnZ");
			spawnLocation = new ChunkCoordinates(posX, posY, posZ);
		}
		//read players
		NBTTagList playerList = compound.getTagList("players", NBT.TAG_STRING);
		for(int i = 0; i < playerList.tagCount(); i++){
			String uuidString = playerList.getStringTagAt(i);
			addPlayer(CoKPlayerRegistry.getOrCreatPlayerForUUID(UUID.fromString(uuidString)));
		}
	}
	
	public void writeToNBT(NBTTagCompound compound){
		compound.setString("name", this.name);
		compound.setInteger("color", getColorAsInt());
		//write spawncoords 
		if(spawnLocation != null){
			compound.setInteger("spawnX", spawnLocation.posX);
			compound.setInteger("spawnY", spawnLocation.posY);
			compound.setInteger("spawnZ", spawnLocation.posZ);
		}
		//write spawncoords
		NBTTagList playerList = new NBTTagList();
		for(UUID uuid : playerUUIDs){
			playerList.appendTag(new NBTTagString(uuid.toString()));
		}
		compound.setTag("players", playerList);
	}
	
	
	public void writeToBuffer(ByteBuf buf){
		//write name
		buf.writeInt(name.length());
		buf.writeBytes(name.getBytes());
		//write spawnlocation
		buf.writeBoolean(spawnLocation != null);
		if(spawnLocation != null){
			buf.writeInt(spawnLocation.posX);
			buf.writeInt(spawnLocation.posY);
			buf.writeInt(spawnLocation.posZ);
		}
		//write players
		buf.writeInt(playerUUIDs.size());
		for(UUID uuid : playerUUIDs){
			//write UUID
			buf.writeInt(uuid.toString().length());
			buf.writeBytes(uuid.toString().getBytes());
		}
	}
	
	public void readFromBuffer(ByteBuf buf){
		//read name
		int nameLength = buf.readInt();
		this.name = new String(buf.readBytes(nameLength).array());
		//read spawnLocation
		boolean hasSpawnLocation = buf.readBoolean();
		if(hasSpawnLocation){
			int x = buf.readInt();
			int y = buf.readInt();
			int z = buf.readInt();
			this.spawnLocation = new ChunkCoordinates(x,y,z);
		}
		//read players
		int playerCount = buf.readInt();
		for(int i = 0; i < playerCount; i++){
			//read UUID
			int uuidLength = buf.readInt();
			UUID uuid = UUID.fromString(new String(buf.readBytes(uuidLength).array()));
			addPlayer(CoKPlayerRegistry.getOrCreatPlayerForUUID(uuid));
		}
	}
}
