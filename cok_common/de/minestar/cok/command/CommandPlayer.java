package de.minestar.cok.command;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.helper.PlayerHelper;
import de.minestar.cok.network.CoKGamePacket;
import de.minestar.cok.network.PacketHandler;
import de.minestar.cok.references.Reference;

public class CommandPlayer extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.PlayerCommand;
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return getCommandName() + " [add|remove] team player";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if(astring.length < 3){
			ChatSendHelper.sendMessage(icommandsender, "Usage: " + getCommandUsage(icommandsender));
			return;
		}
		if(!CoKGame.teams.containsKey(astring[1])){
			ChatSendHelper.sendError(icommandsender, "Team \"" + astring[1] + "\" could not be found!");
			return;
		}
		if(astring[0].equalsIgnoreCase("add")){
			if(!PlayerHelper.isOnlineUser(astring[2])){
				ChatSendHelper.sendError(icommandsender, "Could not find player \"" + astring[2] + "\"!");
				return;
			}
			if(!CoKGame.addPlayerToTeam(astring[1], astring[2])){
				ChatSendHelper.sendError(icommandsender, "Could not add user \"" + astring[2] + "\" to team \"" + astring[1] + "\"!");
			} else{
				CoKGamePacket.sendPacketToAllPlayers(PacketHandler.PLAYER_ADD, Arrays.copyOfRange(astring, 1, 3));
				
				ChatSendHelper.sendMessage(icommandsender, "User \"" + astring[2] + "\" successfully added to team \"" + astring[1] + "\"!");
			}
			return;
		}
		if(astring[0].equals("remove")){
			if(!CoKGame.getTeam(astring[1]).hasPlayer(astring[2])){
				ChatSendHelper.sendError(icommandsender, "User \"" + astring[2] + "\" is not part of team \"" + astring[1] + "\"!");
				return;
			}
			if(!CoKGame.removePlayerFromTeam(astring[1], astring[2])){
				ChatSendHelper.sendError(icommandsender, "Could not remove user \"" + astring[2] + "\" from team \"" + astring[1] + "\"!");
			} else{
				CoKGamePacket.sendPacketToAllPlayers(PacketHandler.PLAYER_REMOVE, Arrays.copyOfRange(astring, 1, 3));
				
				ChatSendHelper.sendMessage(icommandsender, "User \"" + astring[2] + "\" successfully removed from team \"" + astring[1] + "\"!");
			}
			return;
		}
		ChatSendHelper.sendMessage(icommandsender, "Usage: " + getCommandUsage(icommandsender));
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring) {
		LinkedList<String> list = new LinkedList<String>();
		if(astring.length <= 1){
			list.add("add");
			list.add("remove");
		}
		return list;
	}
	
	@Override
	public boolean isUsernameIndex(String[] astring, int i) {
		return true; //TODO check this method
	}

}
