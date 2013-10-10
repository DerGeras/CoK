/*******************************************************************************
 * Clash of Kingdoms Minecraft Mod
 *     Copyright (C) 2013 Geras
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.profession.Profession;
import de.minestar.cok.references.Color;

public class ServerTickHandler implements ITickHandler {
	

	public static boolean isScoreCheckQueued = false;
	public static ArrayList<String> changedProfessions = new ArrayList<String>();
	
	private final EnumSet<TickType> ticksToGet;
	
	public ServerTickHandler(EnumSet<TickType> ticksToGet){
		this.ticksToGet = ticksToGet;
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		//currently nothing
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && CoKGame.gameRunning){
			if(isScoreCheckQueued){
				CoKGame.checkWinningCondition();
			}
			isScoreCheckQueued = false;
			
			for(String playerName : changedProfessions){
				EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(playerName);
				Team team = CoKGame.getTeamOfPlayer(playerName);
				Profession profession = CoKGame.playerProfessions.get(playerName);
				if(player != null && profession != null && team != null){
					profession.giveKit(player, team);
					ChatSendHelper.sendMessageToPlayer(player, "You are now a " + profession.getClassName() + "!");
					ChatSendHelper.broadCastError(player.username + "is the new " + profession.getClassName() + " of team "
							+ Color.getColorCodeFromChar(team.getColor()) + team.getName());
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
