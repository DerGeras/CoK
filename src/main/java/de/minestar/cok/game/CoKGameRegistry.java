package de.minestar.cok.game;

import java.util.HashMap;
import java.util.UUID;

public class CoKGameRegistry {

	public static HashMap<String, CoKGame> registeredGames = new HashMap<String, CoKGame>();
	
	public static void registerGame(String name){
		registeredGames.put(name, new CoKGame(name));
	}
	
}
