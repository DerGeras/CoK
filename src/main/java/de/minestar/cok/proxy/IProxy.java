package de.minestar.cok.proxy;

import cpw.mods.fml.common.network.IGuiHandler;

public interface IProxy extends IGuiHandler{
	
	/**
	 * Called on pre-initialization
	 */
	public void preInit();
	
	/**
	 * Called on initialization
	 */
	public void init();
	
}
