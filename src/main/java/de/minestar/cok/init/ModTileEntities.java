package de.minestar.cok.init;

import cpw.mods.fml.common.registry.GameRegistry;
import de.minestar.cok.tileentity.TileEntitySocket;

public class ModTileEntities {

	public static void init(){
		GameRegistry.registerTileEntity(TileEntitySocket.class, "tileEntitySocket");
	}
	
}
