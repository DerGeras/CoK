package de.minestar.cok.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.network.Player;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.network.PacketHandler;
import de.minestar.cok.references.Color;

public class PacketPlayerAdd extends CoKPacket {

	private String teamName, playerName;

	public PacketPlayerAdd(String teamName, String playerName) {
		super(PacketHandler.PLAYER_ADD);
		this.teamName = teamName;
		this.playerName = playerName;
	}

	public PacketPlayerAdd(DataInputStream input, Player player) throws Exception {
		super(input, player);
	}

	@Override
	protected void read(DataInputStream input) throws Exception {
		teamName = input.readUTF();
		playerName = input.readUTF();
	}

	@Override
	public void handleIncomingPacket() {
		CoKGame.addPlayerToTeam(teamName, playerName);
	}

	@Override
	public void pack(DataOutputStream output) throws Exception {
		output.writeUTF(teamName);
		output.writeUTF(playerName);
	}

}
