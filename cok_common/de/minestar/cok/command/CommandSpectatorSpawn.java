package de.minestar.cok.command;

import java.util.List;

import de.minestar.cok.game.CoKGame;
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.references.Reference;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;

public class CommandSpectatorSpawn extends CoKCommand {

	@Override
	public String getCommandName() {
		return Reference.SpectatorSpawnCommand;
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return getCommandName();
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if(icommandsender instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) icommandsender;
			ChunkCoordinates coords = new ChunkCoordinates();
			coords.posX = (int)player.posX;
			coords.posY = (int)player.posY;
			coords.posZ = (int)player.posY;
			CoKGame.spectatorSpawn = coords;
			for(String playername : MinecraftServer.getServer().getConfigurationManager().getAllUsernames()){
				if(CoKGame.getTeamOfPlayer(playername) == null){
					CoKGame.setPlayerSpectator(playername);
				}
			}
			ChatSendHelper.sendMessage(icommandsender, "Spectator spawn set to " + coords.posX
					+ ", " + coords.posY + ", " + coords.posZ);
		}
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring) {
		return null;
	}

}
