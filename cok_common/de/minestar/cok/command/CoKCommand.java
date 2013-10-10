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

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public abstract class CoKCommand extends CommandBase {

	
	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public abstract String getCommandName();

	@Override
	public abstract String getCommandUsage(ICommandSender icommandsender);

	@Override
	public List<?> getCommandAliases() {
		return null;
	}

	@Override
	public abstract void processCommand(ICommandSender icommandsender, String[] astring);

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return !MinecraftServer.getServer().isDedicatedServer() || MinecraftServer.getServer().getConfigurationManager().getOps().contains(icommandsender.getCommandSenderName().toLowerCase().trim());
	}

	@Override
	public abstract List<?> addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring);

	@Override
	public boolean isUsernameIndex(String[] astring, int i) {
		return false;
	}

}
