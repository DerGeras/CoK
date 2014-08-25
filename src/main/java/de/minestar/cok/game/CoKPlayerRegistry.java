package de.minestar.cok.game;

import io.netty.buffer.ByteBuf;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class CoKPlayerRegistry {

	private static HashMap<UUID, CoKPlayer> allPlayers = new HashMap<UUID, CoKPlayer>();
	
	public static void addPlayer(CoKPlayer player){
		allPlayers.put(player.getUUID(), player);
	}
	
	public static void addPlayer(UUID uuid){
		addPlayer(new CoKPlayer(uuid));
	}
	
	public static void removePlayer(CoKPlayer player){
		allPlayers.remove(player);
	}
	
	public static Collection<CoKPlayer> getAllPlayers(){
		return allPlayers.values();
	}
	
	public static CoKPlayer getPlayerForName(String username){
		for(CoKPlayer player : allPlayers.values()){
			if(player.getUserName().equals(username)){
				return player;
			}
		}
		return null;
	}
	
	public static CoKPlayer getPlayerForUUID(UUID uuid){
		return allPlayers.get(uuid);
	}
	
	public static CoKPlayer getOrCreatPlayerForUUID(UUID uuid){
		if(allPlayers.containsKey(uuid)){
			return allPlayers.get(uuid);
		}
		CoKPlayer player = new CoKPlayer(uuid);
		allPlayers.put(uuid, player);
		return player;
	}
	
	public static void clearPlayers(){
		allPlayers.clear();
	}
	
	public static HashSet<CoKPlayer> getPlayersForUUIDs(Collection <UUID> uuids){
		HashSet<CoKPlayer> result = new HashSet<CoKPlayer>();
		for(UUID uuid : uuids){
			result.add(getPlayerForUUID(uuid));
		}
		return result;
	}

	public static void readFromNBT(NBTTagCompound compound) {
		//clear old data
		clearPlayers();
		//read players
		NBTTagList playerList = compound.getTagList("players", NBT.TAG_COMPOUND);
		for(int i = 0; i < playerList.tagCount(); i++){
			addPlayer(new CoKPlayer(playerList.getCompoundTagAt(i)));
		}
	}

	public static void writeToNBT(NBTTagCompound compound) {
		//write players
		NBTTagList playerList = new NBTTagList();
		for(CoKPlayer player : allPlayers.values()){
			NBTTagCompound playerCompound = new NBTTagCompound();
			player.writeToNBT(playerCompound);
			playerList.appendTag(playerCompound);
		}
		compound.setTag("players", playerList);
	}

	public static void readFromBuffer(ByteBuf buf) {
		//clear old data
		clearPlayers();
		//read players
		int playerCount = buf.readInt();
		for(int i = 0; i < playerCount; i++){
			addPlayer(new CoKPlayer(buf));
		}
		
	}
	
	public static void writeToBuffer(ByteBuf buf){
		//write players
		buf.writeInt(allPlayers.size());
		for(CoKPlayer player : allPlayers.values()){
			player.writeToBuffer(buf);
		}
	}
}
