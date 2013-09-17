package de.minestar.cok.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import de.minestar.cok.network.packets.CoKPacket;
import de.minestar.cok.references.Reference;

public class PacketHelper {

	public static void sendPacketToServer(CoKPacket packet) {
		// pack COK-Packet
		ByteArrayOutputStream bos = new ByteArrayOutputStream(0);
		DataOutputStream output = new DataOutputStream(bos);
		try {
			output.writeByte(packet.getPacketID());
			packet.pack(output);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// build RAW-Packet
		Packet250CustomPayload rawPacket = new Packet250CustomPayload();
		rawPacket.channel = Reference.CHANNEL_NAME;
		rawPacket.data = bos.toByteArray();
		rawPacket.length = bos.size();

		// send RAW-Packet
		PacketDispatcher.sendPacketToAllPlayers(rawPacket);
	}

	public static void sendPacketToAllPlayers(CoKPacket packet) {
		// pack COK-Packet
		ByteArrayOutputStream bos = new ByteArrayOutputStream(0);
		DataOutputStream output = new DataOutputStream(bos);
		try {
			// write packet-ID
			output.writeByte(packet.getPacketID());
			// pack data
			packet.pack(output);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// build RAW-Packet
		Packet250CustomPayload rawPacket = new Packet250CustomPayload();
		rawPacket.channel = Reference.CHANNEL_NAME;
		rawPacket.data = bos.toByteArray();
		rawPacket.length = bos.size();

		// send RAW-Packet
		PacketDispatcher.sendPacketToAllPlayers(rawPacket);
	}

	public static void sendPacketToPlayer(CoKPacket packet, Player player) {
		// pack COK-Packet
		ByteArrayOutputStream bos = new ByteArrayOutputStream(0);
		DataOutputStream output = new DataOutputStream(bos);
		try {
			// write packet-ID
			output.writeByte(packet.getPacketID());
			// pack data
			packet.pack(output);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// build RAW-Packet
		Packet250CustomPayload rawPacket = new Packet250CustomPayload();
		rawPacket.channel = Reference.CHANNEL_NAME;
		rawPacket.data = bos.toByteArray();
		rawPacket.length = bos.size();

		// send RAW-Packet
		PacketDispatcher.sendPacketToPlayer(rawPacket, player);
	}
}
