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

import java.util.HashSet;

import net.minecraftforge.event.ForgeSubscribe;
import de.minestar.cok.event.EventBlockBreak;
import de.minestar.cok.event.EventBlockPlace;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.tileentity.TileEntitySocket;

public class BlockListener {
	
	@ForgeSubscribe
	public void onBlockBreak(EventBlockBreak event){
		if(event.player.capabilities.isCreativeMode){
			return; //Creative Mode is always allowed
		}
		if(!CoKGame.gameRunning || CoKGame.getTeamOfPlayer(event.player.username) == null){
			event.setCanceled(true);
			return;
		}
		for(HashSet<TileEntitySocket> teamSockets : CoKGame.sockets.values()){
			for(TileEntitySocket socket : teamSockets){
				socket.checkEvent(event);
			}
		}
	}
	
	@ForgeSubscribe
	public void onBlockPlace(EventBlockPlace event){
		if(event.player.capabilities.isCreativeMode){
			return; //Creative Mode is always allowed
		}
		if(!CoKGame.gameRunning || CoKGame.getTeamOfPlayer(event.player.username) == null){
			event.setCanceled(true);
			return;
		}
		for(HashSet<TileEntitySocket> teamSockets : CoKGame.sockets.values()){
			for(TileEntitySocket socket : teamSockets){
				socket.checkEvent(event);
			}
		}
	}

}
