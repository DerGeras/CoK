package de.minestar.cok.hook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.game.CoKPlayer;
import de.minestar.cok.game.CoKPlayerRegistry;
import de.minestar.cok.network.NetworkHandler;
import de.minestar.cok.network.message.MessageCompleteGameState;

public class PlayerTracker {
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event){
		CoKPlayer player = CoKPlayerRegistry.getOrCreatPlayerForUUID(event.player.getUniqueID());
		if(player.getTeam() != null){
			player.getTeam().playerJoined(player);
		}
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
			NetworkHandler.network.sendTo(new MessageCompleteGameState(), (EntityPlayerMP) event.player);
		}
	}
	
	@SubscribeEvent
	public void onPlayerLogout(PlayerLoggedOutEvent event){
		CoKPlayer player = CoKPlayerRegistry.getOrCreatPlayerForUUID(event.player.getUniqueID());
		if(player.getTeam() != null){
			player.getTeam().playerLeft(player);
		}
	}
	
	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event){
		if(event.entityLiving instanceof EntityPlayer){
			CoKPlayer player = CoKPlayerRegistry.getOrCreatPlayerForUUID(event.player.getUniqueID());
			if(player.getTeam() != null){
				player.getTeam().playerLeft(player);
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event){
		CoKPlayer player = CoKPlayerRegistry.getOrCreatPlayerForUUID(event.player.getUniqueID());
		if(player.getTeam() != null){
			player.getTeam().playerJoined(player);
		}
	}

}
