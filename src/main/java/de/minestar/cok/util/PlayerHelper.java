package de.minestar.cok.util;

import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class PlayerHelper {

	public static EntityPlayerMP getPlayerForName(String username){
		for (EntityPlayerMP player :  (ArrayList<EntityPlayerMP>)MinecraftServer.getServer().getConfigurationManager().playerEntityList){
			if(player.getGameProfile().getName().equals(username)){
				return player;
			}
		}
		return null;
	}
	
	public static EntityPlayerMP getPlayerForUUID(UUID uuid){
		for (EntityPlayerMP player :  (ArrayList<EntityPlayerMP>)MinecraftServer.getServer().getConfigurationManager().playerEntityList){
			if(player.getGameProfile().getId().equals(uuid)){
				return player;
			}
		}
		return null;
	}
	
	public static UUID getUUIDForName(String name){
		 return MinecraftServer.getServer().func_152358_ax().func_152655_a(name).getId();
	}
	
	public static String getNameForUUID(UUID uuid){
		return MinecraftServer.getServer().func_152358_ax().func_152652_a(uuid).getName();
	}
	
}
