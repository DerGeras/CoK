package de.minestar.cok.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.network.Player;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.network.PacketHandler;
import de.minestar.cok.references.Color;

public class PacketTeamAdd extends CoKPacket {

	private String teamName, color;

	public PacketTeamAdd(String teamName, String color) {
		super(PacketHandler.TEAM_ADD);
		this.teamName = teamName;
		this.color = color;
	}

	public PacketTeamAdd(DataInputStream input, Player player) throws Exception {
		super(input, player);
	}

	@Override
	protected void read(DataInputStream input) throws Exception {
		teamName = input.readUTF();
		color = input.readUTF();
	}

	@Override
	public void handleIncomingPacket() {
		CoKGame.addTeam(teamName, Color.getColorFromString(color));
	}

	@Override
	public void pack(DataOutputStream output) throws Exception {
		output.writeUTF(teamName);
		output.writeUTF(color);
	}

}
