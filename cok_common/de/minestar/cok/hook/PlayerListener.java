/*******************************************************************************
 * Clash of Kingdoms Minecraft Mod
 *     Copyright (C) 2013 Geras
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package de.minestar.cok.hook;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EnumStatus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Team;
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.profession.Profession;

public class PlayerListener {

	/**
	 * Called upon Player deaths, removes given items and changes the captain.
	 * @param event
	 */
	@ForgeSubscribe
	public void onPlayerDeath(PlayerDropsEvent event){

		String name = event.entityPlayer.username;
		if(name == null){
			return;
		}
		Team team = CoKGame.getTeamOfPlayer(name);
		if(team == null){
			event.setCanceled(true);
			return;
		}
		

		Profession profession = CoKGame.playerProfessions.get(name);
		String teamCaptain = team.getCaptain();

		team.playerGone(event.entityPlayer.username);
		
		if(!CoKGame.gameRunning){
			event.setCanceled(true);
			return;
		}
		
		//Remove given items from droplist
		ArrayList<EntityItem> droppedItems = new ArrayList<EntityItem>();
		for(EntityItem itemEntity : event.drops){
			if(!Profession.isGivenItem(itemEntity.getEntityItem())){
				droppedItems.add(itemEntity);
			}
		}
		event.drops.clear();
		event.drops.addAll(droppedItems);
		

		//Punish team
		if(profession != null){
			CoKGame.punishTeam(team, profession);
		}
		
		//redistribute Professions if needed
		if(teamCaptain.equals(name)){
			//drop head :D
			ItemStack headStack = new ItemStack(Item.skull);
			headStack.setItemDamage(3); //Skulltype head
			headStack.setTagCompound(new NBTTagCompound("tag"));
			NBTTagCompound headTag = headStack.getTagCompound();
			headTag.setString("SkullOwner", name);
			event.drops.add(new EntityItem(event.entityPlayer.worldObj, event.entityPlayer.posX,
					event.entityPlayer.posY, event.entityPlayer.posZ, headStack));
			//change captain
			ChatSendHelper.broadCastError("THE RULER OF THE KINGDOM " + team.getName() + " " + name + " HAS DIED!");
		}
	}
	
	/**
	 * Players shouldn't throw items away that were given by the game.
	 * @param event
	 */
	@ForgeSubscribe
	public void onPlayerTossItem(ItemTossEvent event){
		if(!event.player.capabilities.isCreativeMode){
			ItemStack stack = event.entityItem.getEntityItem();
			if(Profession.isGivenItem(stack)){
				event.setCanceled(true);
				event.player.inventory.addItemStackToInventory(stack);
			}
		}
	}
	
	/**
	 * Prevent spectators from attacking.
	 * @param event
	 */
	@ForgeSubscribe
	public void onPlayerHit(LivingAttackEvent event){
		Entity source = event.source.getEntity();
		if(source != null && (source instanceof EntityPlayer)){
			EntityPlayer player = (EntityPlayer) source;
			if(!CoKGame.gameRunning || CoKGame.getTeamOfPlayer(player.username) == null){
				event.setCanceled(true);
			}
		}
	}
	
	@ForgeSubscribe
	public void onPlayerSleepInBed(PlayerSleepInBedEvent event){
		event.result = EnumStatus.NOT_POSSIBLE_HERE;
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
			ChatSendHelper.sendMessageToPlayer((EntityPlayerMP)event.entityPlayer, "Want to sleep through a fight, coward?");
		}
	}
	
}
