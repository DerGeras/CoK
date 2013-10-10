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
package de.minestar.cok.profession;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import de.minestar.cok.game.Team;
import de.minestar.cok.references.Color;
import de.minestar.cok.references.Reference;

public abstract class Profession {
	
	public static ProfessionKing KING = new ProfessionKing();
	public static ProfessionCrossbowman CROSSBOWMAN = new ProfessionCrossbowman();
	public static ProfessionBarbarian BARBARIAN = new ProfessionBarbarian();

	private String name = "";
	
	public Profession(String name){
		this.name = name;
	}
	
	/**
	 * give the player the kit for the profession
	 * @param player
	 */
	public void giveKit(EntityPlayer player, Team team){
		//Armor
		ItemStack helmet = player.inventory.armorItemInSlot(3);
		if(helmet != null){
			if(!player.inventory.addItemStackToInventory(helmet)){
				player.dropPlayerItem(helmet);
			}
		}
		ItemStack helmetItemStack = new ItemStack(Item.helmetLeather);
		NBTTagCompound helmetCompund = new NBTTagCompound();
		helmetItemStack.setTagCompound(helmetCompund);
		helmetCompund.setCompoundTag("display", new NBTTagCompound());
		helmetCompund.getCompoundTag("display").setInteger("color", Color.getHexColorFromInt(team.getColorAsInt()));
		setGivenItem(helmetItemStack);
		player.inventory.armorInventory[3] = helmetItemStack;
	}
	
	/**
	 * returns the % of blocks that increase in case of a player death.
	 * @return
	 */
	public abstract float getPunishment();
	
	/**
	 * @return the className
	 */
	public String getClassName() 
	{
		return name;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String name) 
	{
		this.name = name;
	}
	
	/**
	 * Tries to add a stack to the inventory, throws out non-given items (dropPlayerItem calls
	 * ItemTossEvent)
	 * @param player
	 * @param inventory
	 * @param stack
	 */
	protected void addItemStackToInventory(EntityPlayer player, ItemStack stack){
		int i = 15;
		while(!player.inventory.addItemStackToInventory(stack)){
			ItemStack thrownStack = player.inventory.getStackInSlot(i);
			player.inventory.setInventorySlotContents(i, null);
			player.dropPlayerItem(thrownStack);
			i++;
		}
	}
	
	/**
	 * set the nbttag for an item to be given.
	 * @param stack
	 */
	protected void setGivenItem(ItemStack stack){
		if(stack.getTagCompound() == null){
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setBoolean(Reference.GIVE_ITEM, true);
	}
	
	/**
	 * Checks whether the item stack is given.
	 * @param stack
	 * @return
	 */
	public static boolean isGivenItem(ItemStack stack){
		return stack != null && stack.hasTagCompound() && stack.getTagCompound().getBoolean(Reference.GIVE_ITEM);
	}
}
