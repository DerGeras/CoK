package de.minestar.cok.command;

import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public abstract class CoKCommand implements ICommand {

	
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
		return true;//MinecraftServer.getServer().getConfigurationManager().getOps().contains(icommandsender.getCommandSenderName().toLowerCase().trim());
	}

	@Override
	public abstract List<?> addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring);

	@Override
	public boolean isUsernameIndex(String[] astring, int i) {
		return false;
	}
	
	public boolean isOnlineUser(String username){
		for(String name:MinecraftServer.getServer().getConfigurationManager().getAllUsernames()){
			if(name.equalsIgnoreCase(username)){
				System.out.println(name);
				return true;
			}
		}
		return false;
	}

}
