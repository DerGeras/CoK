package de.minestar.cok.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.network.Player;

public abstract class CoKPacket {

	protected int packetID;
	protected Player player;

	public CoKPacket(int packetID) {
		this.packetID = packetID;
		this.player = null;
	}

	public CoKPacket(DataInputStream input, Player player) throws Exception {
		this.player = player;
		this.read(input);
	}

	public int getPacketID() {
		return packetID;
	}

	protected abstract void read(DataInputStream input) throws Exception;

	public abstract void handleIncomingPacket();

	public abstract void pack(DataOutputStream output) throws Exception;
}
