package de.minestar.cok.command;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.CoKGameRegistry;
import de.minestar.cok.game.CoKPlayer;
import de.minestar.cok.game.CoKPlayerRegistry;
import de.minestar.cok.game.Team;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.ChatSendHelper;

public class CommandPlayer extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.COMMAND_PLAYER;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return String.format("Usage: %s [add|remove] {gameName} {teamName} {playerName}", getCommandName());
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length < 4){
			ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender));
			return;
		}
		CoKGame game = CoKGameRegistry.registeredGames.get(args[1]);
		if(game == null){
			ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find game " + args[1]);
			return;
		}
		Team team = game.getTeam(args[2]);
		if(team == null){
			ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find team " + args[2] + "!");
			return;
		}
		CoKPlayer player = CoKPlayerRegistry.getPlayerForName(args[3]);
		if(player == null){
			ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find player with name " + args[3]);
		}
		if(args[0].equals("add")){
			if(player.getTeam() != null){
				ChatSendHelper.sendErrorMessageToPlayer(sender, String.format("Player %s is already in team %s of game %s!",
						args[3], player.getTeam().getName(), player.getTeam().getGame().getName()));
			}
			team.addPlayer(player);
			ChatSendHelper.sendMessageToPlayer(sender, String.format("%s succesfully added to team %s!",
					args[3], args[2]));
			return;
		}
		if(args[0].equals("remove")){
			if(!team.hasPlayer(player)){
				ChatSendHelper.sendErrorMessageToPlayer(sender, String.format("Player %s is not part of team %s!",
						args[3], player.getTeam().getName()));
			}
			team.removePlayer(player);
			ChatSendHelper.sendMessageToPlayer(sender, String.format("%s succesfully removed from team %s!",
					args[3], args[2]));
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
			for(String name : CoKGameRegistry.registeredGames.keySet()){
				addIfPrefixMatches(list, args[1], name);
			}
		}
		if(args.length == 3){
			CoKGame game = CoKGameRegistry.registeredGames.get(args[1]);
			if(game != null){
				for(String team : game.getAllTeamNames()){
					addIfPrefixMatches(list, args[2], team);
				}
			}
		}
		return list;
	}
	
	@Override
	public boolean isUsernameIndex(String[] astring, int i) {
		return astring.length >= 1 && astring[0].equals("add") && i == 4;
	}

}
