package de.minestar.cok.command;

import java.util.List;

import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.CoKGameRegistry;
import de.minestar.cok.game.Team;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.ChatSendHelper;
import de.minestar.cok.util.Color;

import net.minecraft.command.ICommandSender;

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
		if(args[0].equals("add")){
			//TODO
			return;
		}
		if(args[0].equals("remove")){
			//TODO
			return;
		}
		ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender));

	}

	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isUsernameIndex(String[] astring, int i) {
		return i == 4;
	}

}
