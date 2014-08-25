package de.minestar.cok.util;

import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
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

	/**
	 * clears all of the player inventory
	 * @param playerEntity
	 */
	public static void clearInventory(EntityPlayerMP playerEntity) {
		InventoryPlayer inv = playerEntity.inventory;
		for(int i = 0; i < inv.mainInventory.length; i++){
			inv.mainInventory[i] = null;
		}
		
		for(int i = 0; i < inv.armorInventory.length; i++){
			inv.armorInventory[i] = null;
		}
		
		inv.setItemStack(null);
		
	}

	/**
	 * clear all items that are marked as given.
	 * @param playerEntity
	 */
	public static void clearGivenItemsFromInventory(EntityPlayerMP playerEntity) {
		InventoryPlayer inv = playerEntity.inventory;
		for(int i = 0; i < inv.mainInventory.length; i++){
			if(inv.mainInventory[i] != null && ItemStackHelper.isGiven(inv.mainInventory[i])){
				inv.mainInventory[i] = null;
			}
		}
		
		for(int i = 0; i < inv.armorInventory.length; i++){
			if(inv.armorInventory[i] != null && ItemStackHelper.isGiven(inv.armorInventory[i])){
				inv.armorInventory[i] = null;
			}
		}
		
		if(ItemStackHelper.isGiven(inv.getItemStack())){
			inv.setItemStack(null);
		}
		
	}
	
}
