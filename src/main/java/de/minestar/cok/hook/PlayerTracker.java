package de.minestar.cok.hook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.game.CoKPlayerRegistry;
import de.minestar.cok.network.NetworkHandler;
import de.minestar.cok.network.message.MessageCompleteGameState;

public class PlayerTracker {
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event){
		if(CoKPlayerRegistry.getPlayerForUUID(event.player.getUniqueID()) == null){
			CoKPlayerRegistry.addPlayer(event.player.getUniqueID());
		}
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
			NetworkHandler.network.sendTo(new MessageCompleteGameState(), (EntityPlayerMP) event.player);
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
