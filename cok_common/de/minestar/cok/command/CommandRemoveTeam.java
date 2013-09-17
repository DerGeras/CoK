package de.minestar.cok.command;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.network.CoKGamePacketServer;
import de.minestar.cok.network.PacketHandler;
import de.minestar.cok.references.Reference;

public class CommandRemoveTeam extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.RemoveTeamCommand;
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return getCommandName() + " Team";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if (astring.length < 1) {
			ChatSendHelper.sendMessage(icommandsender, "Usage: " + getCommandUsage(icommandsender));
			return;
		}
		if (CoKGame.removeTeam(astring[0])) {
			CoKGamePacketServer.sendPacketToAllPlayers(PacketHandler.TEAM_REMOVE, astring);

			ChatSendHelper.sendMessage(icommandsender, "Team \"" + astring[0] + "\" successfully removed!");
		} else {
			ChatSendHelper.sendError(icommandsender, "Team \"" + astring[0] + "\" could not be removed!");
		}
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender icommandsender, String[] astring) {
		// return teamNames
		LinkedList<String> list = new LinkedList<String>();
		for (String teamName : CoKGame.teams.keySet()) {
			list.add(teamName);
		}
		return list;
	}

}
