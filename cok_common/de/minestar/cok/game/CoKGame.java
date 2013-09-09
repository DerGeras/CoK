package de.minestar.cok.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.network.CoKGamePacketServer;
import de.minestar.cok.network.PacketHandler;
import de.minestar.cok.profession.Profession;
import de.minestar.cok.references.Color;
import de.minestar.cok.tileentity.TileEntitySocket;

public class CoKGame {
	
	public static HashMap<String, Team> teams;
	public static HashMap<Integer, HashSet<TileEntitySocket>> sockets;
	public static HashSet<TileEntitySocket> unsortedSockets;
	public static HashMap<String, Profession> playerProfessions;
	public static HashSet<String> spectators;
	public static Random rand = new Random();
	
	public static boolean gameRunning = false;
	
	public static void initGame(Configuration config){
		Settings.defaultbuildingBlockID = config.get(Configuration.CATEGORY_GENERAL, "Default building Block ID", Block.stone.blockID).getInt();
		Settings.buildingHeight = config.get(Configuration.CATEGORY_GENERAL, "Building Height", 25).getInt();
		Settings.protectedRadius = config.get(Configuration.CATEGORY_GENERAL, "Protected Radius", 3).getInt();
		
		if(config.hasChanged()){
			config.save();
		}
		
		teams = new HashMap<String, Team>();
		sockets = new HashMap<Integer,HashSet<TileEntitySocket>>();
		unsortedSockets = new HashSet<TileEntitySocket>();
		playerProfessions = new HashMap<String, Profession>();
		spectators = new HashSet<String>();

		gameRunning = false;
	}
	
	/**
	 * clean up the game on server stop
	 */
	public static void cleanUpGame(){
		teams.clear();
		sockets.clear();
		unsortedSockets.clear();
		playerProfessions.clear();
		spectators.clear();
		gameRunning = false;
	}
	
	/**
	 * start a round of CoK
	 */
	public static void startGame(){
		sortSockets();
		gameRunning = true;
		for(Team team: teams.values()){
			team.distributeProfessions();
		}
		ChatSendHelper.broadCastError("Let the clash of kingdoms begin!");
		for(Team team : teams.values()){
			if(team.getAllPlayers().size() > 0){
				ChatSendHelper.broadCastMessage("Team " + Color.getColorCodeFromChar(team.getColor()) + team.getName()
						+ Color.getColorCodeFromString("gray") + " with leader " +
						Color.getColorCodeFromString("white") + team.getCaptain());
			}
			for(String playername : team.getAllPlayers()){
				EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(playername);
				player.inventory.clearInventory(-1, -1);
				Profession playerProfession = playerProfessions.get(playername);
				if(playerProfession != null){
					playerProfession.giveKit(player, team);
				}
			}
		}
		//set non-team players as spectators
		for(String player : MinecraftServer.getServer().getConfigurationManager().getAllUsernames()){
			if(getTeamOfPlayer(player) == null){
				setPlayerSpectator(player);
			}
		}
		//send state to clients
		CoKGamePacketServer.sendPacketToAllPlayers(PacketHandler.GAME_RUNNING, true);
	}
	
	/**
	 * Stop the current round and declare the scores
	 */
	public static void stopGame(){
		if(!gameRunning){
			return;
		}
		gameRunning = false;
		ChatSendHelper.broadCastError("The game has ended!");
		if(CoKGame.teams.size() > 0){
			ChatSendHelper.broadCastError("Results:");
			for(Team team : teams.values()){
				int maxScore = Settings.buildingHeight * (sockets.get(team.getColorAsInt()) == null ? 0 : sockets.get(team.getColorAsInt()).size());
				ChatSendHelper.broadCastMessage(Color.getColorCodeFromChar(team.getColor())
						+ team.getName() + Color.getColorCodeFromString("white") + ": "
						+ getScoreForTeam(team) + "/" + maxScore);
				//TODO finish call
			}
		}
		//send state to clients
		CoKGamePacketServer.sendPacketToAllPlayers(PacketHandler.GAME_RUNNING, false);
	}
	
	/**
	 * get the score for the given teamName
	 * @param name - name of the team
	 */
	public static int getScoreForTeam(Team team){
		sortSockets();
		HashSet<TileEntitySocket> teamSockets = sockets.get(team.getColorAsInt());
		if(teamSockets == null){
			return 0;
		}
		int res = 0;
		for(TileEntitySocket socket : teamSockets){
			res += socket.countBlocks();
		}
		return res;
	}
	
