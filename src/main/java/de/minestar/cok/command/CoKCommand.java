package de.minestar.cok.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public abstract class CoKCommand extends CommandBase {

	@Override
	public abstract String getCommandName();

	@Override
	public abstract String getCommandUsage(ICommandSender sender);

	@Override
	public abstract void processCommand(ICommandSender sender, String[] args);
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return super.canCommandSenderUseCommand(icommandsender);
		//return !MinecraftServer.getServer().isDedicatedServer() || MinecraftServer.getServer().getConfigurationManager().getOps().contains(icommandsender.getCommandSenderName().toLowerCase().trim());
	}
	
	@Override
	public abstract List addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring);

	@Override
	public boolean isUsernameIndex(String[] astring, int i) {
		return false;
	}

}
