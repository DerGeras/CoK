package de.minestar.cok.hook;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.keyhandler.CoKKeyHandler;
import de.minestar.cok.references.Reference;

public class PlayerTickHandler implements ITickHandler {
	
	private final EnumSet<TickType> ticksToGet;
	
	public PlayerTickHandler(EnumSet<TickType> ticksToGet){
		this.ticksToGet = ticksToGet;
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if(CoKKeyHandler.keyPressed){
			EntityPlayer player = (EntityPlayer) tickData[0];
			player.openGui(ClashOfKingdoms.instance, Reference.COK_GUI_ID, player.worldObj, 0, 0, 0);
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
