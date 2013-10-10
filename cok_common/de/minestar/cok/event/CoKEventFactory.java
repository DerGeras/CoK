/*******************************************************************************
 * Clash of Kingdoms Minecraft Mod
 *     Copyright (C) 2013 Geras
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package de.minestar.cok.event;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;

public class CoKEventFactory {

	public static boolean onBlockBreak(World world, int x, int y, int z, Block block, int metadata, EntityPlayer player)
	{
		EventBlockBreak ev = new EventBlockBreak(world, x, y, z, player);
		MinecraftForge.EVENT_BUS.post(ev);
		return !ev.isCanceled();
	}

	public static boolean onBlockPlace(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz)
	{

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
