package de.minestar.cok.game;

import io.netty.buffer.ByteBuf;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.Constants.NBT;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.minestar.cok.game.worlddata.CoKGameWorldData;
import de.minestar.cok.tileentity.TileEntitySocket;
import de.minestar.cok.util.ChatSendHelper;
import de.minestar.cok.util.Color;

public class CoKGame {

	private HashMap<String, Team> teams = new HashMap<String, Team>();
	
	private String name;
	private boolean isRunning = false;
	private ChunkCoordinates spawnLocation;
	
	public CoKGame(String name){
		this.name = name;
	}
	
	public CoKGame(NBTTagCompound compound){
		readFromNBT(compound);
	}
	
	public CoKGame(ByteBuf buf){
		readFromBuffer(buf);
	}
	
	public void addTeam(Team team){
		if(CoKGameWorldData.data != null){
			CoKGameWorldData.data.markDirty();
		}
		if(team != null){
			team.setGame(this);
			teams.put(team.getName(), team);
		}
	}
	
	public void removeTeam(Team team){
		teams.remove(team.getName());
	}
	
	public void removeTeam(String name){
		if(CoKGameWorldData.data != null){
			CoKGameWorldData.data.markDirty();
		}
		teams.remove(name);
	}
	
	public Team getTeam(int color){
		for(Team team : teams.values()){
			if(team.getColorAsInt() == color){
				return team;
			}
		}
		return null;
	}
	
	public Team getTeam(String name){
		return teams.get(name);
	}
	
	public Collection<Team> getAllTeams(){
		return teams.values();
	}
	
	public Collection<String> getAllTeamNames(){
		return teams.keySet();
	}
	
	public boolean isRunning(){
		return isRunning;
	}
	
	public String getName(){
		return name;
	}
	
	public ChunkCoordinates getSpawnLocation(){
		return spawnLocation;
	}
	
	public void setSpawnLocation(ChunkCoordinates spawnLocation){
		this.spawnLocation = spawnLocation;
	}
	
	@SideOnly(Side.SERVER)
	public void startGame(){
		ChatSendHelper.broadCastError("Winter is comming!");
		ChatSendHelper.broadCastMessage("Started the game " + name +  "!");
		isRunning = true;
		for(Team team : teams.values()){
			ChunkCoordinates coords = team.getSpawnlocation();
			if(coords != null){
				for(CoKPlayer player : team.getAllPlayers()){
					EntityPlayerMP playerEntity = player.getPlayerEntity();
					if(playerEntity != null){
						playerEntity.setHealth(20.0f);
						playerEntity.getFoodStats().setFoodLevel(20);
						playerEntity.getFoodStats().setFoodSaturationLevel(20);
						playerEntity.setSpawnChunk(coords, true, 0);
						playerEntity.playerNetServerHandler.setPlayerLocation(coords.posX, coords.posY, coords.posZ, 0, 0);
					}
				}
			}
		}
	}
	
	@SideOnly(Side.SERVER)
	public void stopGame(){
		ChatSendHelper.broadCastError("The game " + name + " has ended!");
		ChatSendHelper.broadCastMessage("Scores:");
		for(Team team : teams.values()){
			HashSet<TileEntitySocket> teamSockets = SocketRegistry.getSockets(team.getColorAsInt());
			ChatSendHelper.broadCastMessage(String.format("%s%s%s:%d/%d",
					Color.getColorCodeFromChar(team.getColor()),
					team.getName(),
					Color.getColorCodeFromString("blue"),
					getScoreForTeam(team),
					teamSockets == null ? 0 : teamSockets.size() * GameSettings.buildingHeight));
		}
		isRunning = false;
		if(spawnLocation != null){
			for(Team team : teams.values()){
				for(CoKPlayer player : team.getAllPlayers()){
					EntityPlayerMP playerEntity = player.getPlayerEntity();
					if(playerEntity != null){
						playerEntity.setSpawnChunk(spawnLocation, true, 0);
						playerEntity.playerNetServerHandler.setPlayerLocation(spawnLocation.posX, spawnLocation.posY, spawnLocation.posZ, 0, 0);
					}
				}
			}
		}
	}
	
	@SideOnly(Side.SERVER)
	public int getScoreForTeam(Team team){
		HashSet<TileEntitySocket> teamSockets = SocketRegistry.getSockets(team.getColorAsInt());
		if(teamSockets == null){
			return 0;
		}
		int sum = 0;
		for(TileEntitySocket s : teamSockets){
			sum += s.countBlocks();
		}
		return sum;
	}
	
	public void readFromNBT(NBTTagCompound compound){
		//read name
		this.name = compound.getString("name");
		//read spawnLocation
		if(compound.hasKey("spawnX")){
			int posX = compound.getInteger("spawnX");
			int posY = compound.getInteger("spawnY");
			int posZ = compound.getInteger("spawnZ");
			spawnLocation = new ChunkCoordinates(posX, posY, posZ);
		}
		//read teams
		NBTTagList teamList = compound.getTagList("teams", NBT.TAG_COMPOUND);
		for(int i = 0; i < teamList.tagCount(); i++){
			NBTTagCompound teamCompound = teamList.getCompoundTagAt(i);
			Team team = new Team(teamCompound);
			addTeam(team);
		}
	}
	
	public void writeToNBT(NBTTagCompound compound){
		//write name
		compound.setString("name", name);
		//write spawnLocation
		if(spawnLocation != null){
			compound.setInteger("spawnX", spawnLocation.posX);
			compound.setInteger("spawnY", spawnLocation.posY);
			compound.setInteger("spawnZ", spawnLocation.posZ);
		}
		//write teams
		NBTTagList teamList = new NBTTagList();
		for(Team team : teams.values()){
			NBTTagCompound teamCompound = new NBTTagCompound();
			team.writeToNBT(teamCompound);
			teamList.appendTag(teamCompound);
		}
		compound.setTag("teams", teamList);
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
		
		//write teams
		buf.writeInt(teams.size());
		for(Team team: teams.values()){
			team.writeToBuffer(buf);
		}
	}
	
	public void readFromBuffer(ByteBuf buf){
		//read name
		int namLength = buf.readInt();
		this.name = new String(buf.readBytes(namLength).array());
		
		//read spawnLocation
		boolean hasSpawnLocation = buf.readBoolean();
		if(hasSpawnLocation){
			int x = buf.readInt();
			int y = buf.readInt();
			int z = buf.readInt();
			this.spawnLocation = new ChunkCoordinates(x,y,z);
		}
		
		//read teams
		int teamCount = buf.readInt();
		for(int i = 0 ; i < teamCount; i++){
			addTeam(new Team(buf));
		}
	}
	
}
