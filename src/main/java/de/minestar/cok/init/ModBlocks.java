package de.minestar.cok.init;

import cpw.mods.fml.common.registry.GameRegistry;
import de.minestar.cok.block.BlockSocket;
import de.minestar.cok.block.BlockTowerBrick;
import de.minestar.cok.block.CoKBlock;
import de.minestar.cok.block.CoKBlockContainer;
import de.minestar.cok.reference.Reference;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {
	
	public static final CoKBlock towerbrick = new BlockTowerBrick();
	public static final CoKBlockContainer socket = new BlockSocket();

	/**
	 * Register blocks on initialization
	 */
	public static void init(){
		GameRegistry.registerBlock(towerbrick, "towerbrick");
		GameRegistry.registerBlock(socket, "socket");
	}
	
}
