package de.minestar.cok.profession;

import java.util.HashSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import de.minestar.cok.game.Team;
import de.minestar.cok.references.Color;

public abstract class Profession {

	private String name = "";
	
	public HashSet<Item> givenItems = new HashSet<Item>();
	
	public Profession(String name){
		this.name = name;
		givenItems.add(Item.helmetLeather);
		givenItems.add(Item.plateLeather);
		givenItems.add(Item.legsLeather);
		givenItems.add(Item.bootsLeather);
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
		
		ItemStack plateItemStack = new ItemStack(Item.plateLeather);
		NBTTagCompound plateCompound = new NBTTagCompound();
		plateItemStack.setTagCompound(plateCompound);
		plateCompound.setCompoundTag("display", new NBTTagCompound());
		plateCompound.getCompoundTag("display").setInteger("color", Color.getHexColorFromInt(team.getColorAsInt()));
		player.inventory.armorInventory[2] = plateItemStack;
		
		ItemStack legsItemStack = new ItemStack(Item.legsLeather);
		NBTTagCompound legsCompound = new NBTTagCompound();
		legsItemStack.setTagCompound(legsCompound);
		legsCompound.setCompoundTag("display", new NBTTagCompound());
		legsCompound.getCompoundTag("display").setInteger("color", Color.getHexColorFromInt(team.getColorAsInt()));
		player.inventory.armorInventory[1] = legsItemStack;
		
		ItemStack bootsItemStack = new ItemStack(Item.bootsLeather);
		NBTTagCompound bootsCompund = new NBTTagCompound();
		bootsItemStack.setTagCompound(bootsCompund);
		bootsCompund.setCompoundTag("display", new NBTTagCompound());
		bootsCompund.getCompoundTag("display").setInteger("color", Color.getHexColorFromInt(team.getColorAsInt()));
		player.inventory.armorInventory[0] = bootsItemStack;
	}
	
	/**
	 * returns the % of blocks that increase in case of a player death.
	 * @return
	 */
	public int getPunishment(){
		return 0;
	}
	
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
