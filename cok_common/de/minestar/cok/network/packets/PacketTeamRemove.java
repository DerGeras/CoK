package de.minestar.cok.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.network.Player;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.network.PacketHandler;

public class PacketTeamRemove extends CoKPacket {

	private String teamName;

	public PacketTeamRemove(String teamName) {
		super(PacketHandler.TEAM_REMOVE);
		this.teamName = teamName;
	}

	public PacketTeamRemove(DataInputStream input, Player player) throws Exception {
		super(input, player);
	}

	@Override
	protected void read(DataInputStream input) throws Exception {
		teamName = input.readUTF();
	}

	@Override
	public void handleIncomingPacket() {
		CoKGame.removeTeam(teamName);
	}

	@Override
	public void pack(DataOutputStream output) throws Exception {
		output.writeUTF(teamName);
	}

}
