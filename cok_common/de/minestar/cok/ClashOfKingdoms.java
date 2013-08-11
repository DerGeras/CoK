package de.minestar.cok;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import de.minestar.cok.block.BlockSocket;
import de.minestar.cok.block.BlockTowerBrick;
import de.minestar.cok.command.CreateTeamCommand;
import de.minestar.cok.command.PlayerCommand;
import de.minestar.cok.command.RemoveTeamCommand;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.handler.PacketHandler;
import de.minestar.cok.hook.ChatListener;
import de.minestar.cok.preloader.ValueMaps;
import de.minestar.cok.proxy.CommonProxy;
import de.minestar.cok.references.Reference;
import de.minestar.cok.weapon.ItemCrossBow;


@Mod(
	modid = Reference.MOD_ID,
	name = Reference.MOD_NAME,
	version = Reference.MOD_VERSION
)
@NetworkMod(
	clientSideRequired = true,
	serverSideRequired = false,
	channels={ Reference.CHANNEL_NAME },
	packetHandler = PacketHandler.class
)
public class ClashOfKingdoms {
	
	// Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide="de.minestar.cok.proxy.ClientProxy", serverSide="de.minestar.cok.proxy.CommonProxy")
    public static CommonProxy proxy;
	
    //Don't know why I would need this.
    @Instance
    public static ClashOfKingdoms instance;
    
    //Block IDs
    public static int socketID;
    public static int towerBrickID;
    
    //Blocks
    public static Block socketBlock;
    public static Block towerBrickBlock;
    
    //Item IDs
    public static int crossBowID;
    
    //Items
    public static Item crossBowItem;
    
    
    //General creativeTab
    public static CreativeTabs cokTab = new CreativeTabs(CreativeTabs.getNextID(), Reference.MOD_ID);
    
    //Configuration
    public static Configuration config;
    
    /**
     * Initialize the game on the server
     * @param event
     */
    @ServerStarting
   	public void onServerStarting(FMLServerStartingEvent event) {
    	event.registerServerCommand(new CreateTeamCommand());
    	event.registerServerCommand(new RemoveTeamCommand());
    	event.registerServerCommand(new PlayerCommand());
    	
    	//initialize the game
    	CoKGame.initGame(config);
    }
    
	/**
	 * Called before the mod is actually loaded
	 */
	@PreInit
	public void preInit(FMLPreInitializationEvent event){
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		initBlockIDs();
		initItemIDs();
		
		if(config.hasChanged()){
			config.save();
		}
	}
	
	/**
	 * Called when the mod gets loaded
	 */
	@Init
	public void init(FMLInitializationEvent event){
		ValueMaps.IIWMdev.get("hi");
		MinecraftForge.EVENT_BUS.register(new ChatListener());
		
		proxy.registerRenderThings();

		registerBlocks();
		
		registerItems();
		
		proxy.registerTileEntites();
	}
	
	/**
	 * Called after all Mods have been loaded.
	 */
	@PostInit
	public void postInit(FMLPostInitializationEvent event){
		
	}
	
	private void initBlockIDs(){
		socketID = config.getBlock(Configuration.CATEGORY_BLOCK, "Socket", 400).getInt();
		towerBrickID = config.getBlock(Configuration.CATEGORY_BLOCK, "Tower Brick", 401).getInt();
	}
	
	private void initItemIDs(){
		crossBowID = config.getItem(Configuration.CATEGORY_ITEM, "Crossbow", 5000).getInt();
	}
	
	private void registerBlocks(){
		//register socket
		socketBlock = new BlockSocket(socketID);
		LanguageRegistry.addName(socketBlock, "Socket");
		GameRegistry.registerBlock(socketBlock, socketBlock.getUnlocalizedName());
		
		//register Tower
		towerBrickBlock = new BlockTowerBrick(towerBrickID);
		LanguageRegistry.addName(towerBrickBlock, "Tower Brick");
		GameRegistry.registerBlock(towerBrickBlock, towerBrickBlock.getUnlocalizedName());
	}
	
	private void registerItems(){
		//register crossbow
		crossBowItem = new ItemCrossBow(crossBowID);
		LanguageRegistry.addName(crossBowItem, "Crossbow");
	}

}
