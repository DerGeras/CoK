package de.minestar.cok.hook;

import java.util.EnumSet;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.game.CoKGame;

public class ServerTickHandler implements ITickHandler {
	
	private final EnumSet<TickType> ticksToGet;
	
	public static boolean isScoreCheckQueued = false;
	
	public ServerTickHandler(EnumSet<TickType> ticksToGet){
		this.ticksToGet = ticksToGet;
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		//currently nothing
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && isScoreCheckQueued){
			CoKGame.checkWinningCondition();
		}
		isScoreCheckQueued = false;
	}

	@Override
	public EnumSet<TickType> ticks() {
		return ticksToGet;
	}

	@Override
	public String getLabel() {
		return "ServerTickHandler";
	}

}
