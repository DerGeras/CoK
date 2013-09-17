package de.minestar.cok.hook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.ChunkCoordinates;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Team;
import de.minestar.cok.helper.ChatSendHelper;
import de.minestar.cok.network.PacketHelper;
import de.minestar.cok.network.packets.PacketGameSetGamestate;
import de.minestar.cok.network.packets.PacketSpectatorAdd;
import de.minestar.cok.network.packets.PacketSpectatorRemove;
import de.minestar.cok.profession.Profession;

public class PlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER) {
			// send gamestate
			PacketHelper.sendPacketToPlayer(new PacketGameSetGamestate(), (Player) player);
			// update spectators
			if (CoKGame.gameRunning && CoKGame.getTeamOfPlayer(player.username) == null) {
				CoKGame.setPlayerSpectator((EntityPlayerMP) player);
				PacketHelper.sendPacketToAllPlayers(new PacketSpectatorAdd(player.username));
			}
			// update spawncoordinates and stay in line with the old team (in
			// terms of disconnect)
			Team team = CoKGame.getTeamOfPlayer(player.username);
			if (team != null) {
				team.playerReturned(player.username);
				ChunkCoordinates spawnCoordinates = team.getSpawnCoordinates();
				if (spawnCoordinates != null) {
					player.setSpawnChunk(spawnCoordinates, true);
				}
			}
		}

	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER) {
			Team team = CoKGame.getTeamOfPlayer(player.username);
			if (team != null) {
				String captain = team.getCaptain();
				Profession profession = CoKGame.playerProfessions.get(player.username);
				team.playerGone(player.username);
				if (CoKGame.gameRunning && captain.equalsIgnoreCase(player.username)) {
					ChatSendHelper.broadCastError(captain + " ,the king of team " + team.getName() + " fled!");
					ChatSendHelper.broadCastError("Long life king " + team.getCaptain() + "!");
				}
				if (profession != null) {
					for (Item item : profession.givenItems) {
						player.inventory.clearInventory(item.itemID, -1);
					}
				}
			} else {
				CoKGame.removeSpectator(player);
				PacketHelper.sendPacketToAllPlayers(new PacketSpectatorRemove(player.username));
			}
		}

	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER) {
			if (!CoKGame.gameRunning) {
				return;
			}

			Team team = CoKGame.getTeamOfPlayer(player.username);
			if (team != null) {
				team.playerReturned(player.username);
			}
			if (team == null) {
				CoKGame.setPlayerSpectator((EntityPlayerMP) player);
				PacketHelper.sendPacketToAllPlayers(new PacketSpectatorAdd(player.username));
			}
		}
	}

}
