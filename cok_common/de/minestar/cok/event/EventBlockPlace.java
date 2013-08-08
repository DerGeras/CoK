package de.minestar.cok.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.Event;

public class EventBlockPlace extends Event{

	public final ItemStack		stack;
	public final World			world;
	public final int			blockX;
	public final int			blockY;
	public final int			blockZ;
	public final int 			side;
	public final float 			hitx;
	public final float			hity;
	public final float			hitz;
	public final EntityPlayer	player;
	
	public EventBlockPlace(ItemStack stack, World world, int x, int y, int z,int side, float hitx, float hity, float hitz, EntityPlayer player)
	{
		super();
		this.stack = stack;
		this.world = world;
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.side = side;
		this.hitx = hitx;
		this.hity = hity;
		this.hitz = hitz;
		this.player = player;
	}
	
}
