package de.minestar.cok.hook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import de.minestar.cok.game.CoKPlayerRegistry;

public class PlayerTracker {
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event){
		if(CoKPlayerRegistry.getPlayerForUUID(event.player.getUniqueID()) == null){
			CoKPlayerRegistry.addPlayer(event.player.getUniqueID());
		}
	}
	
	@SubscribeEvent
	public void onPlayerLogout(PlayerLoggedOutEvent event){
		//TODO
	}
	
	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event){
		if(event.entityLiving instanceof EntityPlayer){
			//TODO
		}
	}

}
