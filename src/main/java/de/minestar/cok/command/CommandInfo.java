package de.minestar.cok.command;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.CoKGameRegistry;
import de.minestar.cok.game.CoKPlayer;
import de.minestar.cok.game.CoKPlayerRegistry;
import de.minestar.cok.game.Team;
import de.minestar.cok.game.TeamRegistry;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.ChatSendHelper;
import de.minestar.cok.util.Color;

public class CommandInfo extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.COMMAND_INFO;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return String.format("Usage: %s [game|team|player] {name}", getCommandName());
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length == 0){
			sendGeneralInfo(sender);
			return;
		}
		if(args.length == 2){
			String command = args[0];
			String name = args[1];
			if(command.equals("game")){
				sendGameInfo(sender, name);
				return;
			}
			if(command.equals("team")){
				sendTeaminfo(sender, name);
				return;
			}
			if(command.equals("player")){
				sendPlayerInfo(sender, name);
				return;
			}
		}
		ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender));
	}
	
	private void sendGeneralInfo(ICommandSender sender){
		ChatSendHelper.sendMessageToPlayer(sender, "Registered games:");
		for(CoKGame game : CoKGameRegistry.registeredGames.values()){
			ChatSendHelper.sendMessageToPlayer(sender, "-" + game.getName());
		}
		ChatSendHelper.sendMessageToPlayer(sender, "Registered teams:");
		for(Team team : TeamRegistry.getAllTeams()){
			ChatSendHelper.sendMessageToPlayer(sender, "-" + Color.getColorCodeFromChar(team.getColor()) + team.getName());
		}
	}
	
	private void sendGameInfo(ICommandSender sender, String name){
		CoKGame game = CoKGameRegistry.registeredGames.get(name);
		if(game == null){
			ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find game " + name + "!");
			return;
		}
		ChatSendHelper.sendMessageToPlayer(sender, "GameInfo for game " + name + ":");
		ChatSendHelper.sendMessageToPlayer(sender, "-Running: " + game.isRunning());
		ChatSendHelper.sendMessageToPlayer(sender, "-Teams:");
		for(Team team : game.getAllTeams()){
			ChatSendHelper.sendMessageToPlayer(sender, "--" + Color.getColorCodeFromChar(team.getColor()) + team.getName());
		}
	}
	
	private void sendTeaminfo(ICommandSender sender, String name){
		Team team = TeamRegistry.getTeam(name);
		if(team == null){
			ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find team " + name + "!");
			return;
		}
		ChatSendHelper.sendMessageToPlayer(sender, "TeamInfo for team " + Color.getColorCodeFromChar(team.getColor()) + name + ":");
		if(team.getGame() != null){
			ChatSendHelper.sendMessageToPlayer(sender, "-Game: " + team.getGame().getName());
		}
		ChatSendHelper.sendMessageToPlayer(sender, "-Players:");
		for(CoKPlayer player : team.getAllPlayers()){
			ChatSendHelper.sendMessageToPlayer(sender, "--" + player.getUserName());
		}
		
	}
	
	private void sendPlayerInfo(ICommandSender sender, String name){
		CoKPlayer player = CoKPlayerRegistry.getPlayerForName(name);
		if(player == null){
			ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find player " + name + "!");
			return;
		}
		ChatSendHelper.sendMessageToPlayer(sender, "PlayerInfo for player " + name + ":");
		if(player.getTeam() != null){
			ChatSendHelper.sendMessageToPlayer(sender, "-Team: " + player.getTeam().getName());
		}
		if(player.getGame() != null){
			ChatSendHelper.sendMessageToPlayer(sender, "-Game: " + player.getGame().getName());
		}
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender,
			String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		if(args.length == 1){
			addIfPrefixMatches(list, args[0], "game", "team", "player");
		}
		if(args.length == 2 && args[0].equals("game")){
			for(String gameName : CoKGameRegistry.registeredGames.keySet()){
				addIfPrefixMatches(list, args[1], gameName);
			}
		}
		if(args.length == 2 && args[0].equals("team")){
			for(Team team : TeamRegistry.getAllTeams()){
				addIfPrefixMatches(list, args[1], team.getName());
			}
		}
		if(args.length == 2 && args[0].equals("player")){
			for(CoKPlayer player : CoKPlayerRegistry.getAllPlayers()){
				addIfPrefixMatches(list, args[1], player.getUserName());
			}
		}
		return list;
	}

}
