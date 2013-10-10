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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Team;
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.network.CoKGamePacketServer;
import de.minestar.cok.profession.Profession;

public class PlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {
        	CoKGamePacketServer.sendGameStateToPlayer((Player) player);
        	Team team = CoKGame.getTeamOfPlayer(player.username);
        	if(team == null){
        		CoKGame.setPlayerSpectator((EntityPlayerMP) player);
        	}
        	if(team != null){
        		team.playerReturned(player.username);
        		ChunkCoordinates spawnCoordinates = team.getSpawnCoordinates();
        		if(spawnCoordinates != null){
    				player.setSpawnChunk(spawnCoordinates, true);
    			}
        	}
        }

	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {
        	Team team = CoKGame.getTeamOfPlayer(player.username);
        	if(team != null){
        		String captain = team.getCaptain();
        		team.playerGone(player.username);
        		if(CoKGame.gameRunning && captain.equalsIgnoreCase(player.username)){
        			ChatSendHelper.broadCastError(captain + " ,the king of team " + team.getName() + " fled!");
        			ChatSendHelper.broadCastError("Long life king " + team.getCaptain() + "!");
        		}
        		//remove given Items
    			for(int i = 0; i < 36; i++){
    				if(Profession.isGivenItem(player.inventory.getStackInSlot(i))){
    					player.inventory.setInventorySlotContents(i, null);
    				}
    			}
    			for(int i = 0; i < 4; i++){
    				if(Profession.isGivenItem(player.inventory.armorItemInSlot(i))){
    					player.inventory.armorInventory[i] = null;
    				}
    			}
        	} else{
        		CoKGame.removeSpectator(player);
        	}
        }

	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {
        	Team team = CoKGame.getTeamOfPlayer(player.username);
			if(team != null){
				team.playerReturned(player.username);
			}			
			if(team == null){
        		CoKGame.setPlayerSpectator((EntityPlayerMP) player);
        	}
        }
	}

}
