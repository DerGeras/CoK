package de.minestar.cok.packet;

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
	public static final byte SCORE = 6;

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
		case TEAM_ADD: CoKGamePacket.addTeam(inputStream); break;
		case TEAM_REMOVE: CoKGamePacket.removeTeam(inputStream); break;
		case PLAYER_ADD: CoKGamePacket.addPlayer(inputStream); break;
		case PLAYER_REMOVE: CoKGamePacket.removePlayer(inputStream); break;
		default: return;
		}
	}

}
