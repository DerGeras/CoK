package de.minestar.cok.game;

import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.Constants.NBT;
import de.minestar.cok.game.profession.Profession;
import de.minestar.cok.game.worlddata.CoKGameWorldData;

public class CoKGameRegistry {

	public static HashMap<String, CoKGame> registeredGames = new HashMap<String, CoKGame>();
	public static HashSet<Profession> registeredProfessions = new HashSet<Profession>();
	
	private static ChunkCoordinates generalSpawn;
	
	public static void registerGame(String name){
		registerGame(new CoKGame(name));
	}
	
	
	public static void registerGame(CoKGame game){
		if(CoKGameWorldData.data != null){
			CoKGameWorldData.data.markDirty();
		}
		registeredGames.put(game.getName(), game);
	}
	
	/**
	 * should clear everything, games, teams, players
	 */
	public static void clearAll(){
		registeredGames.clear();
		TeamRegistry.clearTeams();
		CoKPlayerRegistry.clearPlayers();
	}
	
	/**
	 * clear games
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
		NBTTagList gameList = compound.getTagList("games", NBT.TAG_COMPOUND);
		for(int i = 0; i < gameList.tagCount(); i++){
			NBTTagCompound gameCompound = gameList.getCompoundTagAt(i);
			registerGame(new CoKGame(gameCompound));
		}
	}
	
	public static void writeToNBT(NBTTagCompound compound){
		CoKPlayerRegistry.writeToNBT(compound);
		TeamRegistry.writeToNBT(compound);
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
