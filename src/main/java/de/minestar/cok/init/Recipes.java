package de.minestar.cok.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes {
	
	public static void init(){
		//recipe for bolts
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.bolt, 8), "x","x", 'x', ModItems.bolt);
		
		//recipe for flour
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.flour), new ItemStack(Items.wheat));
		//recipe for dough
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.dough, 14),
				"itemFlour", "itemFlour", "itemFlour", "itemFlour", "itemFlour", "itemFlour", "itemFlour",
				Blocks.brown_mushroom, Items.water_bucket));
		//new recipes for bread
		GameRegistry.addSmelting(ModItems.flour, new ItemStack(Items.bread), 0.15f);
		GameRegistry.addSmelting(ModItems.dough, new ItemStack(Items.bread), 0.20f);
	}

}
