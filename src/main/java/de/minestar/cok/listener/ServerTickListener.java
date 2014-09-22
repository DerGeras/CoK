package de.minestar.cok.listener;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.CoKGameRegistry;
import de.minestar.cok.game.worlddata.CoKGameWorldData;
import de.minestar.cok.network.NetworkHandler;
import de.minestar.cok.network.message.MessageCompleteGameState;

public class ServerTickListener {
	
	public static boolean isScoreCheckQueued = false;
	public static boolean isGameStateUpdated = false;

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event){
		for(CoKGame game : CoKGameRegistry.getAllGames()){
			game.onUpdate();
		}
		isScoreCheckQueued = false;
		if(isGameStateUpdated){
			NetworkHandler.network.sendToAll(new MessageCompleteGameState());
			isGameStateUpdated = false;
		}
	}
	
}
