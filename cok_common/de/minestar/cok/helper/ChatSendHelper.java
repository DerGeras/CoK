package de.minestar.cok.helper;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import de.minestar.cok.references.Color;

public class ChatSendHelper {

	public static void sendMessage(ICommandSender sender, String text){
		sender.sendChatToPlayer(Color.getColorCodeFromString("gray") + "[CoK] " + text);
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
