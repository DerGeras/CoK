package de.minestar.cok.game;

import java.util.HashSet;
import java.util.UUID;

import com.sun.xml.internal.bind.v2.runtime.Coordinator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.Constants.NBT;
import de.minestar.cok.game.worlddata.CoKGameWorldData;
import de.minestar.cok.tileentity.TileEntitySocket;

public class Team {

	private HashSet<CoKPlayer> players = new HashSet<CoKPlayer>();
	
	private String name;
	private char color;
	private ChunkCoordinates spawnCoordinates;
	private CoKGame currentGame;
	
	public Team (String name, char color){
		this.name = name;
		this.color = color;
	}
	
	
	public Team (NBTTagCompound compound){
		readFromNBT(compound);
	}
	
	public HashSet<CoKPlayer> getAllPlayers(){
		return players;
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
		return players.contains(player);
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
			return players.add(player);
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
			player.setTeam(null);
		}
		return players.remove(player);
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
	 * remove all players from team
	 */
	public void clearPlayers(){
		for(CoKPlayer player : players){
			player.setTeam(null);
		}
		players.clear();
	}
	
	public void readFromNBT(NBTTagCompound compound){
		this.name = compound.getString("name");
		this.color = Character.forDigit(compound.getInteger("color"), 16);
		//read coordinates
		if(compound.hasKey("spawnX")){
			int posX = compound.getInteger("spawnX");
			int posY = compound.getInteger("spawnY");
			int posZ = compound.getInteger("spawnZ");
			spawnCoordinates = new ChunkCoordinates(posX, posY, posZ);
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
		if(spawnCoordinates != null){
			compound.setInteger("spawnX", spawnCoordinates.posX);
			compound.setInteger("spawnY", spawnCoordinates.posY);
			compound.setInteger("spawnZ", spawnCoordinates.posZ);
		}
		//write spawncoords
		NBTTagList playerList = new NBTTagList();
		for(CoKPlayer player : players){
			playerList.appendTag(new NBTTagString(player.getUUID().toString()));
		}
		compound.setTag("players", playerList);
	}
}
