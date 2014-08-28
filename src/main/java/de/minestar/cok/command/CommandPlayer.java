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

public class CommandPlayer extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.COMMAND_PLAYER;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return String.format("Usage: /%s [add|remove] {teamName} {playerName}", getCommandName());
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length < 3){
			ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender));
			return;
		}
		String command = args[0];
		String teamName = args[1];
		String playerName = args[2];
		Team team = TeamRegistry.getTeam(args[1]);
		if(team == null){
			ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find team " + teamName + "!");
			return;
		}
		CoKPlayer player = CoKPlayerRegistry.getPlayerForName(args[2]);
		if(player == null){
			ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find player with name " + args[2]);
		}
		if(command.equals("add")){
			if(player.getTeam() != null){
				ChatSendHelper.sendErrorMessageToPlayer(sender, String.format("Player %s is already in team %s!",
						playerName, player.getTeam().getName()));
			}
			team.addPlayer(player);
			ChatSendHelper.sendMessageToPlayer(sender, String.format("%s succesfully added to team %s!",
					playerName, teamName));
			return;
		}
		if(command.equals("remove")){
			if(!team.hasPlayer(player)){
				ChatSendHelper.sendErrorMessageToPlayer(sender, String.format("Player %s is not part of team %s!",
						playerName, player.getTeam().getName()));
			}
			team.removePlayer(player);
			ChatSendHelper.sendMessageToPlayer(sender, String.format("%s succesfully removed from team %s!",
					playerName, teamName));
			return;
		}
		ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender));
	}

	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender, String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		if(args.length <= 1){
			addIfPrefixMatches(list, args[0], "add", "remove");
		}
		if(args.length == 2){
			for(Team team : TeamRegistry.getAllTeams()){
				addIfPrefixMatches(list, args[1], team.getName());
			}
		}
		if(args.length == 3 && args[0].equals("add")){
			for(CoKPlayer player : CoKPlayerRegistry.getAllPlayers()){
				addIfPrefixMatches(list, args[2], player.getUserName());
			}
		}
		if(args.length == 3 && args[0].equals("remove")){
			Team team = TeamRegistry.getTeam(args[1]);
			if(team != null){
				for(CoKPlayer player : team.getAllPlayers()){
					addIfPrefixMatches(list, args[2], player.getUserName());
				}
			}
		}
		return list;
	}
	
	@Override
	public boolean isUsernameIndex(String[] astring, int i) {
		return false;
	}

}
