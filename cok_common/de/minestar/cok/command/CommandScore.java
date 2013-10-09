package de.minestar.cok.command;

import java.util.List;

import net.minecraft.command.ICommandSender;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Settings;
import de.minestar.cok.game.Team;
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.references.Color;
import de.minestar.cok.references.Reference;

public class CommandScore extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.ScoreCommand;
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return getCommandName();
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
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
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring) {
		return null;
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return true;
	}

}
