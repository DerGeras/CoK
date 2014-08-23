package de.minestar.cok.command;

import java.util.LinkedList;
import java.util.List;

import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.CoKGameRegistry;
import de.minestar.cok.game.Team;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.ChatSendHelper;
import net.minecraft.command.ICommandSender;

public class CommandSetSpawn extends CoKCommand{

	@Override
	public String getCommandName() {
		return Reference.COMMAND_SET_SPAWN;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return String.format("Usage: /%s {game} {{team}}",
				getCommandName());
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length < 1 || args.length > 2){
			ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender);
			return;
		}
		CoKGame game = CoKGameRegistry.registeredGames.get(args[0]);
		if(game != null){
			ChatSendHelper.sendMessageToPlayer(sender, "Set spawnpoint for game " + args[0]);
		} else{
			ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find game " + args[0]);
		}
		if(args.length == 1){
			game.setSpawnLocation(sender.getPlayerCoordinates());			
		}
		if(args.length == 2){
			Team team = game.getTeam(args[1]);
			if(team != null){
				team.setSpawnCoordinates(sender.getPlayerCoordinates());

				ChatSendHelper.sendMessageToPlayer(sender,
						String.format("Set spawnpoint for team %s in game %s",
								args[1], args[0]));
			} else {
				ChatSendHelper.sendMessageToPlayer(sender,
						String.format("Could not find team %s in game %s",
								args[1], args[0]));
			}
		}	
	}

	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender,
			String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		if(args.length == 1){
			for(String name : CoKGameRegistry.registeredGames.keySet()){
				addIfPrefixMatches(list, args[1], name);
			}
		}
		if(args.length == 2){
			CoKGame game = CoKGameRegistry.registeredGames.get(args[1]);
			if(game != null){
				for(Team team : game.getAllTeams()){
					addIfPrefixMatches(list, args[2], team.getName());
				}
			}			
		}
		return list;
	}

}
