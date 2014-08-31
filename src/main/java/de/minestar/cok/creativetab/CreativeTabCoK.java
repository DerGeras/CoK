package de.minestar.cok.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.minestar.cok.init.ModBlocks;
import de.minestar.cok.init.ModItems;
import de.minestar.cok.reference.Reference;

public class CreativeTabCoK {
	
	public static final CreativeTabs COK_ITEM_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase() + ".items"){
													@Override
													@SideOnly(Side.CLIENT)
													public Item getTabIconItem(){
														return ModItems.bolt;
													}
												};
												
	public static final CreativeTabs COK_BLOCK_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase() + ".blocks"){
													@Override
													@SideOnly(Side.CLIENT)
													public Item getTabIconItem(){
														return Item.getItemFromBlock(ModBlocks.towerbrick);
													}
												};

}
