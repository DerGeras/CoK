/*******************************************************************************
 * Clash of Kingdoms Minecraft Mod
 *     Copyright (C) 2013 Geras
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package de.minestar.cok;

import java.util.EnumSet;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
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
import de.minestar.cok.command.CommandPlayer;
import de.minestar.cok.command.CommandRemoveTeam;
import de.minestar.cok.command.CommandScore;
import de.minestar.cok.command.CommandSpectatorSpawn;
import de.minestar.cok.command.CommandTeamChat;
import de.minestar.cok.command.CommandTeamSpawn;
import de.minestar.cok.command.CommandTeams;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.hook.BlockListener;
import de.minestar.cok.hook.ChatListener;
import de.minestar.cok.hook.PlayerListener;
import de.minestar.cok.hook.PlayerTracker;
import de.minestar.cok.hook.ServerTickHandler;
import de.minestar.cok.item.ItemBolt;
import de.minestar.cok.item.ItemDough;
import de.minestar.cok.item.ItemFlour;
import de.minestar.cok.itemblock.ItemBlockSocket;
import de.minestar.cok.loader.SocketChunkLoadCallback;
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
    public static int boltID;
    public static int flourID;
    public static int doughID;
    
    public static int cokHelmetID;
    public static int cokTorsoID;
    
    //Items
    public static Item crossBowItem;
    public static Item warhammerItem;
    public static Item boltItem;
    public static Item flourItem;
    public static Item doughItem;
    
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
    	event.registerServerCommand(new CommandTeamSpawn());
    	event.registerServerCommand(new CommandSpectatorSpawn());
    	event.registerServerCommand(new CommandTeamChat());
    	event.registerServerCommand(new CommandScore());
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
		TickRegistry.registerTickHandler(new ServerTickHandler(EnumSet.of(TickType.SERVER)), Side.SERVER);
		
		//Name Creative tabs
		LanguageRegistry.instance().addStringLocalization("itemGroup.Clash of Kingdoms Blocks", "en_US", "CoK Blocks");
		LanguageRegistry.instance().addStringLocalization("itemGroup.Clash of Kingdoms Items", "en_US", "CoK Items");
		
		registerBlocks();
		
		registerItems();
		
		proxy.registerRecipes(config);
		
		proxy.registerTileEntites();
		proxy.registerRenderThings();
		//init KeyBindings
		proxy.registerKeyHandlers();
		
		//Chunk loading
		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new SocketChunkLoadCallback());
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
		boltID = config.getItem(Configuration.CATEGORY_ITEM, "Bolt", 5010).getInt();
		flourID = config.getItem(Configuration.CATEGORY_ITEM, "Flour", 5011).getInt();
		doughID = config.getItem(Configuration.CATEGORY_ITEM, "Dough", 5012).getInt();
		
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
		//register bolt
		boltItem = new ItemBolt(boltID);
		LanguageRegistry.addName(boltItem, "Bolt");
		//register flour
		flourItem = new ItemFlour(flourID);
		LanguageRegistry.addName(flourItem, "Flour");
		OreDictionary.registerOre("itemFlour", new ItemStack(flourItem));
		//register dough
		doughItem = new ItemDough(doughID);
		LanguageRegistry.addName(doughItem, "Dough");
		OreDictionary.registerOre("itemDough", new ItemStack(doughItem));
		
//		//register cokHelmet
//		cokHelmetItem = new ItemCoKHelmet(cokHelmetID, EnumArmorMaterial.IRON);
//		LanguageRegistry.addName(cokHelmetItem, "CoK Helmet");
//		//register cokTorso
//		cokTorsoItem = new ItemCoKTorso(cokTorsoID, EnumArmorMaterial.IRON);
//		LanguageRegistry.addName(cokTorsoItem, "CoK Torso");
	}
	
}
