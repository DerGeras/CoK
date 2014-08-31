package de.minestar.cok.game.worlddata;

import de.minestar.cok.game.CoKGameRegistry;
import de.minestar.cok.util.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class CoKGameWorldData extends WorldSavedData {
	
	public static final String DATA_KEY = "CoKGameWorldData";
	
	public static CoKGameWorldData data;
	
	/**
	 * Initializing data
	 */
	public static void onWorldLoad(World world){
		//Overworld
		if(world.provider.dimensionId == 0){
			LogHelper.info("Registering CoK game data...");
			MapStorage storage = world.perWorldStorage;
			data = (CoKGameWorldData)storage.loadData(CoKGameWorldData.class, DATA_KEY);
			if(data == null){
				data = new CoKGameWorldData();
				storage.setData(DATA_KEY, data);
			}
		}
	}

	public CoKGameWorldData() {
		super(DATA_KEY);
	}
	
	//must be present
	public CoKGameWorldData(String key){
		super(key); 
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		LogHelper.info("Loading CoK game data from world...");
		CoKGameRegistry.readFromNBT(compound);

	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		LogHelper.info("Saving CoK game data to world...");
		CoKGameRegistry.writeToNBT(compound);
	}

}
