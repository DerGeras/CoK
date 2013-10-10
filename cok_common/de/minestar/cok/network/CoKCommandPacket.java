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
package de.minestar.cok.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Team;
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.references.Reference;

public class CoKCommandPacket {
	
	public static void startGame(Player player){
		if(!(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) || CoKGame.gameRunning){
			return;
		}
		EntityPlayerMP playerMP = (EntityPlayerMP) player;
		if(MinecraftServer.getServer().isDedicatedServer() && !MinecraftServer.getServer().getConfigurationManager().getOps().contains(playerMP.username.toLowerCase().trim())){
			return; //Only OPs are allowed to start a game.
		}
		//Check for set spawn locations
		boolean error = false;
		if(CoKGame.spectatorSpawn == null){
			error = true;
			ChatSendHelper.sendError(playerMP, "Spectator Spawn has not been set!");
		}
		for(Team team : CoKGame.teams.values()){
			if(team.getSpawnCoordinates() == null){
				error = true;
				ChatSendHelper.sendError(playerMP, "Spawn of team " + team.getName() + " has not been set!");
			}
		}
		
		if(error){
			ChatSendHelper.sendError(playerMP, "Game could not be started!");
		} else{
			CoKGame.startGame();
			//send state to clients
			CoKGamePacketServer.sendPacketToAllPlayers(PacketHandler.GAME_RUNNING, true);
		}
	}
	
	public static void stopGame(Player player){
		if(!(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) || !CoKGame.gameRunning){
			return;
		}
		EntityPlayerMP playerMP = (EntityPlayerMP) player;
		if(MinecraftServer.getServer().isDedicatedServer() && !MinecraftServer.getServer().getConfigurationManager().getOps().contains(playerMP.username.toLowerCase().trim())){
			return; //Only OPs are allowed to stop a game on a dedicated server.
		}
		CoKGame.stopGame();
		CoKGamePacketServer.sendPacketToAllPlayers(PacketHandler.GAME_RUNNING, false);
	}
	
	
	public static void sendPacketToServer(byte code, String[] params){
		ByteArrayOutputStream bos = new ByteArrayOutputStream(0);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeByte(code);
			if(params != null){
				for(int i = 0; i < params.length; i++){
					outputStream.writeUTF(params[i]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//build packet
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = Reference.CHANNEL_NAME;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		//send packet
		PacketDispatcher.sendPacketToServer(packet);
	}
	
}
