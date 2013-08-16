package de.minestar.cok.hook;

import java.util.EnumSet;

import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.game.CoKGame;

public class PlayerTickHandler implements ITickHandler {
	
	private final EnumSet<TickType> ticksToGet;
	
	public PlayerTickHandler(EnumSet<TickType> ticksToGet){
		this.ticksToGet = ticksToGet;
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT){
			for(String spectator : CoKGame.spectators){
				//TODO find a better way to fix this
				MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(spectator).setInvisible(true);
			}
		}
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
