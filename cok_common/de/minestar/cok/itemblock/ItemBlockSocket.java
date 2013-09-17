package de.minestar.cok.itemblock;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockSocket extends ItemBlock {

	public static final String[] subNames = {"black", "darkBlue", "darkGreen", "darkAqua", "darkRed", "purple", "gold", "gray", "darkGray", "blue", "green", "aqua", "red", "lightPurple", "yellow", "white"};

	public ItemBlockSocket(int id) {
		super(id);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
	}

	@Override
	public int getMetadata(int damageValue) {
		return damageValue;
	}

}
