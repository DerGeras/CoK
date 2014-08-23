package de.minestar.cok.hook;

import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.minestar.cok.game.worlddata.CoKGameWorldData;

public class WorldLoadListener {


	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onWorldLoad(WorldEvent.Load event){
		CoKGameWorldData.onWorldLoad(event.world);
	}
	
}
