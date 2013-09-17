package de.minestar.cok.network.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.network.PacketHelper;
import de.minestar.cok.network.PacketHandler;

public class PacketGameStart extends CoKPacket {

	public PacketGameStart() {
		super(PacketHandler.START_GAME_COMMAND);
	}

	public PacketGameStart(DataInputStream input, Player player) throws Exception {
		super(input, player);
	}

	@Override
	protected void read(DataInputStream input) {
	}

	@Override
	public void handleIncomingPacket() {
		if (!(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) || CoKGame.gameRunning) {
			return;
		}
		EntityPlayerMP playerMP = (EntityPlayerMP) this.player;
		if (MinecraftServer.getServer().isDedicatedServer() && !MinecraftServer.getServer().getConfigurationManager().getOps().contains(playerMP.username.toLowerCase().trim())) {
			return; // Only OPs are allowed to start a game.
		}
		CoKGame.startGame();

		// send state to clients
		PacketHelper.sendPacketToAllPlayers(new PacketGameUpdateRunning(true));
	}

	@Override
	public void pack(DataOutputStream output) {
	}

}
