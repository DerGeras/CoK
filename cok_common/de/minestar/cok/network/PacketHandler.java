package de.minestar.cok.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import de.minestar.cok.network.packets.CoKPacket;
import de.minestar.cok.network.packets.PacketGameSetGamestate;
import de.minestar.cok.network.packets.PacketGameStart;
import de.minestar.cok.network.packets.PacketGameStop;
import de.minestar.cok.network.packets.PacketGameUpdateRunning;
import de.minestar.cok.network.packets.PacketPlayerAdd;
import de.minestar.cok.network.packets.PacketPlayerRemove;
import de.minestar.cok.network.packets.PacketSpectatorAdd;
import de.minestar.cok.network.packets.PacketSpectatorRemove;
import de.minestar.cok.network.packets.PacketTeamAdd;
import de.minestar.cok.network.packets.PacketTeamRemove;
import de.minestar.cok.references.Reference;

public class PacketHandler implements IPacketHandler {

	public static final byte TEAM_ADD = 1;
	public static final byte TEAM_REMOVE = 2;
	public static final byte PLAYER_ADD = 3;
	public static final byte PLAYER_REMOVE = 4;
	public static final byte CHANGE_CAPTAIN = 5;
	public static final byte GAME_STATE = 6;
	public static final byte GAME_UPDATE_RUNNING = 7;
	public static final byte START_GAME_COMMAND = 8;
	public static final byte STOP_GAME_COMMAND = 9;
	public static final byte SPECTATOR_ADD = 10;
	public static final byte SPECTATOR_REMOVE = 11;

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if (packet.channel.equals(Reference.CHANNEL_NAME)) {
			handlePacket(packet, player);
		}
	}

	private void handlePacket(Packet250CustomPayload rawPacket, Player player) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(rawPacket.data));
		byte code = 0;

		try {
			code = inputStream.readByte();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		CoKPacket packet = null;
		try {
			switch (code) {
			// game state
				case TEAM_ADD :
					packet = new PacketTeamAdd(inputStream, player);
					break;
				case TEAM_REMOVE :
					packet = new PacketTeamRemove(inputStream, player);
					break;
				case PLAYER_ADD :
					packet = new PacketPlayerAdd(inputStream, player);
					break;
				case PLAYER_REMOVE :
					packet = new PacketPlayerRemove(inputStream, player);
					break;
				case GAME_STATE :
					packet = new PacketGameSetGamestate(inputStream, player);
					break;
				case GAME_UPDATE_RUNNING :
					packet = new PacketGameUpdateRunning(inputStream, player);
					break;
				case SPECTATOR_ADD :
					packet = new PacketSpectatorAdd(inputStream, player);
					break;
				case SPECTATOR_REMOVE :
					packet = new PacketSpectatorRemove(inputStream, player);
					break;

				// commands
				case START_GAME_COMMAND :
					packet = new PacketGameStart(inputStream, player);
					break;
				case STOP_GAME_COMMAND :
					packet = new PacketGameStop(inputStream, player);
					break;
				default :
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			packet = null;
		}

		if (packet == null) {
			return;
		}

		try {
			packet.handleIncomingPacket();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

	}

}
