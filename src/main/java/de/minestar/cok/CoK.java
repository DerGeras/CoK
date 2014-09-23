package de.minestar.cok;

import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.chunkloadcallback.SocketChunkLoadCallBack;
import de.minestar.cok.client.gui.overlay.CoKGuiOverlay;
import de.minestar.cok.client.keybinding.KeyListener;
import de.minestar.cok.command.CommandCoK;
import de.minestar.cok.command.CommandInfo;
import de.minestar.cok.command.CommandPlayer;
import de.minestar.cok.command.CommandProtect;
import de.minestar.cok.command.CommandScore;
import de.minestar.cok.command.CommandSetSpawn;
import de.minestar.cok.command.CommandTeam;
import de.minestar.cok.config.ConfigurationHandler;
import de.minestar.cok.init.ModBlocks;
import de.minestar.cok.init.ModItems;
import de.minestar.cok.init.ModTileEntities;
import de.minestar.cok.init.Recipes;
import de.minestar.cok.listener.BlockListener;
import de.minestar.cok.listener.PlayerTracker;
import de.minestar.cok.listener.ServerTickListener;
import de.minestar.cok.listener.WorldLoadListener;
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
	public static CoK instance;
	
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS,
				serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;
	
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event){
		//proxy pre-initialization
		proxy.preInit();
		
		//config stuff
		ConfigurationHandler.preInit(event.getSuggestedConfigurationFile());
		
		//register EventHandlers
		registerEventHandlers();
		
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
		//proxy initialization
		proxy.init();
		
		//register tile entities
		ModTileEntities.init();
		
		//Chunk loading
		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new SocketChunkLoadCallBack());
		
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
		e.registerServerCommand(new CommandSetSpawn());
		e.registerServerCommand(new CommandProtect());
		e.registerServerCommand(new CommandScore());
		e.registerServerCommand(new CommandInfo());
	}
	
	private void registerEventHandlers(){
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
		MinecraftForge.EVENT_BUS.register(new BlockListener());
		PlayerTracker playerTracker = new PlayerTracker();
		FMLCommonHandler.instance().bus().register(playerTracker);
		MinecraftForge.EVENT_BUS.register(playerTracker);
		MinecraftForge.EVENT_BUS.register(new WorldLoadListener());
		FMLCommonHandler.instance().bus().register(new ServerTickListener());
		
		//Clientsided
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			MinecraftForge.EVENT_BUS.register(new CoKGuiOverlay());
			FMLCommonHandler.instance().bus().register(new KeyListener());
		}
	}

}
