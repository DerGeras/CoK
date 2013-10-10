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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

@Cancelable
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
