package de.minestar.cok.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.common.network.Player;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.network.PacketHandler;
import de.minestar.cok.references.Color;

public class PacketSpectatorRemove extends CoKPacket {

	private String playerName;

	public PacketSpectatorRemove(String playerName) {
		super(PacketHandler.SPECTATOR_REMOVE);
		this.playerName = playerName;
	}

	public PacketSpectatorRemove(DataInputStream input, Player player) throws Exception {
		super(input, player);
	}

	@Override
	protected void read(DataInputStream input) throws Exception {
		playerName = input.readUTF();
	}

	@Override
	public void handleIncomingPacket() {
		EntityPlayer thisPlayer = Minecraft.getMinecraft().thePlayer;
		if (thisPlayer != null && thisPlayer.username.equalsIgnoreCase(playerName)) {
			thisPlayer.capabilities.allowFlying = false;
		}
	}

	@Override
	public void pack(DataOutputStream output) throws Exception {
		output.writeUTF(playerName);
	}

}
