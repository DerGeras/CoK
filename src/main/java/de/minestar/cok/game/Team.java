package de.minestar.cok.game;

import java.util.HashSet;
import java.util.UUID;

import net.minecraft.util.ChunkCoordinates;
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
	
	public boolean addPlayer(CoKPlayer player){
		if(player != null){
			if(player.getTeam() != null){
				player.getTeam().removePlayer(player);
			}
			player.setTeam(this);
			return addPlayer(player);
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
}
