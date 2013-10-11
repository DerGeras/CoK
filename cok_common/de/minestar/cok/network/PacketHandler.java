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

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import de.minestar.cok.references.Reference;

public class PacketHandler implements IPacketHandler{
	
	public static final byte TEAM_ADD = 1;
	public static final byte TEAM_REMOVE = 2;
	public static final byte PLAYER_ADD = 3;
	public static final byte PLAYER_REMOVE = 4;
	public static final byte CHANGE_CAPTAIN = 5;
	public static final byte GAME_STATE = 6;
	public static final byte GAME_RUNNING = 7;
	public static final byte START_GAME_COMMAND = 8;
	public static final byte STOP_GAME_COMMAND = 9;
	public static final byte SCORE_UPDATE = 10;

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		
		if(packet.channel.equals(Reference.CHANNEL_NAME)){
			handlePacket(packet, player);
		}
		
	}
	
	private void handlePacket(Packet250CustomPayload packet, Player player){
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		byte code = 0;
		
		try{
			code = inputStream.readByte();
		} catch(Exception e){
			e.printStackTrace();
			return;
		}
		switch(code){
		//game state
		case TEAM_ADD: CoKGamePacketClient.addTeam(inputStream); break;
		case TEAM_REMOVE: CoKGamePacketClient.removeTeam(inputStream); break;
		case PLAYER_ADD: CoKGamePacketClient.addPlayer(inputStream); break;
		case PLAYER_REMOVE: CoKGamePacketClient.removePlayer(inputStream); break;
		case GAME_STATE: CoKGamePacketClient.setGameState(inputStream); break;
		case GAME_RUNNING: CoKGamePacketClient.updateGameRunning(inputStream); break;
		case SCORE_UPDATE: CoKGamePacketClient.setScore(inputStream); break;
		
		//commands
		case START_GAME_COMMAND: CoKCommandPacket.startGame(player);break;
		case STOP_GAME_COMMAND: CoKCommandPacket.stopGame(player); break;
		default: return;
		}
	}

}
