package de.minestar.cok;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import de.minestar.cok.command.CommandCoK;
import de.minestar.cok.command.CommandPlayer;
import de.minestar.cok.command.CommandTeam;
import de.minestar.cok.handler.ConfigurationHandler;
import de.minestar.cok.hook.BlockHandler;
import de.minestar.cok.hook.PlayerTracker;
import de.minestar.cok.init.ModBlocks;
import de.minestar.cok.init.ModItems;
import de.minestar.cok.init.Recipes;
import de.minestar.cok.network.NetworkHandler;
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
		
		//register EventHandlers
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
		MinecraftForge.EVENT_BUS.register(new BlockHandler());
		FMLCommonHandler.instance().bus().register(new PlayerTracker());
		
		//register items
		ModItems.init();
		
		//register blocks
		ModBlocks.init();
		
		//initialize recipes
		Recipes.init();
		
		//initialize network
		NetworkHandler.preInit();

		LogHelper.info("Pre Initialization complete");
	}
	
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event){
		//register item renderers
		proxy.registerItemRenderers();
		
		LogHelper.info("Initialization complete");
	}
	
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
		LogHelper.info("Post Initialization complete");
	}
	
	@Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e){
		e.registerServerCommand(new CommandCoK());
		e.registerServerCommand(new CommandTeam());
		e.registerServerCommand(new CommandPlayer());
	}

}
