package de.minestar.cok.game;

import java.util.HashMap;
import java.util.HashSet;

import com.sun.istack.internal.Nullable;

import de.minestar.cok.tileentity.TileEntitySocket;

public class SocketRegistry {

	public static HashSet<TileEntitySocket> unsortedSockets = new HashSet<TileEntitySocket>();
	
	public static HashMap<Integer, HashSet<TileEntitySocket>> sockets = new HashMap<Integer, HashSet<TileEntitySocket>>();
	
	/**
	 * register a socket tile entity
	 * 
	 * @param socket
	 */
	public static boolean registerSocket(TileEntitySocket socket){
		unsortedSockets.remove(socket);
		//TODO ServerTickHandler.isScoreCheckQueued = true;
		return unsortedSockets.add(socket);
	}
	
	/**
	 * sort unsorted sockets.
	 * Needed this due to the call of {@link TileEntitySocket#getBlockMetadata()}
	 * needing the world to be loaded first
	 */
	public static void sortSockets(){
		for(TileEntitySocket socket : unsortedSockets){
			HashSet<TileEntitySocket> teamSockets = sockets.get(socket.getBlockMetadata());
			if(teamSockets == null){
				teamSockets = new HashSet<TileEntitySocket>();
				sockets.put(socket.getBlockMetadata(), teamSockets);
			}
			teamSockets.remove(socket);
			teamSockets.add(socket);
		}
		unsortedSockets.clear();
	}
	
	/**
	 * unregister a socket
	 * 
	 * @param coords
	 * @return
	 */
	public static boolean removeSocket(TileEntitySocket socket){
		//TODO ServerTickHandler.isScoreCheckQueued = true;
		sortSockets();
		boolean res = unsortedSockets.remove(socket);
		HashSet<TileEntitySocket> teamSockets = sockets.get(socket.getBlockMetadata());
		if(teamSockets != null){
			res = res | teamSockets.remove(socket);
		}
		return res;
	}
	
	/**
	 * get all sockets of a specified color
	 * 
	 * @param color
	 * @return
	 */
	@Nullable
	public static HashSet<TileEntitySocket> getSockets(int color){
		sortSockets();
		return sockets.get(color);
	}
	
	/**
	 * Returns all registered sockets
	 * 
	 * @return
	 */
	public static HashSet<TileEntitySocket> getAllSockets(){
		sortSockets();
		HashSet<TileEntitySocket> allSockets = new HashSet<TileEntitySocket>();
		for(HashSet<TileEntitySocket> set : sockets.values()){
			allSockets.addAll(set);
		}
		return allSockets;
	}
	
}
