package de.minestar.cok.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.Player;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Team;
import de.minestar.cok.network.PacketHandler;

public class PacketGameSetGamestate extends CoKPacket {

	public PacketGameSetGamestate() {
		super(PacketHandler.GAME_STATE);
	}

	public PacketGameSetGamestate(DataInputStream input, Player player) throws Exception {
		super(input, player);
	}

	@Override
	protected void read(DataInputStream input) {
		try {
			// read gameState
			CoKGame.gameRunning = input.readBoolean();
			// read teams
			int numberOfTeams = input.readInt();
			int numberOfPlayers = 0;
			CoKGame.teams.clear();
			for (int i = 0; i < numberOfTeams; i++) {
				String teamName = input.readUTF();
				CoKGame.addTeam(teamName, input.readChar());
				// read users
				numberOfPlayers = input.readInt();
				for (int j = 0; j < numberOfPlayers; i++) {
					CoKGame.addPlayerToTeam(teamName, input.readUTF());
				}
			}
			int numberOfSpectators = input.readInt();
			CoKGame.spectators.clear();
			for (int i = 0; i < numberOfSpectators; i++) {
				updateSpectatorMode(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateSpectatorMode(DataInputStream inputStream) {
		try {
			String playerName = inputStream.readUTF();
			CoKGame.setPlayerSpectator(playerName);
			EntityPlayer thisPlayer = Minecraft.getMinecraft().thePlayer;
			if (thisPlayer != null && thisPlayer.username.equalsIgnoreCase(playerName)) {
				thisPlayer.capabilities.allowFlying = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleIncomingPacket() {
		// no need here, this is a specialcase...
	}

	@Override
	public void pack(DataOutputStream outputStream) throws Exception {
		// write gameState
		outputStream.writeBoolean(CoKGame.gameRunning);

		// write teams
		outputStream.writeInt(CoKGame.teams.size());

		// iterate over teams..
		for (Team team : CoKGame.teams.values()) {
			// write teamName
			outputStream.writeUTF(team.getName());

			// write teamColor
			outputStream.writeChar(team.getColor());

			// write teamSize
			int teamSize = team.getAllPlayers().size();

			// write playercount
			outputStream.writeInt(teamSize);

			// iterate over players...
			for (String playerName : team.getAllPlayers()) {
				outputStream.writeUTF(playerName);
			}
		}
		// write spectatorcount
		outputStream.writeInt(CoKGame.spectators.size());

		// iterate over spectators...
		for (String spectator : CoKGame.spectators) {
			outputStream.writeUTF(spectator);
		}
	}

}
