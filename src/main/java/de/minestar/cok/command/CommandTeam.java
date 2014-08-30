package de.minestar.cok.command;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.CoKGameRegistry;
import de.minestar.cok.game.Team;
import de.minestar.cok.game.TeamRegistry;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.ChatSendHelper;
import de.minestar.cok.util.Color;

public class CommandTeam extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.COMMAND_TEAM;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return String.format("Usage: /%s [add|remove|move] {teamName} {{teamColor on add|gameName on move}}", getCommandName());
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length < 2){
			ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender));
			return;
		}
		String command = args[0];
		String teamName = args[1];
		Team teamForName = TeamRegistry.getTeam(teamName);
		if(command.equals("add") && args.length == 3){
			String color = args[2];
			if(teamForName != null){
				ChatSendHelper.sendErrorMessageToPlayer(sender, "Team with the name " + teamName + "already exists!");
				return;
			}
			if(TeamRegistry.getTeam(Color.getColorFromString(color)) != null){ 
				ChatSendHelper.sendErrorMessageToPlayer(sender, "Team with the color " + color + "already exists!");
				return;
			}
			TeamRegistry.addTeam(new Team(teamName, Color.getColorFromString(color)));
			ChatSendHelper.sendMessageToPlayer(sender, String.format("Team %s with color %s succesfully created!", 
					teamName, color));
			return;
		}
		if(command.equals("remove")){
			if(teamForName == null){
				ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find team " + teamName + "!");
				return;
			}
			if(teamForName.getGame() != null && teamForName.getGame().isRunning()){
				ChatSendHelper.sendErrorMessageToPlayer(sender, "Connot remove a team from a running game!");
				return;
			}
			TeamRegistry.removeTeam(teamName);
			ChatSendHelper.sendMessageToPlayer(sender, String.format("Team %s succesfully removed!",
					teamName));
			return;
		}
		if(command.equals("move") && args.length == 3){
			String gameName = args[2];
			CoKGame game = CoKGameRegistry.registeredGames.get(gameName);
			if(game == null){
				ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find game " + gameName + "!");
				return;
			}
			if(game.isRunning()){
				ChatSendHelper.sendErrorMessageToPlayer(sender, "Cannot move a team to a running game!");
				return;
			}
			if(teamForName.getGame() != null && teamForName.getGame().isRunning()){
				ChatSendHelper.sendErrorMessageToPlayer(sender, "Connot move a team from a running game!");
				return;
			}
			game.addTeam(teamForName);
			ChatSendHelper.sendMessageToPlayer(sender, String.format("Succesfully moved team %s to game %s!",
					teamName, gameName));
			return;
		}

		if(command.equals("move") && args.length == 2){
			if(teamForName.getGame() != null && teamForName.getGame().isRunning()){
				ChatSendHelper.sendErrorMessageToPlayer(sender, "Connot remove a team from a running game!");
				return;
			}
			ChatSendHelper.sendMessageToPlayer(sender, String.format("Succesfully moved team %s to global!",
					teamName));
			return;
		}
		ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender));
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		if(args.length == 1){
			addIfPrefixMatches(list, args[0], "add", "remove", "move");
		}
		
		if(args.length == 2 && (args[0].equals("remove") || args[0].equals("move"))){
			for(Team team : TeamRegistry.getAllTeams()){
				addIfPrefixMatches(list, args[1], team.getName());
			}		
		}
		if(args.length == 3 && args[0].equals("add")){
			addIfPrefixMatches(list, args[2], Color.allColors);
		}
		if(args.length == 3 && args[0].equals("move")){
			for(String gameName : CoKGameRegistry.registeredGames.keySet()){
				addIfPrefixMatches(list, args[2], gameName);
			}
		}
		return list;
	}

}
