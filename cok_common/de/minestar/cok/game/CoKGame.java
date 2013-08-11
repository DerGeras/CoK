package de.minestar.cok.game;

import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;
import de.minestar.cok.tileentity.TileEntitySocket;

public class CoKGame {
	
	public static HashMap<String, Team> teams;
	public static HashSet<TileEntitySocket> sockets; 
	
	public static void initGame(Configuration config){
		Settings.defaultbuildingBlockID = config.get(Configuration.CATEGORY_GENERAL, "Default building Block ID", Block.stone.blockID).getInt();
		Settings.buildingHeight = config.get(Configuration.CATEGORY_GENERAL, "Building Height", 25).getInt();
		Settings.protectedRadius = config.get(Configuration.CATEGORY_GENERAL, "Protected Radius", 3).getInt();
		
		if(config.hasChanged()){
			config.save();
		}
		
		teams = new HashMap<String, Team>();
		sockets = new HashSet<TileEntitySocket>();
	}
	
	public static void cleanUpGame(){
		teams = new HashMap<String, Team>();
		sockets = new HashSet<TileEntitySocket>();
	}
	
	/**
	 * register a socket block
	 * @param coords
	 */
	public static boolean registerSocket(TileEntitySocket coords){
		if(sockets != null) System.out.println("Size " + sockets.size());
		return sockets != null ? sockets.add(coords) : false;
	}
	
	/**
	 * unregister a socket block
	 * @param coords
	 * @return
	 */
	public static boolean removeSocket(TileEntitySocket coords){
		if(sockets != null) System.out.println("Size " + sockets.size());
		return sockets != null ? sockets.remove(coords) : false;
	}
	
	/**
	 * Add a team
	 * @param name of the team
	 * @param color of the team
	 * @return if the team has been successfully added
	 */
	public static boolean addTeam(String name, char color){
		boolean res = teams.containsKey(name);
		if(!res){
			teams.put(name, new Team(name, color));
		}
		return !res;
	}
	
	/**
	 * Add a team
	 * @param name
	 * @param color
	 * @param captain
	 * @return if the team has been successfully added
	 */
	public static boolean addTeam(String name, char color, String captain){
		boolean res = teams.containsKey(name);
		if(!res){
			Team team = new Team(name, color, captain);
			team.addPlayer(name);
			teams.put(name, team);
		}
		return !res;
	}
	
	/**
	 * remove a team with the given team
	 * @param name
	 * @return if the team could be removed (was present)
	 */
	public static boolean removeTeam(String name){
		boolean res = teams.containsKey(name);
		if(res){
			teams.remove(name);
		}
		return res;
	}
	
	/**
	 * get the team with the specified name
	 * @param name
	 * @return team or null if there is no team with this name
	 */
	public static Team getTeam(String name){
		return teams.get(name);
	}
	
	/**
	 * Set the captain of the specified team
	 * @param teamName
	 * @param captain
	 * @return
	 */
	public static boolean setCaptainForTeam(String teamName, String captain){
		boolean res = teams.containsKey(teamName);
		if(res){
			teams.get(teamName).setCaptain(captain);
		}
		return res;
	}
	
	/**
	 * Get the team of a specified player
	 * @param playername
	 * @return
	 */
	public static Team getTeamOfPlayer(String playername){
		for(Team team: teams.values()){
			if(team.hasPlayer(playername)){
				return team;
			}
		}
		return null;
	}
	
	/**
	 * Add player to the team if they aren't in another one already
	 * @param teamname
	 * @param playername
	 * @return
	 */
	public static boolean addPlayerToTeam(String teamname, String playername){
		boolean res = teams.containsKey(teamname) && getTeamOfPlayer(playername) == null;
			if(res){
				res = teams.get(teamname).addPlayer(playername);
			}
		return res;
	}
	
	/**
	 * remove player from specified team
	 * @param teamname
	 * @param playername
	 * @return if the operation was successfull
	 */
	public static boolean removePlayerFromTeam(String teamname, String playername){
		boolean res = teams.containsKey(teamname);
		if(res){
			res = teams.get(teamname).removePlayer(playername);
		}
		return res;
	}
	
}
