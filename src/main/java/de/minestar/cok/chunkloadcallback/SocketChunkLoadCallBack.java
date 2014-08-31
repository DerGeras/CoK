package de.minestar.cok.chunkloadcallback;

import java.util.List;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager.OrderedLoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class SocketChunkLoadCallBack implements OrderedLoadingCallback {

	@Override
	public void ticketsLoaded(List<Ticket> tickets, World world) {
		//do nothing?

	}

	@Override
	public List<Ticket> ticketsLoaded(List<Ticket> tickets, World world,
			int maxTicketCount) {
		return tickets;
	}

}
