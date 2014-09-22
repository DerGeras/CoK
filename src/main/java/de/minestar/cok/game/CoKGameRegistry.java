package de.minestar.cok.game;

import io.netty.buffer.ByteBuf;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.Constants.NBT;

import com.sun.istack.internal.Nullable;

import de.minestar.cok.game.profession.Profession;
import de.minestar.cok.game.worlddata.CoKGameWorldData;

public class CoKGameRegistry {

	private static HashMap<String, CoKGame> registeredGames = new HashMap<String, CoKGame>();
	public static HashSet<Profession> registeredProfessions = new HashSet<Profession>();
	
	private static ChunkCoordinates generalSpawn;
	
	
	public static Collection<String> getAllGameNames(){
		return registeredGames.keySet();
	}
	
	public static Collection<CoKGame> getAllGames(){
		return registeredGames.values();
	}
	
	@Nullable
	public static CoKGame getGame(String gameName){
		return registeredGames.get(gameName);
	}
	
	public static void registerGame(String name){
		registerGame(new CoKGame(name));
	}
	
	
	public static ChunkCoordinates getGeneralSpawn() {
		return generalSpawn;
	}


	public static void setGeneralSpawn(ChunkCoordinates spawnLocation) {
		CoKGameWorldData.markDataDirty();
		CoKGameRegistry.generalSpawn = spawnLocation;
	}


	public static void registerGame(CoKGame game){
		CoKGameWorldData.markDataDirty();
		registeredGames.put(game.getName(), game);
	}
	
	public static void removeGame(String gameName){
		CoKGameWorldData.markDataDirty();
		registeredGames.remove(gameName);
	}
	
	public static void removeGame(CoKGame game){
		removeGame(game.getName());
	}
	
	/**
	 * Clears everything, games, teams, players
	 */
	public static void clearAll(){
		registeredGames.clear();
		TeamRegistry.clearTeams();
		CoKPlayerRegistry.clearPlayers();
	}
	
	/**
	 * Clear all registered games
	 */
	public static void clearGames(){
		for(CoKGame game : registeredGames.values()){
			game.clearTeams();
		}
		registeredGames.clear();
	}
	
	public static void readFromNBT(NBTTagCompound compound){
		clearGames();
		CoKPlayerRegistry.readFromNBT(compound);
		TeamRegistry.readFromNBT(compound);
		//read coordinates
		if(compound.hasKey("spawnX")){
			int posX = compound.getInteger("spawnX");
			int posY = compound.getInteger("spawnY");
			int posZ = compound.getInteger("spawnZ");
			generalSpawn = new ChunkCoordinates(posX, posY, posZ);
		}
		//read game list
		NBTTagList gameList = compound.getTagList("games", NBT.TAG_COMPOUND);
		for(int i = 0; i < gameList.tagCount(); i++){
			NBTTagCompound gameCompound = gameList.getCompoundTagAt(i);
			registerGame(new CoKGame(gameCompound));
		}
	}
	
	public static void writeToNBT(NBTTagCompound compound){
		CoKPlayerRegistry.writeToNBT(compound);
		TeamRegistry.writeToNBT(compound);
		//write spawncoords 
		if(generalSpawn != null){
			compound.setInteger("spawnX", generalSpawn.posX);
			compound.setInteger("spawnY", generalSpawn.posY);
			compound.setInteger("spawnZ", generalSpawn.posZ);
		}
		//write games
		NBTTagList gameList = new NBTTagList();
		for(CoKGame game : registeredGames.values()){
			NBTTagCompound gameCompound = new NBTTagCompound();
			game.writeToNBT(gameCompound);
			gameList.appendTag(gameCompound);
		}
		compound.setTag("games", gameList);
	}
	
	public static void readFromBuffer(ByteBuf buf){
		clearGames();
		CoKPlayerRegistry.readFromBuffer(buf);
		TeamRegistry.readFromBuffer(buf);
		int gameCount =  buf.readInt();
		for(int i = 0; i < gameCount; i++){
			registerGame(new CoKGame(buf));
		}
	}
	
	public static void writeToBuffer(ByteBuf buf){
		CoKPlayerRegistry.writeToBuffer(buf);
		TeamRegistry.writeToBuffer(buf);
		buf.writeInt(registeredGames.size());
		for(CoKGame game : registeredGames.values()){
			game.writeToBuffer(buf);
		}
	}
	
}
