package de.minestar.cok.game.profession;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import de.minestar.cok.game.Team;
import de.minestar.cok.util.Color;
import de.minestar.cok.util.ItemStackHelper;

public abstract class Profession {
	
	public static final Profession king = new ProfessionKing();
	public static final Profession barbarian = new ProfessionBarbarian();
	public static final Profession crossbowman = new ProfessionCrossbowman();
	
	private String name = "";
	
	public Profession(String name){
		this.name = name;
	}
	
	/**
	 * Give the player the kit for the profession
	 * 
	 * @param player
	 */
	public void giveKit(EntityPlayerMP player, Team team){
		//Armor
		ItemStack helmet = player.inventory.armorItemInSlot(3);
		if(helmet != null){
			if(!player.inventory.addItemStackToInventory(helmet)){
				player.dropPlayerItemWithRandomChoice(helmet, false);
			}
		}
		ItemStack helmetItemStack = new ItemStack(Items.leather_helmet);
		NBTTagCompound helmetCompound = new NBTTagCompound();
		helmetItemStack.setTagCompound(helmetCompound);
		helmetCompound.setTag("display", new NBTTagCompound());
		helmetCompound.getCompoundTag("display").setInteger("color", Color.getHexColorFromInt(team.getColorAsInt()));
		ItemStackHelper.setGiven(helmetItemStack);
		player.inventory.armorInventory[3] = helmetItemStack;
	}
	
	/**
	 * returns the % of blocks that increase in case of a player death.
	 * (number of blocks = number of sockets * punishment
	 * 
	 * @return
	 */
	public abstract float getPunishment();
	
	/**
	 * @return the className
	 */
	public String getClassName(){
		return name;
	}
	
	/**
	 * Tries to add a stack to the inventory, throws out non-given items (dropPlayerItem throws
	 * {@link ItemTossEvent))
	 * 
	 * @param player
	 * @param inventory
	 * @param stack
	 */
	protected void addItemStackToInventory(EntityPlayer player, ItemStack stack){
		int i = 15;
		while(!player.inventory.addItemStackToInventory(stack)){
			ItemStack thrownStack = player.inventory.getStackInSlot(i);
			player.inventory.setInventorySlotContents(i, null);
			player.dropPlayerItemWithRandomChoice(thrownStack, false);
			i++;
		}
	}
}
