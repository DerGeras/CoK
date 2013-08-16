package de.minestar.cok.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.references.Reference;

public class CoKCommandPacket {
	
	public static void startGame(){
		if(!(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) || CoKGame.gameRunning){
			return;
		}
		CoKGame.startGame();
		//send state to clients
		CoKGamePacket.sendPacketToAllPlayers(PacketHandler.GAME_RUNNING, true);
	}
	
	public static void stopGame(){
		if(!(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) || !CoKGame.gameRunning){
			return;
		}
		CoKGame.stopGame();
		CoKGamePacket.sendPacketToAllPlayers(PacketHandler.GAME_RUNNING, false);
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
