package de.minestar.cok.game;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class CoKPlayerRegistry {

	private static HashMap<UUID, CoKPlayer> allPlayers = new HashMap<UUID, CoKPlayer>();
	
	public static void addPlayer(CoKPlayer player){
		allPlayers.put(player.getUUID(), player);
	}
	
	public static void addPlayer(UUID uuid){
		addPlayer(new CoKPlayer(uuid));
	}
	
	public static void removePlayer(CoKPlayer player){
		allPlayers.remove(player);
	}
	
	public static Collection<CoKPlayer> getAllPlayers(){
		return allPlayers.values();
	}
	
	public static CoKPlayer getPlayerForName(String username){
		for(CoKPlayer player : allPlayers.values()){
			if(player.getUserName().equals(username)){
				return player;
			}
		}
		return null;
	}
	
	public static CoKPlayer getPlayerForUUID(UUID uuid){
		return allPlayers.get(uuid);
	}
	
	public static CoKPlayer getOrCreatPlayerForUUID(UUID uuid){
		if(allPlayers.containsKey(uuid)){
			return allPlayers.get(uuid);
		}
		return allPlayers.put(uuid, new CoKPlayer(uuid));
	}
	
	public static void clearPlayers(){
		allPlayers.clear();
	}
}
