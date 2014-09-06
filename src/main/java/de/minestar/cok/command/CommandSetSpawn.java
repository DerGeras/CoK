package de.minestar.cok.command;

import java.util.LinkedList;
import java.util.List;

import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.CoKGameRegistry;
import de.minestar.cok.game.Team;
import de.minestar.cok.game.TeamRegistry;
import de.minestar.cok.game.worlddata.CoKGameWorldData;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.ChatSendHelper;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChunkCoordinates;

public class CommandSetSpawn extends CoKCommand{

	@Override
	public String getCommandName() {
		return Reference.COMMAND_SET_SPAWN;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return String.format("Usage: /%s [global|game|team] {name} {{x}} {{y}} {{z}}",
				getCommandName());
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length < 1){
			ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender));
			return;
		}
		String command = args[0];
		if(command.equals("global")){
			ChunkCoordinates coords;
			if(args.length == 4){
				coords = new ChunkCoordinates(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
			} else{
				coords = sender.getPlayerCoordinates();
			}
			CoKGameRegistry.setGeneralSpawn(coords);
			ChatSendHelper.sendMessageToPlayer(sender, String.format("Global spawnpoint successfully set to %d %d %d!",
					 coords.posX, coords.posY, coords.posZ));
			return;
		}
		if(args.length < 2){
			ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender));
			return;
		}
		String name = args[1];
		if(command.equals("game")){
			CoKGame game = CoKGameRegistry.registeredGames.get(name);
			if(game == null){
				ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find game " + name + "!");
				return;
			}
			ChunkCoordinates coords;
			if(args.length == 5){
				coords = new ChunkCoordinates(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
			} else {
				coords = sender.getPlayerCoordinates();
			}
			game.setSpawnLocation(coords);
			ChatSendHelper.sendMessageToPlayer(sender, String.format("Spawnpoint successfully created for game %s to %d %d %d!",
					name, coords.posX, coords.posY, coords.posZ));
			return;
		}
		if(command.equals("team")){
			Team team = TeamRegistry.getTeam(name);
			if(team == null){
				ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find team " + name + "!");
				return;
			}
			ChunkCoordinates coords;
			if(args.length == 5){
				coords = new ChunkCoordinates(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
			} else {
				coords = sender.getPlayerCoordinates();
			}
			team.setSpawnCoordinates(coords);
			ChatSendHelper.sendMessageToPlayer(sender, String.format("Spawnpoint successfully created for team %s to %d %d %d!",
					name, coords.posX, coords.posY, coords.posZ));
			return;
		}
		ChatSendHelper.sendErrorMessageToPlayer(sender, getCommandUsage(sender));
	}

	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender,
			String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		if(args.length == 1){
			addIfPrefixMatches(list, args[0], "game", "team");
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
		return list;
	}

}
