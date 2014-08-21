package de.minestar.cok.game;

import java.util.HashMap;

import de.minestar.cok.game.worlddata.CoKGameWorldData;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class CoKGameRegistry {

	public static HashMap<String, CoKGame> registeredGames = new HashMap<String, CoKGame>();
	
	public static void registerGame(String name){
		registerGame(new CoKGame(name));
	}
	
	
	public static void registerGame(CoKGame game){
		CoKGameWorldData.data.markDirty();
		registeredGames.put(game.getName(), game);
	}
	
	public static void readFromNBT(NBTTagCompound compound){
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
	
}
