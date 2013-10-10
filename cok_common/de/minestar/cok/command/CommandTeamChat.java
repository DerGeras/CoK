/*******************************************************************************
 * Clash of Kingdoms Minecraft Mod
 *     Copyright (C) 2013 Geras
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
