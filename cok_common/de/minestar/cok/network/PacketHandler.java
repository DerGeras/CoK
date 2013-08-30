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
	public static final byte SPECTATOR_ADD = 10;
	public static final byte SPECTATOR_REMOVE = 11;

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
		case TEAM_ADD: CoKGamePacket.addTeam(inputStream); break;
		case TEAM_REMOVE: CoKGamePacket.removeTeam(inputStream); break;
		case PLAYER_ADD: CoKGamePacket.addPlayer(inputStream); break;
		case PLAYER_REMOVE: CoKGamePacket.removePlayer(inputStream); break;
		case GAME_STATE: CoKGamePacket.setGameState(inputStream); break;
		case GAME_RUNNING: CoKGamePacket.updateGameRunning(inputStream); break;
		case SPECTATOR_ADD: CoKGamePacket.addSpectator(inputStream); break;
		case SPECTATOR_REMOVE: CoKGamePacket.removeSpectator(inputStream); break;
		
		//commands
		case START_GAME_COMMAND: CoKCommandPacket.startGame(player);break;
		case STOP_GAME_COMMAND: CoKCommandPacket.stopGame(player); break;
		default: return;
		}
	}

}
