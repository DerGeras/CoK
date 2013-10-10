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
package de.minestar.cok.helper;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import de.minestar.cok.game.Team;
import de.minestar.cok.references.Color;

public class ChatSendHelper {

	public static void sendMessage(ICommandSender sender, String text){
		sender.sendChatToPlayer(Color.getColorCodeFromString("gray") + "[CoK] " + text);
	}
	
	public static void sendMessageToPlayer(EntityPlayerMP receiver, String text){
		receiver.sendChatToPlayer(Color.getColorCodeFromString("gray") + "[CoK] " + text);
	}
	
	public static void sendMessageToPlayer(String name, String text){
		EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(name);
		if(player != null){
			player.sendChatToPlayer(Color.getColorCodeFromString("gray") + "[CoK] " + text);
		}
	}
	
	public static void sendTeamMessageToPlayer(String name, String text){
		EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(name);
		if(player != null){
			player.sendChatToPlayer(Color.getColorCodeFromString("gray") + "[Team] " + text);
		}
	}
	
	public static void sendMessageToTeam(Team team, String text){
		if(team != null){
			for(String name : team.getAllPlayers()){
				sendTeamMessageToPlayer(name, text);
			}
		}
	}
	
	public static void sendError(ICommandSender sender, String text){
		sender.sendChatToPlayer(Color.getColorCodeFromString("red") + "[CoK] " + text);
	}
	
	public static void broadCastMessage(String text){
		String[] playernames = MinecraftServer.getServer().getConfigurationManager().getAllUsernames();
		for(String player : playernames){
			ChatSendHelper.sendMessage(MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(player), text);
		}
	}
	
	public static void broadCastError(String text){
		String[] playernames = MinecraftServer.getServer().getConfigurationManager().getAllUsernames();
		for(String player : playernames){
			ChatSendHelper.sendError(MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(player), text);
		}
	}
	
}
