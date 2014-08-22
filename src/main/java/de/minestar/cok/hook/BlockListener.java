package de.minestar.cok.hook;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.minestar.cok.event.BlockPlaceEvent;
import de.minestar.cok.util.LogHelper;

public class BlockListener {

	@SubscribeEvent
	public void onBlockPlace(BlockPlaceEvent event){
		LogHelper.info(String.format("Block placed at %d, %d, %d", event.x, event.x, event.z));
	}
	
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event){
		LogHelper.info(String.format("Block broken at %d, %d, %d", event.x, event.y, event.z));
	}
	
}
