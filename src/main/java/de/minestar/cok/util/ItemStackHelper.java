package de.minestar.cok.util;

import de.minestar.cok.reference.Reference;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemStackHelper {

	public static void setGiven(ItemStack stack){
		if(stack.getTagCompound() == null){
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setBoolean(Reference.GIVEN_ITEM, true);
	}
	
	public static boolean isGiven(ItemStack stack){
		return stack != null && stack.hasTagCompound() && stack.getTagCompound().getBoolean(Reference.GIVEN_ITEM);
	}
	
}
