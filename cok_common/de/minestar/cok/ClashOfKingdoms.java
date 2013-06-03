package de.minestar.cok;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
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
    
    //General creativeTab
    public static CreativeTabs cokTab = new CreativeTabs(CreativeTabs.getNextID(), Reference.MOD_ID);
    
	/**
	 * Called before the mod is actually loaded
	 */
	@PreInit
	public void preInit(FMLPreInitializationEvent event){
		
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
	
	private void registerItems(){
		crossBowItem = new ItemCrossBow(5000);
	}
	
}
