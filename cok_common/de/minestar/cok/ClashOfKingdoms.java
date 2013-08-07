package de.minestar.cok;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
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
import cpw.mods.fml.common.registry.LanguageRegistry;
import de.minestar.cok.command.CreateTeamCommand;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.handler.PacketHandler;
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
    
    //Items
    public static Item crossBowItem;
    
    //Item IDs
    public static int crossBowID;
    
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
		proxy.registerRenderThings();
		
		registerItems();
	}
	
	/**
	 * Called after all Mods have been loaded.
	 */
	@PostInit
	public void postInit(FMLPostInitializationEvent event){
		
	}
	
	private void initItemIDs(){
		crossBowID = config.getItem(Configuration.CATEGORY_ITEM, "Crossbow", 5000).getInt();
	}
	
	private void registerItems(){
		//register crossbow
		crossBowItem = new ItemCrossBow(crossBowID).setUnlocalizedName("crossbow");
		LanguageRegistry.addName(crossBowItem, "Crossbow");
	}
	
}
