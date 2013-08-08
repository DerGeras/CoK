package de.minestar.cok.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.Event;

public class EventBlockPlace extends Event{

	public final World			world;
	public final int			blockX;
	public final int			blockY;
	public final int			blockZ;
	public final EntityPlayer	player;
	
	public EventBlockPlace(World world, int x, int y, int z, EntityPlayer player)
	{
		super();
		this.world = world;
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.player = player;
	}
	
}
