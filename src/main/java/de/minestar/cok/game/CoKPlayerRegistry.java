package de.minestar.cok.game;

import io.netty.buffer.ByteBuf;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

import com.sun.istack.internal.Nullable;

public class CoKPlayerRegistry {

	private static HashMap<UUID, CoKPlayer> allPlayers = new HashMap<UUID, CoKPlayer>();
	
	public static void addPlayer(CoKPlayer player){
		allPlayers.put(player.getUUID(), player);
	}
	
	/**
	 * Creates a player with the given UUID and registers it
	 * 
	 * @param uuid
	 */
	public static void addPlayer(UUID uuid){
		addPlayer(new CoKPlayer(uuid));
	}
	
	/**
	 * Removes the given player from the registry
	 * 
	 * @param player
	 */
	public static void removePlayer(CoKPlayer player){
		if(player.getTeam() != null){
			player.getTeam().removePlayer(player);
		}
		allPlayers.remove(player);
	}
	
	/**
	 * Returns all registered players (includes offline players!)
	 * 
	 * @return
	 */
	public static Collection<CoKPlayer> getAllPlayers(){
		return allPlayers.values();
	}
	
	/**
	 * Return a player with the specified username
	 * Keep in mind that usernames are cached locally (serverside)
	 * and might be outdated.
	 * 
	 * @param username
	 * @return
	 */
	@Nullable
	public static CoKPlayer getPlayerForName(String username){
		for(CoKPlayer player : allPlayers.values()){
			if(player.getUserName().equals(username)){
				return player;
			}
		}
		return null;
	}
	
	/**
	 * Return a player with the specified UUID
	 * 
	 * @param uuid
	 * @return
	 */
	@Nullable
	public static CoKPlayer getPlayerForUUID(UUID uuid){
		return allPlayers.get(uuid);
	}
	
	/**
	 * Returns a player with the given UUID, registers a new player
	 * with this UUID if there is none present
	 * 
	 * @param uuid
	 * @return
	 */
	public static CoKPlayer getOrCreatPlayerForUUID(UUID uuid){
		if(allPlayers.containsKey(uuid)){
			return allPlayers.get(uuid);
		}
		CoKPlayer player = new CoKPlayer(uuid);
		allPlayers.put(uuid, player);
		return player;
	}
	
	
	/**
	 * Remove all registered players
	 */
	public static void clearPlayers(){
		allPlayers.clear();
	}
	
	/**
	 * Returns a {@link HashSet} of Players from a given {@link Collection}
	 * of players.
	 * Keep in mind that the output size might defer from the input size
	 * (Includes offline Players)
	 * 
	 * @param uuids
	 * @return
	 */
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
