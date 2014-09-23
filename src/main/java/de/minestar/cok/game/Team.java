package de.minestar.cok.game;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.Constants.NBT;

import com.sun.istack.internal.Nullable;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.minestar.cok.game.profession.Profession;
import de.minestar.cok.game.worlddata.CoKGameWorldData;
import de.minestar.cok.tileentity.TileEntitySocket;
import de.minestar.cok.util.ChatSendHelper;
import de.minestar.cok.util.Color;
import de.minestar.cok.util.LogHelper;
import de.minestar.cok.util.PlayerHelper;

public class Team {

	private HashSet<UUID> playerUUIDs = new HashSet<UUID>();
	private LinkedList<Profession> availableProfessions = new LinkedList<Profession>();
	
	private static Random rand = new Random();
	
	private static final int professionRespawnTime = 20 * 60;
	
	private String name;
	private char color;
	private ChunkCoordinates spawnLocation;
	private CoKGame currentGame;
	private int timeSinceLastProfessionChange;
	
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
	
	/**
	 * Returns all players of this team (includes offlien players)
	 * 
	 * @return
	 */
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
	
	public String getFormattedName(){
		return String.format("%s%s", Color.getColorCodeFromChar(color), name);
	}
	
	public void setName(String name){
		if(name != null){
			this.name = name;
		}
	}
	
	/**
	 * returns the integer representation of this teams color
	 * 
	 * @return
	 */
	public int getColorAsInt(){
		return color >= 97 ? color - 87 : color - 48;
	}
	

	public boolean hasPlayer(CoKPlayer player){
		return playerUUIDs.contains(player.getUUID());
	}
	
	public ChunkCoordinates getSpawnlocation(){
		return spawnLocation;
	}
	
	/**
	 * Sets the spawn location for this team.
	 * In case the game is running, the spawn coordinates for
	 * all players are set to this location
	 * 
	 * @param coords
	 */
	public void setSpawnCoordinates(@Nullable ChunkCoordinates coords){
		this.spawnLocation = coords;
		if(spawnLocation != null && getGame() != null && getGame().isRunning()){
			for(CoKPlayer player : getAllPlayers()){
				EntityPlayerMP playerEntity = player.getPlayerEntity();
				if(playerEntity != null){
					playerEntity.setSpawnChunk(spawnLocation, true, 0);				
				}
			}
		}
	}
	
	/**
	 * Add a player to the team.
	 * In case the game is running, the player is teleported to the
	 * spawnpoint of the team, otherwise they will be teleported
	 * to the spawn specified by the game (if it is non-null)
	 * 
	 * @param player
	 * @return
	 */
	public boolean addPlayer(CoKPlayer player){
		CoKGameWorldData.markDataDirty();
		if(player != null){
			if(player.getTeam() != null){
				player.getTeam().removePlayer(player);
			}
			player.setTeam(this);
			if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
				playerJoined(player);
				EntityPlayerMP playerEntity = player.getPlayerEntity();
				if(getGame() != null && playerEntity != null){
					if(getGame().isRunning()){
						if(spawnLocation != null){
							playerEntity.setSpawnChunk(spawnLocation, true, 0);
							playerEntity.playerNetServerHandler.setPlayerLocation(
									spawnLocation.posX, spawnLocation.posY, spawnLocation.posZ, 0, 0);	
						}
					} else{ //game not running
						ChunkCoordinates coords = getGame().getSpawnLocation();
						if(coords != null){
							playerEntity.setSpawnChunk(coords, true, 0);
							playerEntity.playerNetServerHandler.setPlayerLocation(
									coords.posX, coords.posY, coords.posZ, 0, 0);	
						}
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
	
	/**
	 * Removes a player from the team. The player is then teleported
	 * to the general spawnpoint (if set)
	 * 
	 * @param player
	 * @return
	 */
	public boolean removePlayer(CoKPlayer player){
		CoKGameWorldData.markDataDirty();
		if(player != null){
			player.setTeam(null);
			if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
				playerLeft(player);
				//teleport to global spawn
				EntityPlayerMP playerEntity = player.getPlayerEntity();
				ChunkCoordinates coords = CoKGameRegistry.getGeneralSpawn();
				if(playerEntity != null && coords != null){
					playerEntity.setSpawnChunk(spawnLocation, true, 0);
					playerEntity.playerNetServerHandler.setPlayerLocation(
							coords.posX, coords.posY, coords.posZ, 0, 0);	
				}
			}
		}
		return playerUUIDs.remove(player.getUUID());
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
	 * 
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
	 * Should be called whenever a player enters the team/logs back in/revives
	 * Triggers a distribution of all available professions
	 * 
	 * @param player
	 */
	public void playerJoined(CoKPlayer player){
		EntityPlayerMP playerEntity = player.getPlayerEntity();
		if(getGame() != null && playerEntity != null){
			if(getGame().isRunning()){
				if(spawnLocation != null){
					playerEntity.setSpawnChunk(spawnLocation, true, 0);
				}
			} else { //game not running
				if(getGame().getSpawnLocation() != null){
					playerEntity.setSpawnChunk(getGame().getSpawnLocation(), true, 0);
				}
			}
		}
	}
	
	/**
	 * Should always be called when a player disconnects/dies/leaves the team
	 * Triggers a distribution of all available professions
	 * 
	 * @param player
	 */
	public void playerLeft(CoKPlayer player){
		if(player.getProfession() != null){
			availableProfessions.add(player.getProfession());
			player.setProfession(null);
			PlayerHelper.clearGivenItemsFromInventory(player.getPlayerEntity());
			timeSinceLastProfessionChange = professionRespawnTime;
		}
	}
	
	/**
	 * Called from {@link CoKGame#startGame()}
	 * Teleports the players to their respective team bases
	 * and sets health and hunger to max
	 * Triggers a distribution of all professions
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
	 * Called from {@link CoKGame#stopGame()}
	 * Clears given items from inventory
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
	 * Called by {@link CoKGame#onUpdate()}
	 * Redistributes professions etc.
	 */
	public void onUpdate(){
		if(currentGame != null && currentGame.isRunning()){
			timeSinceLastProfessionChange = Math.max(0, timeSinceLastProfessionChange - 1);
			if(timeSinceLastProfessionChange == 20 * 10){
				ChatSendHelper.broadCastMessageToTeam(this, "Class redestribution in 10 seconds!");
			}
			if(timeSinceLastProfessionChange == 20 * 5){
				ChatSendHelper.broadCastMessageToTeam(this, "Class redestribution in 5 seconds!");				
			}
			if(timeSinceLastProfessionChange == 0){
				distributeProfessions();
			}
		}
	}
	
	/**
	 * Distribute all available professions
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
	 * Returns a suitable profession candidate
	 * (a player that is alive and has no profession)
	 * 
	 * @return
	 */
	@Nullable
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
	 * Remove all players from the team
	 */
	public void clearPlayers(){
		for(CoKPlayer player : getAllPlayers()){
			removePlayer(player);
		}
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
			addPlayer(CoKPlayerRegistry.getPlayerForUUID(UUID.fromString(uuidString)));
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
		//write color
		buf.writeChar(color);
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
		//read color
		this.color = buf.readChar();
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
			addPlayer(CoKPlayerRegistry.getPlayerForUUID(uuid));
		}
	}
}
