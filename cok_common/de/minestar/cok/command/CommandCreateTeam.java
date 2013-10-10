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
import de.minestar.cok.references.Color;
import de.minestar.cok.references.Reference;

public class CommandCreateTeam extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.CreateTeamCommand;
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return getCommandName() + " TeamName Color";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if(astring.length < 2){
			ChatSendHelper.sendMessage(icommandsender, "Usage: " + getCommandUsage(icommandsender));
			return;
		}
		char color = Color.getColorFromString(astring[1]);
		if(color != 'g'){
			boolean res = false;
			res = CoKGame.addTeam(astring[0], color);
			if(!res){
				ChatSendHelper.sendError(icommandsender, "Team \"" + astring[0] + "\" could not be created!");
			} else{
				CoKGamePacketServer.sendPacketToAllPlayers(PacketHandler.TEAM_ADD, astring);
				
				ChatSendHelper.sendMessage(icommandsender, "Team \"" + astring[0] + "\" successfully created!");
			}
		} else{
			ChatSendHelper.sendError(icommandsender, "Invalid colour \"" + astring[0] + "\"!");
		}
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring) {
		LinkedList<String> list = new LinkedList<String>();
		if(astring.length == 2){
			list.add("black");
			list.add("darkblue");
			list.add("darkgreen");
			list.add("darkaqua");
			list.add("darkred");
			list.add("purple");
			list.add("gold");
			list.add("gray");
			list.add("darkgray");
			list.add("blue");
			list.add("green");
			list.add("aqua");
			list.add("red");
			list.add("lightpurple");
			list.add("yellow");
			list.add("white");
		}
		return list;
	}

}
