package de.minestar.cok.command;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.CoKGameRegistry;
import de.minestar.cok.game.Team;
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
		return String.format("Usage: %s [add|remove] {gameName} {teamName} {{teamColor on add}}", getCommandName());
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length < 3){
			ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender));
			return;
		}
		CoKGame game = CoKGameRegistry.registeredGames.get(args[1]);
		if(game == null){
			ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find game " + args[1]);
			return;
		}
		if(args[0].equals("add") && args.length == 4){
			for(Team team : game.getAllTeams()){
				if(team.getName().equals(args[2])){
					ChatSendHelper.sendErrorMessageToPlayer(sender, "Team with the name " + args[2] + "already exists!");
					return;
				}
				if(team.getColor() == Color.getColorFromString(args[3])){ 
					ChatSendHelper.sendErrorMessageToPlayer(sender, "Team with the color " + args[3] + "already exists!");
					return;
				}
			}
			game.addTeam(new Team(args[2], Color.getColorFromString(args[3])));
			ChatSendHelper.sendMessageToPlayer(sender, String.format("Team %s with color %s created in game %s!", 
					args[2], args[3], args[1]));
			return;
		}
		if(args[0].equals("remove")){
			if(!game.getAllTeamNames().contains(args[2])){
				ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find team " + args[2] + "!");
				return;
			}
			game.removeTeam(args[2]);
			ChatSendHelper.sendErrorMessageToPlayer(sender, String.format("Team %s from game %s removed!",
					args[2], args[1]));
			return;
		}
		ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender));
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		if(args.length <= 1){
			list.add("add");
			list.add("remove");
		}
		if(args.length == 2 && (args[0].equals("add") || args[0].equals("remove"))){
			for(String name : CoKGameRegistry.registeredGames.keySet()){
				list.add(name);
			}
		}
		
		if(args.length == 3 && (args[0].equals("remove"))){
			CoKGame game = CoKGameRegistry.registeredGames.get(args[1]);
			if(game != null){
				for(Team team : game.getAllTeams()){
					list.add(team.getName());
				}
			}			
		}
		if(args.length == 4 && args[0].equals("add")){
			list.add("black");
			list.add("darkblue");
			list.add("darkgreen");
			list.add("darkaqua");
			list.add("darkred");
			list.add("purple");
			list.add("gold");
			list.add("gray");
			list.add("darkgray");
			list.add("blue");
			list.add("green");
			list.add("aqua");
			list.add("red");
			list.add("lightpurple");
			list.add("yellow");
			list.add("white");
		}
		return list;
	}

}
