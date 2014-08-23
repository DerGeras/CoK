package de.minestar.cok.hook;

import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.minestar.cok.event.BlockPlaceEvent;
import de.minestar.cok.game.SocketRegistry;
import de.minestar.cok.tileentity.TileEntitySocket;

public class BlockListener {

	@SubscribeEvent
	public void onBlockPlace(BlockPlaceEvent event){
		//LogHelper.info(String.format("Block placed at %d, %d, %d", event.x, event.x, event.z));
		for(TileEntitySocket socket : SocketRegistry.getAllSockets()){
			socket.checkEvent(event);
		}
	}
	
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event){
		//LogHelper.info(String.format("Block broken at %d, %d, %d", event.x, event.y, event.z));
		for(TileEntitySocket socket : SocketRegistry.getAllSockets()){
			socket.checkEvent(event);
		}
	}
	
}
