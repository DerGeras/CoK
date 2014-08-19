package de.minestar.cok.command;

import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.command.ICommandSender;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.CoKGameRegistry;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.ChatSendHelper;
import de.minestar.cok.util.LogHelper;

public class CommandCoK extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.COMMAND_COK;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return String.format("Usage: %s [create|start|stop] {gameName}", getCommandName());
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length < 2){
			ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender));
			return;
		}
		LogHelper.info(args[0]);
		if(args[0].equals("create")){
			if(CoKGameRegistry.registeredGames.containsKey(args[1])){
				ChatSendHelper.sendErrorMessageToPlayer(sender, "A game with the name " + args[1] + " already exits!");
				return;
			}
			CoKGameRegistry.registerGame(args[1]);
			ChatSendHelper.sendMessageToPlayer(sender, "Game " + args[1] + " created");
			return;
		}
		if(args[0].equals("start")){
			CoKGame game = CoKGameRegistry.registeredGames.get(args[1]);
			if(game == null){
				ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find game " + args[1] + "!");
				return;
			}
			if(game.isRunning()){
				ChatSendHelper.sendErrorMessageToPlayer(sender, "Game already running!");
			} else {
				game.startGame();
			}
			return;
		}
		if(args[0].equals("stop")){
			CoKGame game = CoKGameRegistry.registeredGames.get(args[1]);
			if(game == null){
				ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find game " + args[1] + "!");
				return;
			}
			if(!game.isRunning()){
				ChatSendHelper.sendErrorMessageToPlayer(sender, "Game is not running!");
			} else {
				game.stopGame();
			}
			return;
		}
		ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender));
	}

	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender, String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		if(args.length == 1){
			addIfPrefixMatches(list, args[0], "create", "start", "stop");
		}
		if(args.length == 2 && (args[1].equals("start") || args[1].equals("stop"))){
			for(String name : CoKGameRegistry.registeredGames.keySet()){
				addIfPrefixMatches(list, args[1], name);
			}
		}
		return list;
	}

}