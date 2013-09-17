package de.minestar.cok.hook;

import java.util.HashSet;

import net.minecraftforge.event.ForgeSubscribe;
import de.minestar.cok.event.EventBlockBreak;
import de.minestar.cok.event.EventBlockPlace;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.tileentity.TileEntitySocket;

public class BlockListener {
	
	@ForgeSubscribe
	public void onBlockBreak(EventBlockBreak event){
		if(event.player.capabilities.isCreativeMode){
			return; //Creative Mode is always allowed
		}
		if(!CoKGame.gameRunning || CoKGame.getTeamOfPlayer(event.player.username) == null){
			event.setCanceled(true);
			return;
		}
		for(HashSet<TileEntitySocket> teamSockets : CoKGame.sockets.values()){
			for(TileEntitySocket socket : teamSockets){
				socket.checkEvent(event);
			}
		}
	}
	
	@ForgeSubscribe
	public void onBlockPlace(EventBlockPlace event){
		if(event.player.capabilities.isCreativeMode){
			return; //Creative Mode is always allowed
		}
		if(!CoKGame.gameRunning || CoKGame.getTeamOfPlayer(event.player.username) == null){
			event.setCanceled(true);
			return;
		}
		for(HashSet<TileEntitySocket> teamSockets : CoKGame.sockets.values()){
			for(TileEntitySocket socket : teamSockets){
				socket.checkEvent(event);
			}
		}
	}

}
