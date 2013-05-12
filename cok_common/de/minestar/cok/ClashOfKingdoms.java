package de.minestar.cok;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import de.minestar.cok.handler.PacketHandler;
import de.minestar.cok.references.Reference;


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
		
	}
	
	/**
	 * Called after all Mods have been loaded.
	 */
	@PostInit
	public void postInit(FMLPostInitializationEvent event){
		
	}
	
}
