package de.minestar.cok.game;

import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraftforge.event.world.BlockEvent.BreakEvent;

import de.minestar.cok.tileentity.TileEntitySocket;

public class SocketRegistry {

	public static HashSet<TileEntitySocket> unsortedSockets = new HashSet<TileEntitySocket>();
	
	public static HashMap<Integer, HashSet<TileEntitySocket>> sockets = new HashMap<Integer, HashSet<TileEntitySocket>>();
	
	/**
	 * register a socket block
	 * @param coords
	 */
	public static boolean registerSocket(TileEntitySocket socket){
		unsortedSockets.remove(socket);
		//TODO ServerTickHandler.isScoreCheckQueued = true;
		return unsortedSockets.add(socket);
	}
	
	/**
	 * sort unsorted sockets.
	 * Needed this due to divergation in the call "socket.getBlockMetadata()"
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
	 * unregister a socket block
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
	
}