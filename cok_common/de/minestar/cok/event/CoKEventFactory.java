package de.minestar.cok.event;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;

public class CoKEventFactory {

	public static boolean onBlockBreak(World world, int x, int y, int z, Block block, int metadata, EntityPlayer player) {
		EventBlockBreak ev = new EventBlockBreak(world, x, y, z, player);
		MinecraftForge.EVENT_BUS.post(ev);
		return !ev.isCanceled();
	}

	public static boolean onBlockPlace(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz) {

		// calculate offsets.
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		x += dir.offsetX;
		y += dir.offsetY;
		z += dir.offsetZ;

		EventBlockPlace ev = new EventBlockPlace(itemStack, world, x, y, z, side, hitx, hity, hitz, player);
		MinecraftForge.EVENT_BUS.post(ev);
		return !ev.isCanceled();
	}

}
