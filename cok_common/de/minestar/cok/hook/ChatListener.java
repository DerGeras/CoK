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
package de.minestar.cok.hook;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Team;
import de.minestar.cok.references.Color;

public class ChatListener {
	
	@ForgeSubscribe
	public void serverChatSend(ServerChatEvent event){
		String name = event.username;
		Team team = CoKGame.getTeamOfPlayer(name);
		if(team == null){
			event.line = name + ": " + event.message;
			return;
		}
		String colorcode = Color.getColorCodeFromChar(team.getColor());
		event.line = colorcode + team.getName() + " " + Color.getColorCodeFromString("white")
				+ name + ": " + event.message;
	}

}
