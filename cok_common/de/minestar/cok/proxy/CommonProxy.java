package de.minestar.cok.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import de.minestar.cok.ClashOfKingdoms;
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
	
	public void registerRecipes(){
		GameRegistry.addRecipe(new ItemStack(ClashOfKingdoms.boltItem, 8), "x", "x",
				'x', new ItemStack(Item.ingotIron));
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