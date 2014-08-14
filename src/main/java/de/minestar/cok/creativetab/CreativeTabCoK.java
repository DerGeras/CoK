package de.minestar.cok.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import de.minestar.cok.item.CoKItem;
import de.minestar.cok.reference.Reference;

public class CreativeTabCoK {
	
	public static final CreativeTabs COK_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase()){
													@Override
													public Item getTabIconItem(){
														return CoKItem.itemBolt;
													}
												};

}
