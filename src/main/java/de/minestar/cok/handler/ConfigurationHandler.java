package de.minestar.cok.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.LogHelper;

public class ConfigurationHandler {
	
	public static final String CATEGORY_GAME_SETTINGS = "Game Settings";
	public static final String CATEGORY_LOCAL_SETTINGS = "Local Settings";
	
	public static Configuration config;
	
	public static void init(File file){
		if(config == null){
			config = new Configuration(file);
			loadConfiguration();
		}
		
	}
	
	private static void loadConfiguration(){
		try{
			config.load();
			//read properties
			
		} catch(Exception e){
			LogHelper.error("Could not load configuration file");
		} finally {
			if(config.hasChanged()){
				config.save();
			}
		}
	}
	
	@SubscribeEvent
	public void onConfigurationChanged(ConfigChangedEvent.OnConfigChangedEvent event){
		if(event.modID.equalsIgnoreCase(Reference.MOD_ID)){
			//reload config
			loadConfiguration();
		}
	}
	
}
