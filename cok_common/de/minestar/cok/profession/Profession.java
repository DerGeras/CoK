package de.minestar.cok.profession;

import java.util.HashSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import de.minestar.cok.game.Team;
import de.minestar.cok.references.Color;

public abstract class Profession {
	
	public static ProfessionKing KING = new ProfessionKing();
	public static ProfessionCrossbowman CROSSBOWMAN = new ProfessionCrossbowman();
	public static ProfessionBarbarian BARBARIAN = new ProfessionBarbarian();

	private String name = "";
	
	public HashSet<Item> givenItems = new HashSet<Item>();
	
	public Profession(String name){
		this.name = name;
		givenItems.add(Item.helmetLeather);
	}
	
	/**
	 * give the player the kit for the profession
	 * @param player
	 */
	public void giveKit(EntityPlayer player, Team team){
		//Armor
		ItemStack helmetItemStack = new ItemStack(Item.helmetLeather);
		NBTTagCompound helmetCompund = new NBTTagCompound();
		helmetItemStack.setTagCompound(helmetCompund);
		helmetCompund.setCompoundTag("display", new NBTTagCompound());
		helmetCompund.getCompoundTag("display").setInteger("color", Color.getHexColorFromInt(team.getColorAsInt()));
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
}
