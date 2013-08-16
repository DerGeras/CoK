package de.minestar.cok.hook;

import java.util.EnumSet;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class PlayerTickHandler implements ITickHandler {
	
	private final EnumSet<TickType> ticksToGet;
	
	public PlayerTickHandler(EnumSet<TickType> ticksToGet){
		this.ticksToGet = ticksToGet;
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		//TODO Auto-generated stuff

	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnumSet<TickType> ticks() {
		return ticksToGet;
	}

	@Override
	public String getLabel() {
		return "PlayerTickHandler";
	}

}
