package de.minestar.cok.util;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;

public class ChatSendHelper {

	public static void sendMessageToPlayer(ICommandSender sender, String text){
		sender.addChatMessage(new ChatComponentText(String.format("%s[CoK] %s", Color.getColorCodeFromString("gray"), text)));
	}
	
	public static void sendErrorMessageToPlayer(ICommandSender sender, String text){
		sender.addChatMessage(new ChatComponentText(String.format("%s[CoK] %s", Color.getColorCodeFromString("red"), text)));
	}
	
	public static void broadCastMessage(String text){
		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(
				new ChatComponentText(String.format("%s[CoK] %s", Color.getColorCodeFromString("blue"), text)));
	}
	
	public static void broadCastError(String text){
		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(
				new ChatComponentText(String.format("%s[CoK] %s", Color.getColorCodeFromString("red"), text)));
	}
	
}
