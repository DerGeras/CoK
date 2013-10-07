package de.minestar.cok.command;

import java.util.List;

import net.minecraft.command.ICommandSender;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Team;
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.references.Reference;

public class CommandTeamChat extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.TeamChatCommand;
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return getCommandName() + "Message";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		Team team = CoKGame.getTeamOfPlayer(icommandsender.getCommandSenderName());
		if(astring.length > 0 && team != null){
			StringBuffer message = new StringBuffer(icommandsender.getCommandSenderName());
			message.append(":");
			for(int i = 0; i < astring.length; i++){
				message.append(" ");
				message.append(astring[i]);
			}
			ChatSendHelper.sendMessageToTeam(team, message.toString());
		} else{
			ChatSendHelper.sendError(icommandsender, getCommandUsage(icommandsender));
		}
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring) {
		return null;
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return CoKGame.getTeamOfPlayer(icommandsender.getCommandSenderName()) != null;
	}

}
