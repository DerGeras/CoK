package de.minestar.cok.game;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;

public class CoKGame {
	
	public static LinkedList<Team> teams = new LinkedList<Team>();
	
	public static void initGame(Configuration config){
		Settings.defaultbuildingBlockID = config.get(Configuration.CATEGORY_GENERAL, "Building Block ID", Block.stone.blockID).getInt();
		Settings.buildingHeight = config.get(Configuration.CATEGORY_GENERAL, "Building Height", 25).getInt();
		
		if(config.hasChanged()){
			config.save();
		}
	}
	
}
