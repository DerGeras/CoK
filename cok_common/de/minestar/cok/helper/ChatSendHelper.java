package de.minestar.cok.helper;

import net.minecraft.command.ICommandSender;

public class ChatSendHelper {

	public static void sendMessage(ICommandSender sender, String text){
		sender.sendChatToPlayer("[CoK] " + text);
	}
	
}
