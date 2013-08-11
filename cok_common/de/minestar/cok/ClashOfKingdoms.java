package de.minestar.cok;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.Mod.ServerStopping;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import de.minestar.cok.block.BlockSocket;
import de.minestar.cok.block.BlockTowerBrick;
import de.minestar.cok.command.CommandCreateTeam;
import de.minestar.cok.command.CommandPlayer;
import de.minestar.cok.command.CommandRemoveTeam;
import de.minestar.cok.command.CommandTeams;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.handler.PacketHandler;
import de.minestar.cok.hook.BlockListener;
import de.minestar.cok.hook.ChatListener;
import de.minestar.cok.itemblock.ItemBlockSocket;
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
    
    //MetadatablockNames
    public static final String[] socketBlockNames = {
    	"Black Socket", "Dark Blue Socket", "Dark Green Socket", "Dark Aqua Socket",
    	"Dark Red Socket", "Purple Socket", "Gold Socket", "Gray Socket", "Dark Gray Socket",
    	"Blue Socket", "Green Socket", "Aqua Socket", "Red Socket", "Light Purple Socket",
    	"Yellow Socket", "White Socket"
    	};
    
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
    	event.registerServerCommand(new CommandCreateTeam());
    	event.registerServerCommand(new CommandRemoveTeam());
    	event.registerServerCommand(new CommandPlayer());
    	event.registerServerCommand(new CommandTeams());
    }
    
    @ServerStopping
    public void onServerStopping(FMLServerStoppingEvent event){
    	CoKGame.cleanUpGame();
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
		MinecraftForge.EVENT_BUS.register(new ChatListener());
		MinecraftForge.EVENT_BUS.register(new BlockListener());
		
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
		//initialize the game
    	CoKGame.initGame(config);
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
		GameRegistry.registerBlock(socketBlock, ItemBlockSocket.class, socketBlock.getUnlocalizedName());
		for (int ix = 0; ix < 16; ix++) {
			ItemStack socketBlockStack = new ItemStack(socketBlock, 1, ix);
			
			LanguageRegistry.addName(socketBlockStack, socketBlockNames[socketBlockStack.getItemDamage()]);
		}
		
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
