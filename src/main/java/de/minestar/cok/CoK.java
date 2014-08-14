package de.minestar.cok;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import de.minestar.cok.config.ConfigurationHandler;
import de.minestar.cok.proxy.IProxy;
import de.minestar.cok.reference.Reference;

@Mod( modid = Reference.MOD_ID,
		name = Reference.MOD_NAME,
		version = Reference.VERSION)
public class CoK {
	
	@Mod.Instance(Reference.MOD_ID)
	public CoK instance;
	
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS,
				serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;
	
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event){
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
	}
	
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event){
		
	}
	
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}

}
