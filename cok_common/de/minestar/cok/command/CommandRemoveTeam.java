package de.minestar.cok.command;

import java.util.List;

import de.minestar.cok.game.CoKGame;
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.references.Reference;

import net.minecraft.command.ICommandSender;

public class CommandRemoveTeam extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.RemoveTeamCommand;
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return getCommandName() + " Team";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if(astring.length < 1){
			ChatSendHelper.sendMessage(icommandsender, "Usage: " + getCommandUsage(icommandsender));
			return;
		}
		if(CoKGame.removeTeam(astring[0])){
			ChatSendHelper.sendMessage(icommandsender, "Team \"" + astring[0] + "\" successfully removed!");
		} else{
			ChatSendHelper.sendError(icommandsender, "Team \"" + astring[0] + "\" could not be removed!");
		}
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring) {
		return (List<?>) CoKGame.teams.values();
	}

}
