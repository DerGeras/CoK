package de.minestar.cok.hook;

import java.util.ArrayList;
import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Team;
import de.minestar.cok.profession.Profession;

public class ServerTickHandler implements ITickHandler {

	public static boolean isScoreCheckQueued = false;
	public static ArrayList<String> changedProfessions = new ArrayList<String>();

	private final EnumSet<TickType> ticksToGet;

	public ServerTickHandler(EnumSet<TickType> ticksToGet) {
		this.ticksToGet = ticksToGet;
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// currently nothing
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && CoKGame.gameRunning) {
			if (isScoreCheckQueued) {
				CoKGame.checkWinningCondition();
			}
			isScoreCheckQueued = false;

			for (String playerName : changedProfessions) {
				EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(playerName);
				Team team = CoKGame.getTeamOfPlayer(playerName);
				Profession profession = CoKGame.playerProfessions.get(playerName);
				if (player != null && profession != null && team != null) {
					profession.giveKit(player, team);
				}
			}
			changedProfessions.clear();
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return ticksToGet;
	}

	@Override
	public String getLabel() {
		return "ServerTickHandler";
	}

}
