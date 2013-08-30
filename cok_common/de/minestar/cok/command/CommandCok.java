package de.minestar.cok.command;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Settings;
import de.minestar.cok.game.Team;
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.references.Color;
import de.minestar.cok.references.Reference;

public class CommandCok extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.CoKCommand;
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return getCommandName() + " [start|score|end]";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		CoKGame.sortSockets();
		if(astring.length < 1){
			ChatSendHelper.sendMessage(icommandsender, "Usage: " + getCommandUsage(icommandsender));
			return;
		}
		if(astring[0].equals("start")){
			if(CoKGame.gameRunning){
				return;
			}
			CoKGame.startGame();
			return;
		}
		if(astring[0].equals("score")){
			if(!CoKGame.gameRunning){
				ChatSendHelper.sendError(icommandsender, "There is no game running");
				return;
			}
			if(CoKGame.teams.size() > 0){
				ChatSendHelper.sendMessage(icommandsender, "Results:");
				for(Team team : CoKGame.teams.values()){
					int maxScore = Settings.buildingHeight * (CoKGame.sockets.get(team.getColorAsInt()) == null ? 0 : CoKGame.sockets.get(team.getColorAsInt()).size());
					ChatSendHelper.sendMessage(icommandsender, Color.getColorCodeFromChar(team.getColor())
							+ team.getName() + Color.getColorCodeFromString("white") + ": "
							+ CoKGame.getScoreForTeam(team) + "/" + maxScore);
				}
			}
			return;
		}
		if(astring[0].equals("end")){
			if(!CoKGame.gameRunning){
				return;
			}
			CoKGame.stopGame();
			return;
		}
		ChatSendHelper.sendMessage(icommandsender, "Usage: " + getCommandUsage(icommandsender));
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring) {
		LinkedList<String> list = new LinkedList<String>();
		if(astring.length <= 1){
			list.add("start");
			list.add("score");
			list.add("end");
		}
		return list;
	}

}
