package de.minestar.cok.listener;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.CoKGameRegistry;

public class ServerTickListener {
	
	public static boolean isScoreCheckQueued = false;

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event){
		for(CoKGame game : CoKGameRegistry.registeredGames.values()){
			game.onUpdate();
		}
		isScoreCheckQueued = false;
	}
	
}
