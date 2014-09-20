package de.minestar.cok.init;

import cpw.mods.fml.common.registry.GameRegistry;
import de.minestar.cok.tileentity.TileEntitySocket;

public class ModTileEntities {

	/**
	 * Register tile entities on initialization
	 */
	public static void init(){
		GameRegistry.registerTileEntity(TileEntitySocket.class, "tileEntitySocket");
	}
	
}
