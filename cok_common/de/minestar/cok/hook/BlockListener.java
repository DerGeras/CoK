package de.minestar.cok.hook;

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
		if(CoKGame.getTeamOfPlayer(event.player.username) == null){
			event.setCanceled(true);
		}
		for(TileEntitySocket socket : CoKGame.sockets){
			socket.checkEvent(event);
		}
//		System.out.println("Block broke at " + event.blockX + " " + event.blockY  + " " +
//				event.blockZ + " by " + event.player.username);
	}
	
	@ForgeSubscribe
	public void onBlockPlace(EventBlockPlace event){
		if(event.player.capabilities.isCreativeMode){
			return; //Creative Mode is always allowed
		}
		if(CoKGame.getTeamOfPlayer(event.player.username) == null){
			event.setCanceled(true);
		}
		for(TileEntitySocket socket : CoKGame.sockets){
			socket.checkEvent(event);
		}
//		System.out.println("Block placed at " + event.blockX + " " + event.blockY  + " " +
//				event.blockZ + " by " + event.player.username);
	}

}
