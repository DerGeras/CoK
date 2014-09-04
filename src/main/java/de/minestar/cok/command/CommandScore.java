package de.minestar.cok.command;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.CoKGameRegistry;
import de.minestar.cok.game.SocketRegistry;
import de.minestar.cok.game.Team;
import de.minestar.cok.game.TeamRegistry;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.tileentity.TileEntitySocket;
import de.minestar.cok.util.ChatSendHelper;
import de.minestar.cok.util.Color;

public class CommandScore extends CoKCommand{

	@Override
	public String getCommandName(){ 
		return Reference.COMMAND_SCORE;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return String.format("Usage: %s {{game}}", getCommandName());
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length == 0){
			for(CoKGame game : CoKGameRegistry.registeredGames.values()){
				ChatSendHelper.sendMessageToPlayer(sender, String.format("Scores for game %s:", game.getName()));
				for(Team team : game.getAllTeams()){
					ChatSendHelper.sendMessageToPlayer(sender, String.format("%s%s%s:%d/%d",
							Color.getColorCodeFromChar(team.getColor()),
							team.getName(),
							Color.getColorCodeFromString("blue"),
							game.getScoreForTeam(team),
							game.getMaxScoreForTeam(team)));
				}
			}
			return;
		}
		if(args.length == 1){
			String gameName = args[0];
			CoKGame game = CoKGameRegistry.registeredGames.get(gameName);
			if(game == null){
				ChatSendHelper.sendErrorMessageToPlayer(sender, "Could not find game " + gameName + "!");
				return;
			}
			ChatSendHelper.sendMessageToPlayer(sender, String.format("Scores for game %s:", game.getName()));
			for(Team team : game.getAllTeams()){
				ChatSendHelper.sendMessageToPlayer(sender, String.format("%s%s%s:%d/%d",
						Color.getColorCodeFromChar(team.getColor()),
						team.getName(),
						Color.getColorCodeFromString("white"),
						game.getScoreForTeam(team),
						game.getMaxScoreForTeam(team)));
			}
			return;
		}
		ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender));		
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
			for(String gameName : CoKGameRegistry.registeredGames.keySet()){
				addIfPrefixMatches(list, args[0], gameName);
			}
		}
		return list;
	}

}
