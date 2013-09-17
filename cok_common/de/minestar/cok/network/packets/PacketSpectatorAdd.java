package de.minestar.cok.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.Player;
import de.minestar.cok.network.PacketHandler;

public class PacketSpectatorAdd extends CoKPacket {

	private String playerName;

	public PacketSpectatorAdd(String playerName) {
		super(PacketHandler.SPECTATOR_ADD);
		this.playerName = playerName;
	}

	public PacketSpectatorAdd(DataInputStream input, Player player) throws Exception {
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
			thisPlayer.capabilities.allowFlying = true;
		}
	}

	@Override
	public void pack(DataOutputStream output) throws Exception {
		output.writeUTF(playerName);
	}

}
