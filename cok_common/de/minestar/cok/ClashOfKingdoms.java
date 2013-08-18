package de.minestar.cok;

import java.util.EnumSet;

import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.Mod.ServerStopping;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.block.BlockSocket;
import de.minestar.cok.block.BlockTowerBrick;
import de.minestar.cok.command.CommandCok;
import de.minestar.cok.command.CommandCreateTeam;
import de.minestar.cok.command.CommandKit;
import de.minestar.cok.command.CommandPlayer;
import de.minestar.cok.command.CommandRemoveTeam;
import de.minestar.cok.command.CommandTeams;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.hook.BlockListener;
import de.minestar.cok.hook.ChatListener;
import de.minestar.cok.hook.PlayerListener;
import de.minestar.cok.hook.PlayerTickHandler;
import de.minestar.cok.hook.PlayerTracker;
import de.minestar.cok.item.ItemCoKHelmet;
import de.minestar.cok.item.ItemCoKTorso;
import de.minestar.cok.itemblock.ItemBlockSocket;
import de.minestar.cok.keyhandler.CoKKeyHandler;
import de.minestar.cok.network.PacketHandler;
import de.minestar.cok.proxy.CommonProxy;
import de.minestar.cok.references.Reference;
import de.minestar.cok.weapon.ItemCrossBow;
import de.minestar.cok.weapon.ItemWarhammer;


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
    public static int warhammerID;
    
    public static int cokHelmetID;
    public static int cokTorsoID;
    
    //Items
    public static Item crossBowItem;
    public static Item warhammerItem;
    
    public static Item cokHelmetItem;
    public static Item cokTorsoItem;
    
    
    //General creativeTabs
    public static CreativeTabs cokBlockTab = new CreativeTabs("Clash of Kingdoms Blocks") {
        public ItemStack getIconItemStack() {
            return new ItemStack(towerBrickBlock, 1, 0);
        }
    };
    public static CreativeTabs cokItemTab = new CreativeTabs("Clash of Kingdoms Items") {
            public ItemStack getIconItemStack() {
                    return new ItemStack(crossBowItem, 1, 0);
            }
    };
    
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
    	event.registerServerCommand(new CommandCok());
    	event.registerServerCommand(new CommandKit());
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
		instance = this;
		NetworkRegistry.instance().registerGuiHandler(this, proxy);
		
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
		MinecraftForge.EVENT_BUS.register(new PlayerListener());
		
		GameRegistry.registerPlayerTracker(new PlayerTracker());
		TickRegistry.registerTickHandler(new PlayerTickHandler(EnumSet.of(TickType.PLAYER)), Side.CLIENT);
		
		//init KeyBindings
		KeyBinding[] key = {new KeyBinding(Reference.CoKMenuKey, Keyboard.KEY_G)};
        boolean[] repeat = {false};
        KeyBindingRegistry.registerKeyBinding(new CoKKeyHandler(key, repeat));
		
		//Name Creative tabs
		LanguageRegistry.instance().addStringLocalization("itemGroup.Clash of Kingdoms Blocks", "en_US", "CoK Blocks");
		LanguageRegistry.instance().addStringLocalization("itemGroup.Clash of Kingdoms Items", "en_US", "CoK Items");
		
		registerBlocks();
		
		registerItems();
		
		proxy.registerTileEntites();
		proxy.registerRenderThings();
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
		warhammerID = config.getItem(Configuration.CATEGORY_ITEM, "Warhammer", 5001).getInt();
		
		cokHelmetID = config.getItem(Configuration.CATEGORY_ITEM, "CoKHelmet", 5020).getInt();
		cokTorsoID = config.getItem(Configuration.CATEGORY_ITEM, "CoKTorso", 5021).getInt();
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
		//register warhammer
		warhammerItem = new ItemWarhammer(warhammerID, EnumToolMaterial.IRON);
		LanguageRegistry.addName(warhammerItem, "Warhammer");
		
		//register cokHelmet
		cokHelmetItem = new ItemCoKHelmet(cokHelmetID, EnumArmorMaterial.IRON);
		LanguageRegistry.addName(cokHelmetItem, "CoK Helmet");
		//register cokTorso
		cokTorsoItem = new ItemCoKTorso(cokTorsoID, EnumArmorMaterial.IRON);
		LanguageRegistry.addName(cokTorsoItem, "CoK Torso");
	}

}
