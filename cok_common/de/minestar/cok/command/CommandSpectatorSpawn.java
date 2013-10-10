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

import de.minestar.cok.game.CoKGame;
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.references.Reference;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;

public class CommandSpectatorSpawn extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.SpectatorSpawnCommand;
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return getCommandName();
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if(icommandsender instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) icommandsender;
			ChunkCoordinates coords = new ChunkCoordinates();
			coords.posX = (int)player.posX;
			coords.posY = (int)player.posY;
			coords.posZ = (int)player.posZ;
			CoKGame.spectatorSpawn = coords;
			for(String playername : MinecraftServer.getServer().getConfigurationManager().getAllUsernames()){
				if(CoKGame.getTeamOfPlayer(playername) == null){
					CoKGame.setPlayerSpectator(playername);
				}
			}
			ChatSendHelper.sendMessage(icommandsender, "Spectator spawn set to " + coords.posX
					+ ", " + coords.posY + ", " + coords.posZ);
		}
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring) {
		return null;
	}

}
