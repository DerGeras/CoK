package de.minestar.cok;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import de.minestar.cok.block.CoKBlock;
import de.minestar.cok.handler.ConfigurationHandler;
import de.minestar.cok.init.ModBlocks;
import de.minestar.cok.init.ModItems;
import de.minestar.cok.proxy.IProxy;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.LogHelper;

@Mod( modid = Reference.MOD_ID,
		name = Reference.MOD_NAME,
		version = Reference.VERSION,
		guiFactory = Reference.GUI_FACTORY_CLASS)
public class CoK {
	
	
	@Mod.Instance(Reference.MOD_ID)
	public CoK instance;
	
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS,
				serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;
	
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event){
		//config stuff
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
		
		//register items
		ModItems.init();
		
		//register blocks
		ModBlocks.init();
		
		LogHelper.info("Pre Initialization complete");
	}
	
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event){
		
		LogHelper.info("Initialization complete");
	}
	
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
		LogHelper.info("Post Initialization complete");
	}

}
