package de.minestar.cok.util;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Team;

public class ChatSendHelper {
	
	public static final String MESSAGE_COLORCODE = Color.getColorCodeFromString("white");
	public static final String ERROR_COLORCODE = Color.getColorCodeFromString("red");
	public static final String BROADCAST_MESSAGE_COLORCODE = Color.getColorCodeFromString("blue");

	public static void sendMessageToPlayer(ICommandSender sender, String text){
		sender.addChatMessage(new ChatComponentText(String.format("%s[CoK] %s", MESSAGE_COLORCODE, text)));
	}
	
	public static void sendErrorMessageToPlayer(ICommandSender sender, String text){
		sender.addChatMessage(new ChatComponentText(String.format("%s[CoK] %s", ERROR_COLORCODE, text)));
	}
	
	public static void broadCastMessage(String text){
		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(
				new ChatComponentText(String.format("%s[CoK] %s", BROADCAST_MESSAGE_COLORCODE, text)));
	}
	
	public static void broadCastError(String text){
		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(
				new ChatComponentText(String.format("%s[CoK] %s", ERROR_COLORCODE, text)));
	}
	
	public static void broadCastMessageToGame(CoKGame game, String text){
		for(EntityPlayer player : PlayerHelper.getPlayerEntitiesForGame(game)){
			sendMessageToPlayer(player, text);
		}
	}
	
	public static void broadCastErrorToGame(CoKGame game, String text){
		for(EntityPlayer player : PlayerHelper.getPlayerEntitiesForGame(game)){
			sendErrorMessageToPlayer(player, text);
		}
	}
	
	
	public static void broadCastMessageToTeam(Team team, String text){
		for(EntityPlayer player : PlayerHelper.getPlayerEntitiesForTeam(team)){
			sendMessageToPlayer(player, text);
		}
	}
	
	public static void broadCastErrorToTeam(Team team, String text){
		for(EntityPlayer player : PlayerHelper.getPlayerEntitiesForTeam(team)){
			sendErrorMessageToPlayer(player, text);
		}
	}
	
	
}
