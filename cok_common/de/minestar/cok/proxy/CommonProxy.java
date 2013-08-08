package de.minestar.cok.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import de.minestar.cok.tileentity.TileEntitySocket;

public class CommonProxy {

	public void registerRenderThings(){
		
		// Currently empty.
	}
	
	public void registerTileEntites(){
		GameRegistry.registerTileEntity(TileEntitySocket.class, "tileSocket");
	}
	
}
