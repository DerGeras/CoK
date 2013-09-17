package de.minestar.cok.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.network.Player;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.network.PacketHandler;

public class PacketGameUpdateRunning extends CoKPacket {

	private boolean gameRunning;

	public PacketGameUpdateRunning(boolean isRunning) {
		super(PacketHandler.GAME_UPDATE_RUNNING);
		this.gameRunning = isRunning;
	}

	public PacketGameUpdateRunning(DataInputStream input, Player player) throws Exception {
		super(input, player);
	}

	@Override
	protected void read(DataInputStream input) throws Exception {
		this.gameRunning = input.readBoolean();
	}

	@Override
	public void handleIncomingPacket() {
		CoKGame.gameRunning = this.gameRunning;
	}

	@Override
	public void pack(DataOutputStream output) throws Exception {
		output.writeBoolean(this.gameRunning);
	}

}
