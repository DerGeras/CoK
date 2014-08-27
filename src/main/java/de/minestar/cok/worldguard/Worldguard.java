package de.minestar.cok.worldguard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import de.minestar.cok.util.LogHelper;
import de.minestar.cok.worldguard.worlddata.WorldGuardWorldData;

import joptsimple.util.KeyValuePair;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.Constants.NBT;

public class Worldguard {

	public static HashMap<Integer, HashSet<WorldRegion>> protectedRegions = new HashMap<Integer, HashSet<WorldRegion>>();
	
	/**
	 * checks whether the given position is protected
	 * @param coordinates
	 * @return
	 */
	public static boolean isProtected(int dimensionID, ChunkCoordinates coordinates){
		return isProtected(dimensionID, coordinates.posX, coordinates.posY, coordinates.posZ);
	}
	
	/**
	 * checks whether the given position is protected
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static boolean isProtected(int dimensionID, int x, int y, int z){
		boolean res = false;
		HashSet<WorldRegion> dimRegions = protectedRegions.get(dimensionID);
		if(dimRegions != null){	
			for(WorldRegion r : dimRegions){
				res = res || r.isInRegion(x, y, z);
			}
		}
		return res;
	}
	
	public static void addProtectedRegion(int dimensionID, WorldRegion region){
		HashSet<WorldRegion> dimRegions = protectedRegions.get(dimensionID);
		if(dimRegions == null){
			dimRegions = new HashSet<WorldRegion>();
			protectedRegions.put(dimensionID, dimRegions);
		}
		dimRegions.add(region);
		WorldGuardWorldData.markDataDirty();
	}
	
	public static void clearProtectedRegions(){
		protectedRegions.clear();
		WorldGuardWorldData.markDataDirty();
	}
	
	public static void clearProtectedRegionsFromDim(int dimensionID){
		protectedRegions.remove(dimensionID);
		WorldGuardWorldData.markDataDirty();
	}
	
	/**
	 * read protected regions from nbt
	 * @param compound
	 */
	public static void readFromNBT(NBTTagCompound compound){
		NBTTagList dimensionList = compound.getTagList("dimensions", NBT.TAG_COMPOUND);
		//load dimensions
		for(int i = 0; i < dimensionList.tagCount(); i++){
			NBTTagCompound dimCompound = dimensionList.getCompoundTagAt(i);
			int dim = dimCompound.getInteger("dimensionID");
			NBTTagList regionList = dimCompound.getTagList("regions", NBT.TAG_COMPOUND);
			//load regions
			for(int j = 0; j < regionList.tagCount(); j++){
				NBTTagCompound regionCompound = regionList.getCompoundTagAt(j);
				NBTTagCompound posACompound = regionCompound.getCompoundTag("posA");
				ChunkCoordinates posA = new ChunkCoordinates(
						posACompound.getInteger("posX"),
						posACompound.getInteger("posY"),
						posACompound.getInteger("posZ"));
				NBTTagCompound posBCompound = regionCompound.getCompoundTag("posB");
				ChunkCoordinates posB = new ChunkCoordinates(
						posBCompound.getInteger("posX"),
						posBCompound.getInteger("posY"),
						posBCompound.getInteger("posZ"));
				//register region
				addProtectedRegion(dim, new WorldRegion(posA, posB));
			}
		}
	}
	
	/**
	 * write protected regions to nbt
	 * @param compound
	 */
	public static void writeToNBT(NBTTagCompound compound){
		NBTTagList dimensionList = new NBTTagList();
		//write dimensions
		for(Map.Entry<Integer, HashSet<WorldRegion>> dimRegions : protectedRegions.entrySet()){
			NBTTagCompound dimCompound = new NBTTagCompound();
			dimCompound.setInteger("dimensionID", dimRegions.getKey());
			//write regions
			NBTTagList regionList = new NBTTagList();
			for(WorldRegion region : dimRegions.getValue()){
				NBTTagCompound regionCompound = new NBTTagCompound();
				//write posA
				NBTTagCompound posACompound = new NBTTagCompound();
				posACompound.setInteger("posX", region.getPosA().posX);
				posACompound.setInteger("posY", region.getPosA().posY);
				posACompound.setInteger("posZ", region.getPosA().posZ);
				regionCompound.setTag("posA", posACompound);
				//write posB
				NBTTagCompound posBCompound = new NBTTagCompound();
				posBCompound.setInteger("posX", region.getPosB().posX);	
				posBCompound.setInteger("posY", region.getPosB().posY);
				posBCompound.setInteger("posZ", region.getPosB().posZ);
				regionCompound.setTag("posB", posBCompound);
				regionList.appendTag(regionCompound);
			}
			dimCompound.setTag("regions", regionList);
			dimensionList.appendTag(dimCompound);
		}
		compound.setTag("dimensions", dimensionList);
	}
	
}
