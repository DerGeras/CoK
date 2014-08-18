package de.minestar.cok.game;

import java.util.HashSet;
import java.util.UUID;

import de.minestar.cok.util.PlayerHelper;

public class CoKPlayerRegistry {

	private static HashSet<CoKPlayer> allPlayers = new HashSet<CoKPlayer>();
	
	public static void addPlayer(CoKPlayer player){
		allPlayers.add(player);
	}
	
	public static void addPlayer(String username){
		addPlayer(new CoKPlayer(PlayerHelper.getUUIDForName(username)));
	}
	
	public static void addPlayer(UUID uuid){
		addPlayer(new CoKPlayer(uuid));
	}
	
	public static void removePlayer(CoKPlayer player){
		allPlayers.remove(player);
	}
	
	public static void removePlayer(String username){
		removePlayer(getPlayerForName(username));
	}
	
	public static void removePlayer(UUID uuid){
		removePlayer(getPlayerForUUID(uuid));
	}
	
	public static CoKPlayer getPlayerForName(String username){
		for(CoKPlayer player : allPlayers){
			if(player.getUserName().equals(username)){
				return player;
			}
		}
		return null;
	}
	
	public static CoKPlayer getPlayerForUUID(UUID uuid){
		for(CoKPlayer player : allPlayers){
			if(player.getUUID().equals(uuid)){
				return player;
			}
		}
		return null;
	}
}
