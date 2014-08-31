package de.minestar.cok.preloader;

import java.util.HashMap;

public class PreloaderReference {
	
	public static final String MOD_CONTAINER_CLASS = "de.minestar.cok.preloader.CoKCoreModContainer";
	public static final String PRELOADER_CLASS = "de.minestar.cok.preloader.CoKPreloader";
	public static final String TRANSFORMER_CLASS = "de.minestar.cok.preloader.asm.CoKAccessTransformer";
	public static final String EVENT_FACTORY_JAVA_CLASS = "de/minestar/cok/event/EventFactory";

	public static final String[] transformers = { "de.minestar.cok.preloader.asm.EventAdder" };
	
	// obfuscated ItemStack with method tryPlaceItemInWorld
    public static final HashMap<String, String> ISobf = new HashMap<String, String>();
    //unobsucate ItemStack with method tryPlaceItemInWorld
    public static final HashMap<String, String> ISdeobf = new HashMap<String, String>();// ItemStack, DEV, placeEvent
	
    static{
    	ISobf.put("itemStackJavaClassName", "yd");
    	ISobf.put("itemStackClassName", "yd");
    	ISobf.put("targetMethod", "a");
    	ISobf.put("entityPlayerJavaClassName", "ue");
    	ISobf.put("worldJavaClassName", "abv");
    	
    	ISdeobf.put("itemStackJavaClassName", "net/minecraft/item/ItemStack");
    	ISdeobf.put("itemStackClassName", "net.minecraft.item.ItemStack");
    	ISdeobf.put("targetMethod", "tryPlaceItemIntoWorld");
    	ISdeobf.put("entityPlayerJavaClassName", "net/minecraft/entity/player/EntityPlayer");
    	ISdeobf.put("worldJavaClassName", "net/minecraft/world/World");
    }
    
}
