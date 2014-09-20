package de.minestar.cok.handler;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.minestar.cok.game.CoKGameRegistry;
import de.minestar.cok.game.GameSettings;
import de.minestar.cok.game.profession.Profession;
import de.minestar.cok.game.profession.ProfessionBarbarian;
import de.minestar.cok.game.profession.ProfessionCrossbowman;
import de.minestar.cok.game.profession.ProfessionKing;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.LogHelper;

public class ConfigurationHandler {
	
	public static final String CATEGORY_GAME_SETTINGS = "Game Settings";
	public static final String CATEGORY_LOCAL_SETTINGS = "Local Settings";
	public static final String CATEGORY_PROFESSIONS = "Professions";
	
	public static Configuration config;
	
	
	/**
	 * Called on pre-initialization
	 * 
	 * @param file - the configuration file
	 */
	public static void preInit(File file){
		if(config == null){
			config = new Configuration(file);
			loadConfiguration();
		}
		
	}
	
	private static void loadConfiguration(){
		try{
			config.load();
			
			//load game settings
			String buildingBlockName =
				config.getString("BuildingBlock", CATEGORY_GAME_SETTINGS, 
					Item.itemRegistry.getNameForObject(Item.getItemFromBlock(Blocks.stone)),
					"The towers will be made of these");
			GameSettings.defaultBuildingBlock = Block.getBlockFromName(buildingBlockName);
			
			GameSettings.buildingHeight =
				config.getInt("BuildingHeight", CATEGORY_GAME_SETTINGS, 12, 0, 128, "Building height for towers");
			
			GameSettings.protectedRadius =
				config.getInt("ProtectionRadius", CATEGORY_GAME_SETTINGS, 3, 0, 64, 
						"Square radius of protection around bases");
			
			//load professions
			if(config.getBoolean("Crossbowman", CATEGORY_PROFESSIONS, true, "Enables the crossbowman")){
				CoKGameRegistry.registeredProfessions.add(Profession.crossbowman);
			}
			if(config.getBoolean("Barbarian", CATEGORY_PROFESSIONS, true, "Enables the barbarian")){
				CoKGameRegistry.registeredProfessions.add(Profession.barbarian);
			}
			CoKGameRegistry.registeredProfessions.add(Profession.king);
			
		} catch(Exception e){
			LogHelper.error("Could not load configuration file");
		} finally {
			if(config.hasChanged()){
				config.save();
			}
		}
	}
	
	/**
	 * Called when the configuration is changed from the configuration GUI
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public void onConfigurationChanged(ConfigChangedEvent.OnConfigChangedEvent event){
		if(event.modID.equalsIgnoreCase(Reference.MOD_ID)){
			//reload config
			loadConfiguration();
		}
	}
	
}
