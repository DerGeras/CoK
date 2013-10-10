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

import java.util.LinkedList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.network.CoKGamePacketServer;
import de.minestar.cok.network.PacketHandler;
import de.minestar.cok.references.Reference;

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
			CoKGamePacketServer.sendPacketToAllPlayers(PacketHandler.TEAM_REMOVE, astring);
			
			ChatSendHelper.sendMessage(icommandsender, "Team \"" + astring[0] + "\" successfully removed!");
		} else{
			ChatSendHelper.sendError(icommandsender, "Team \"" + astring[0] + "\" could not be removed!");
		}
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring) {
		//return teamNames
		LinkedList<String> list = new LinkedList<String>();
		if(astring.length > 0){
			for(String teamName : CoKGame.teams.keySet()){
				if(teamName.startsWith(astring[0])){
					list.add(teamName);
				}
			}
		}
		return list;
	}

}
