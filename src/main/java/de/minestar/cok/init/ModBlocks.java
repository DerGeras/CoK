package de.minestar.cok.init;

import cpw.mods.fml.common.registry.GameRegistry;
import de.minestar.cok.block.BlockTowerBrick;
import de.minestar.cok.block.CoKBlock;
import de.minestar.cok.reference.Reference;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {
	
	public static final CoKBlock towerBrick = new BlockTowerBrick();

	
	public static void init(){
		GameRegistry.registerBlock(towerBrick, "towerbrick");
	}
	
}
