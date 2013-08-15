package de.minestar.cok.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Team;
import de.minestar.cok.references.Color;
import de.minestar.cok.references.Reference;

public class CoKGamePacket {
	
	/**
	 * sets the gamestate of clients on login.
	 * @param inputStream
	 */
	public static void setGameState(DataInputStream inputStream){
		try {
			//read teams
			int numberOfPlayers = 0;
			int numberOfTeams = inputStream.readInt();
			for(int i = 0; i < numberOfTeams; i++){
				String teamName = inputStream.readUTF();
				CoKGame.addTeam(teamName, inputStream.readChar());
				//read users
				numberOfPlayers = inputStream.readInt();
				for(int j = 0; j < numberOfPlayers; i++){
					CoKGame.addPlayerToTeam(teamName, inputStream.readUTF());
				}
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void addTeam(DataInputStream inputStream){
		try {
			String teamName = inputStream.readUTF();
			String color = inputStream.readUTF();
			CoKGame.addTeam(teamName, Color.getColorFromString(color));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeTeam(DataInputStream inputStream){
		try {
			String teamName = inputStream.readUTF();
			CoKGame.removeTeam(teamName);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void addPlayer(DataInputStream inputStream){
		try {
			String teamName = inputStream.readUTF();
			String playerName = inputStream.readUTF();
			CoKGame.addPlayerToTeam(teamName, playerName);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void removePlayer(DataInputStream inputStream){
		try {
			String teamName = inputStream.readUTF();
			String playerName = inputStream.readUTF();
			CoKGame.removePlayerFromTeam(teamName, playerName);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void sendPacketToAllPlayers(byte code, String[] data){
		ByteArrayOutputStream bos = new ByteArrayOutputStream(0);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeByte(code);
			for(int i = 0; i < data.length; i++){
				outputStream.writeUTF(data[i]);
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
		PacketDispatcher.sendPacketToAllPlayers(packet);
	}
	
	/**
	 * send the gamestate to one specific Player
	 */
	public static void sendGameStateToPlayer(Player player){
		ByteArrayOutputStream bos = new ByteArrayOutputStream(0);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeByte(PacketHandler.GAME_STATE);
			//write teams
			outputStream.writeInt(CoKGame.teams.size());
			for(Team team: CoKGame.teams.values()){
				outputStream.writeUTF(team.getName());
				outputStream.writeChar(team.getColor());
				int teamSize = team.getAllPlayers().size();
				//write players of team
				outputStream.writeInt(teamSize);
				for(String playerName: team.getAllPlayers()){
					outputStream.writeUTF(playerName);
				}
				
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		
		//build packet
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = Reference.CHANNEL_NAME;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		//send packet
		PacketDispatcher.sendPacketToPlayer(packet, player);
	}
	
}
