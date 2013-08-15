package de.minestar.cok.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.PacketDispatcher;

import de.minestar.cok.game.CoKGame;
import de.minestar.cok.references.Color;
import de.minestar.cok.references.Reference;

public class CoKGamePacket {

	public static void addTeam(DataInputStream inputStream){
		String teamName;
		String color;
		try {
			teamName = inputStream.readUTF();
			color = inputStream.readUTF();
			CoKGame.addTeam(teamName, Color.getColorFromString(color));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeTeam(DataInputStream inputStream){
		String teamName;
		try {
			teamName = inputStream.readUTF();
			CoKGame.removeTeam(teamName);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void addPlayer(DataInputStream inputStream){
		String teamName;
		String playerName;
		try {
			teamName = inputStream.readUTF();
			playerName = inputStream.readUTF();
			CoKGame.addPlayerToTeam(teamName, playerName);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void removePlayer(DataInputStream inputStream){
		String teamName;
		String playerName;
		try {
			teamName = inputStream.readUTF();
			playerName = inputStream.readUTF();
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
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = Reference.CHANNEL_NAME;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToAllPlayers(packet);
	}
	
}
