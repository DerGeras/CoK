package de.minestar.cok.game.worlddata;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.game.CoKGameRegistry;
import de.minestar.cok.listener.ServerTickListener;
import de.minestar.cok.util.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class CoKGameWorldData extends WorldSavedData {
	
	private static final String DATA_KEY = "CoKGameWorldData";
	
	private static CoKGameWorldData data;
	
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
	
	public static void markDataDirty(){
		if(FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER){
			return;
		}
		if(data != null){
			data.markDirty();
		}
		ServerTickListener.isGameStateUpdated = true;
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
