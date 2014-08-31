package de.minestar.cok.listener;

import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.minestar.cok.event.BlockPlaceEvent;
import de.minestar.cok.game.CoKPlayer;
import de.minestar.cok.game.CoKPlayerRegistry;
import de.minestar.cok.game.SocketRegistry;
import de.minestar.cok.tileentity.TileEntitySocket;
import de.minestar.cok.util.ChatSendHelper;
import de.minestar.cok.worldguard.Worldguard;

public class BlockListener {

	@SubscribeEvent
	public void onBlockPlace(BlockPlaceEvent event){
		if(event.player.capabilities.isCreativeMode){
			return;
		}
		//check whether game is running
		CoKPlayer player = CoKPlayerRegistry.getPlayerForUUID(event.player.getUniqueID());
		if(player.getGame() == null || !player.getGame().isRunning()){
			event.setCanceled(true);
			return;
		}
		//check for protection
		boolean isProtected = Worldguard.isProtected(event.player.dimension, event.x, event.y, event.z);
		event.setCanceled(isProtected);
		if(isProtected){
			ChatSendHelper.sendErrorMessageToPlayer(event.player, "This area is protected!");
		} else {
			//check for sockets in the area
			for(TileEntitySocket socket : SocketRegistry.getAllSockets()){
				socket.checkEvent(event);
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event){
		if(event.getPlayer().capabilities.isCreativeMode){
			return;
		}
		//check whether game is running
		CoKPlayer player = CoKPlayerRegistry.getPlayerForUUID(event.getPlayer().getUniqueID());
		if(player.getGame() == null || !player.getGame().isRunning()){
			event.setCanceled(true);
			return;
		}
		//check for protection
		boolean isProtected = Worldguard.isProtected(event.getPlayer().dimension, event.x, event.y, event.z);
		event.setCanceled(isProtected);
		if(isProtected){
			ChatSendHelper.sendErrorMessageToPlayer(event.getPlayer(), "This area is protected!");
		} else {
			//check for sockets in the area
			for(TileEntitySocket socket : SocketRegistry.getAllSockets()){
				socket.checkEvent(event);
			}
		}
	}
	
}
