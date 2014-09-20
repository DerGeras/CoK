package de.minestar.cok.worldguard.worlddata;

import de.minestar.cok.game.CoKGameRegistry;
import de.minestar.cok.util.LogHelper;
import de.minestar.cok.worldguard.Worldguard;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class WorldGuardWorldData extends WorldSavedData {
	
	public static final String DATA_KEY = "CoKWorldGuard";
	
	private static WorldGuardWorldData data;
	
	/**
	 * Initializing data
	 */
	public static void onWorldLoad(World world){
		//Overworld
		if(world.provider.dimensionId == 0){
			LogHelper.info("Registering CoK worldguard data...");
			MapStorage storage = world.perWorldStorage;
			data = (WorldGuardWorldData)storage.loadData(WorldGuardWorldData.class, DATA_KEY);
			if(data == null){
				data = new WorldGuardWorldData();
				storage.setData(DATA_KEY, data);
			}
		}
	}
	
	/**
	 * Marks the data as dirty, if not called the data will not be saved!
	 */
	public static void markDataDirty(){
		if(data != null){
			data.markDirty();
		}
	}

	public WorldGuardWorldData() {
		super(DATA_KEY);
	}
	
	//must be present
	public WorldGuardWorldData(String key){
		super(key); 
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		LogHelper.info("Loading worldguard data from world...");
		Worldguard.readFromNBT(compound);

	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		LogHelper.info("Saving worldguard data to world...");
		Worldguard.writeToNBT(compound);
	}

}
