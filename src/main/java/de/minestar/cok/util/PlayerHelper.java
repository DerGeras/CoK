package de.minestar.cok.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import cpw.mods.fml.common.FMLCommonHandler;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.CoKPlayer;
import de.minestar.cok.game.Team;

public class PlayerHelper {

	public static EntityPlayerMP getPlayerForName(String username){
		for (EntityPlayerMP player :  (ArrayList<EntityPlayerMP>)FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList){
			if(player.getGameProfile().getName().equals(username)){
				return player;
			}
		}
		return null;
	}
	
	
	public static EntityPlayerMP getPlayerForUUID(UUID uuid){
		for (EntityPlayerMP player :  (ArrayList<EntityPlayerMP>)FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList){
			if(player.getPersistentID().equals(uuid)){
				return player;
			}
		}
		return null;
	}
	
	
	public static UUID getUUIDForName(String username){
		 return getPlayerForName(username).getPersistentID();
	}
	
	
	public static String getNameForUUID(UUID uuid){
		return getPlayerForUUID(uuid).getGameProfile().getName();
	}

	/**
	 * Clears all of the player inventory
	 * 
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
	 * Clear all items that are marked as given.
	 * 
	 * @param playerEntity
	 */
	public static void clearGivenItemsFromInventory(EntityPlayer playerEntity) {
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
	
	/**
	 * Returns a set of player entites for the given game.
	 * 
	 * @param game
	 * @return
	 */
	public static HashSet<EntityPlayerMP> getPlayerEntitiesForGame(CoKGame game){
		HashSet<EntityPlayerMP> result = new HashSet<EntityPlayerMP>();
		if(game != null){
			for(Team team : game.getAllTeams()){
				for(CoKPlayer player : team.getAllPlayers()){
					EntityPlayerMP playerEntity = getPlayerForUUID(player.getUUID());
					if(playerEntity != null){
						result.add(playerEntity);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Returns a set of player entitities for the given team
	 * 
	 * @param team
	 * @return
	 */
	public static HashSet<EntityPlayer> getPlayerEntitiesForTeam(Team team) {
		HashSet<EntityPlayer> result = new HashSet<EntityPlayer>();
		if(team != null){
			for(CoKPlayer player : team.getAllPlayers()){
				EntityPlayer playerEntity = getPlayerForUUID(player.getUUID());
				if(playerEntity != null){
					result.add(playerEntity);
				}
			}
		}
		return result;
	}
	
}
