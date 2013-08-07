package de.minestar.cok.helper;

import de.minestar.cok.references.Color;
import net.minecraft.command.ICommandSender;

public class ChatSendHelper {

	public static void sendMessage(ICommandSender sender, String text){
		sender.sendChatToPlayer(Color.getColorCodeFromString("gray") + "[CoK] " + text);
	}
	
	public static void sendError(ICommandSender sender, String text){
		sender.sendChatToPlayer(Color.getColorCodeFromString("red") + "[CoK] " + text);
	}
	
}
