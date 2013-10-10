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
import de.minestar.cok.references.Reference;

public class CommandCok extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.CoKCommand;
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return getCommandName() + " [start|end]";
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
		if(astring[0].equalsIgnoreCase("end")){
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
			list.add("end");
		}
		return list;
	}

}
