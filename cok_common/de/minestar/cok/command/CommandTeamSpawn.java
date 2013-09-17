package de.minestar.cok.command;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChunkCoordinates;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Team;
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.references.Reference;

public class CommandTeamSpawn extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.TeamSpawnCommand;
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return getCommandName() + " teamName";
	}

	/**
	 * set the spawn for a specified team
	 */
	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if (astring.length < 1) {
			ChatSendHelper.sendError(icommandsender, "Usage: " + getCommandUsage(icommandsender));
			return;
		}
		String teamName = astring[0];
		if (teamName != null) {
			Team team = CoKGame.getTeam(teamName);
			if (team == null) {
				ChatSendHelper.sendError(icommandsender, "Could not find team " + teamName);
				return;
			}
			ChunkCoordinates coords = icommandsender.getPlayerCoordinates();
			team.setSpawnCoordinates(coords);
			ChatSendHelper.sendMessage(icommandsender, "Spawn for team " + teamName + " set to " + coords.posX + ", " + coords.posY + ", " + coords.posZ);
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
