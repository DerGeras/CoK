package de.minestar.cok.event;

import de.minestar.cok.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class EventFactory {

	//try to place block in world
	public static boolean onBlockPlace(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz)
    {
        // calculate offsets.
        ForgeDirection dir = ForgeDirection.getOrientation(side);

        x += dir.offsetX;
        y += dir.offsetY;
        z += dir.offsetZ;

        BlockPlaceEvent event = new BlockPlaceEvent(itemStack, player, world, x, y, z, side, hitx, hity, hitz);
        return !MinecraftForge.EVENT_BUS.post(event);
    }
}
