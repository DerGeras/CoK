package de.minestar.cok.chunkloadcallback;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager.OrderedLoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import de.minestar.cok.init.ModBlocks;
import de.minestar.cok.tileentity.TileEntitySocket;
import de.minestar.cok.util.LogHelper;

public class SocketChunkLoadCallBack implements OrderedLoadingCallback {

	@Override
	public void ticketsLoaded(List<Ticket> tickets, World world) {
		for (Ticket ticket : tickets) {
			int x = ticket.getModData().getInteger("socketX");
			int y = ticket.getModData().getInteger("socketY");
			int z = ticket.getModData().getInteger("socketZ");
			TileEntitySocket socket = (TileEntitySocket) world.getTileEntity(x, y, z);
			socket.forceChunkLoading(ticket);

		}

	}

	@Override
	public List<Ticket> ticketsLoaded(List<Ticket> tickets, World world,
			int maxTicketCount) {
		List<Ticket> validTickets = new ArrayList<Ticket>();
		for (Ticket ticket : tickets) {
			int x = ticket.getModData().getInteger("socketX");
			int y = ticket.getModData().getInteger("socketY");
			int z = ticket.getModData().getInteger("socketZ");

			if (world.getBlock(x, y, z) == ModBlocks.socket) {
				validTickets.add(ticket);
			}
		}
		return validTickets;
	}

}
