package de.minestar.cok.command;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Team;
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.profession.Profession;
import de.minestar.cok.references.Reference;

public class CommandKit extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.KitCommand;
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return false; //CoKGame.getTeamOfPlayer(icommandsender.getCommandSenderName()) != null;
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return getCommandName();
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if(!CoKGame.gameRunning){
			ChatSendHelper.sendError(icommandsender, "The game is not running!");
		}
		//Check for team and profession
		Team team = CoKGame.getTeamOfPlayer(icommandsender.getCommandSenderName());
		Profession profession = CoKGame.playerProfessions.get(icommandsender.getCommandSenderName());
		if(team == null || profession == null){
			ChatSendHelper.sendError(icommandsender, "You are not in a team!");
			return;
		}
		((EntityPlayer) icommandsender).inventory.clearInventory(-1, -1);
		profession.giveKit((EntityPlayer) icommandsender, team);

	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring) {
		// TODO Auto-generated method stub
		return null;
	}

}