	/**
	 * Compute the score of every team, and remove teams that are defeated
	 * 
	 */
	public static void checkWinningCondition(){
		if(!gameRunning){
			return;
		}
		sortSockets();
		HashSet<Team> teamCopies = new HashSet<Team>();
		teamCopies.addAll(teams.values());
		for(Team team: teamCopies){
			int maxScore = Settings.buildingHeight * (CoKGame.sockets.get(team.getColorAsInt()) == null ? 0 : CoKGame.sockets.get(team.getColorAsInt()).size());
			if(maxScore > 0){
				int score = getScoreForTeam(team);
				if(score >= maxScore){
					ChatSendHelper.broadCastError("The kingdom " + team.getName() + " has fallen!");
					removeTeam(team.getName());
				}
			}
		}
		if(teams.size() == 1){
			Team lastTeam = null;
			for(Team team : teams.values()){
				lastTeam = team;
			}
			if(lastTeam != null){
				ChatSendHelper.broadCastError("The kingdom " + lastTeam.getName() + " has crushed all their ennemies!");
				ChatSendHelper.broadCastError("Long live king " + lastTeam.getCaptain() + "!");
			}
			CoKGame.stopGame();
		}
		if(teams.size() == 0){
			ChatSendHelper.broadCastError("Is this even possible? All kingdoms have been defeated!");
			CoKGame.stopGame();
		}
	}
	
	/**
	 * register a socket block
	 * @param coords
	 */
	public static boolean registerSocket(TileEntitySocket socket){
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
		sortSockets();
		if(sockets != null){
			HashSet<TileEntitySocket> teamSockets = sockets.get(socket.getBlockMetadata());
			if(teamSockets == null){
				return false;
			}
			return teamSockets.remove(socket);
		}
		return false;
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
	 * remove a team with the given team
	 * @param name
	 * @return if the team could be removed (was present)
	 */
	public static boolean removeTeam(String name){
		boolean res = teams.containsKey(name);
		if(res){
			Team team = teams.get(name);
			for(String player: team.getAllPlayers()){
				playerProfessions.remove(player);
				if(gameRunning){
					setPlayerSpectator(player);
				}
			}
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
		removeSpectator(playername);
		boolean res = teams.containsKey(teamname) && getTeamOfPlayer(playername) == null;
			if(res){
				res = teams.get(teamname).addPlayer(playername);
				if(gameRunning){
					if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
						EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(playername);
						player.inventory.clearInventory(-1, -1);
						Profession profession = playerProfessions.get(playername);
						if(profession != null){
							profession.giveKit(player, teams.get(teamname));
						}
					}
				}
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
			if(gameRunning){
				setPlayerSpectator(playername);
			}
		}
		return res;
	}
	
	/**
	 * Sets a player to spectator mode
	 * Sets capabilities
	 * @param player EntityPlayer
	 */
	public static void setPlayerSpectator(EntityPlayer player){
		spectators.add(player.username);
		
		player.capabilities.allowFlying = true;
		player.capabilities.disableDamage = true;
		player.capabilities.allowEdit = false;
		player.setInvisible(true);
		player.inventory.clearInventory(-1, -1);
	}
	
	/**
	 * Sets a player to spectator mode
	 * Sets capabilities
	 * @param playername String
	 */
	public static void setPlayerSpectator(String playername){
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
			EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(playername);
			if(player != null){
				String[] name = { player.username };
				CoKGamePacketServer.sendPacketToAllPlayers(PacketHandler.SPECTATOR_ADD, name);
				setPlayerSpectator(player);
			}
		}
	}
	
	/**
	 * remove spectator
	 * @param player
	 */
	public static void removeSpectator(EntityPlayer player){
		spectators.remove(player.username);

		player.capabilities.allowFlying = false;
		player.capabilities.disableDamage = false;
		player.capabilities.allowEdit = true;
		player.setInvisible(false);
	}
	
	/**
	 * Remove Spectator
	 * sets capabilities
	 * @param playername
	 */
	public static void removeSpectator(String playername){
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
			EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(playername);
			if(player != null){
				String[] name = { player.username };
				CoKGamePacketServer.sendPacketToAllPlayers(PacketHandler.SPECTATOR_REMOVE, name);
				removeSpectator(player);
			}
		}
	}
	
	
	/**
	 * punish a team by adding blocks to the tower
	 * i.e. when the leader dies
	 * @param team
	 * @param amount
	 */
	public static void punishTeam(Team team, int amount){
		sortSockets();
		int rest = amount;
		boolean added = true;
		while(added && rest > 0){
			added = false;
			HashSet<TileEntitySocket> sockets = CoKGame.sockets.get(team.getColorAsInt());
			if(sockets != null){
				for(TileEntitySocket socket : sockets){
					if(socket.addBlock()){
						added = true;
						if(--rest <= 0){
							break;
						}
					}
				}
			}
		}
	}
	
}
