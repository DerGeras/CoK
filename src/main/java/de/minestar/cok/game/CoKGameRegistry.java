package de.minestar.cok.game;

import io.netty.buffer.ByteBuf;

import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;
import de.minestar.cok.game.worlddata.CoKGameWorldData;

public class CoKGameRegistry {

	public static HashMap<String, CoKGame> registeredGames = new HashMap<String, CoKGame>();
	
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
	 * should clear everything
	 */
	public static void clear(){
		registeredGames.clear();
		CoKPlayerRegistry.clearPlayers();
	}
	
	public static void readFromNBT(NBTTagCompound compound){
		clear();
		NBTTagList gameList = compound.getTagList("games", NBT.TAG_COMPOUND);
		for(int i = 0; i < gameList.tagCount(); i++){
			NBTTagCompound gameCompound = gameList.getCompoundTagAt(i);
			registerGame(new CoKGame(gameCompound));
		}
	}
	
	public static void writeToNBT(NBTTagCompound compound){
		NBTTagList gameList = new NBTTagList();
		for(CoKGame game : registeredGames.values()){
			NBTTagCompound gameCompound = new NBTTagCompound();
			game.writeToNBT(gameCompound);
			gameList.appendTag(gameCompound);
		}
		compound.setTag("games", gameList);
	}
	
	public static void writeToBuffer(ByteBuf buf){
		buf.writeInt(registeredGames.size());
		for(CoKGame game : registeredGames.values()){
			game.writeToBuffer(buf);
		}
	}
	
	public static void readFromBuffer(ByteBuf buf){
		clear();
		int gameCount =  buf.readInt();
		for(int i = 0; i < gameCount; i++){
			registerGame(new CoKGame(buf));
		}
	}
	
}
