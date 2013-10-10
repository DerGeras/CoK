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
package de.minestar.cok.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.references.Reference;
import de.minestar.cok.tileentity.TileEntitySocket;

public class CommonProxy implements IGuiHandler{

	public void registerRenderThings(){
		
		// Currently empty.
	}
	
	public void registerTileEntites(){
		GameRegistry.registerTileEntity(TileEntitySocket.class, "tileSocket");
	}
	
	public void registerKeyHandlers(){
		//Empty
	}
	
	public void registerRecipes(Configuration config){
		//Crafting
		GameRegistry.addRecipe(new ItemStack(ClashOfKingdoms.boltItem, 8), "x", "x",
				'x', new ItemStack(Item.ingotIron));
		
		
		//easy bread recipe
		if(config.get(Reference.CATEGORY_RECIPE, "flour recipe", true).getBoolean(false)){
			GameRegistry.addShapelessRecipe(new ItemStack(ClashOfKingdoms.flourItem), new ItemStack(Item.wheat));
			GameRegistry.addSmelting(ClashOfKingdoms.flourItem.itemID, new ItemStack(Item.bread, 1), 0.15f);
		}
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}
	
}
