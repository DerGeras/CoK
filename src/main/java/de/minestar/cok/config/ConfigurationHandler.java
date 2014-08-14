package de.minestar.cok.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import de.minestar.cok.util.LogHelper;

public class ConfigurationHandler {
	
	public static Configuration config;
	
	public static void init(File file){
		config = new Configuration(file);
		try{
			config.load();
			//read properties
			
		} catch(Exception e){
			LogHelper.error("Could not load configuration file");
		} finally {
			config.save();
		}
	}
	
}
