package de.minestar.cok.proxy;

import cpw.mods.fml.common.network.NetworkRegistry;
import de.minestar.cok.CoK;
import de.minestar.cok.client.gui.CoKGUIInfo;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class CommonProxy implements IProxy{
	
	@Override
	public void preInit(){
		//register gui handlers
		NetworkRegistry.INSTANCE.registerGuiHandler(CoK.instance, this);		
	}

	@Override
	public void init(){
		//default: do nothing
	}
	
	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
		LogHelper.info("Hi");
		switch(guiId){
		case Reference.GUI_INFO_ID: return new CoKGUIInfo(player);
		default: return null;
		}
		
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
		switch(guiId){
		case Reference.GUI_INFO_ID: return new CoKGUIInfo(player);
		default: return null;
		}
	}
}
