package de.minestar.cok.init;

import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import de.minestar.cok.item.CoKItem;
import de.minestar.cok.item.ItemBolt;
import de.minestar.cok.item.ItemDough;
import de.minestar.cok.item.ItemFlour;
import de.minestar.cok.reference.Reference;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {
	
	
	public static final CoKItem dough = new ItemDough();
	public static final CoKItem flour = new ItemFlour();
	public static final CoKItem bolt = new ItemBolt();
	
	
	public static void init(){
		GameRegistry.registerItem(dough, "dough");
		OreDictionary.registerOre("itemDough", dough);
		GameRegistry.registerItem(flour, "flour");
		OreDictionary.registerOre("itemFlour", flour);
		GameRegistry.registerItem(bolt, "bolt");
	}
	
}
