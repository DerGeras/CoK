package de.minestar.cok.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

@Cancelable
public class BlockPlaceEvent extends Event {

	public final World world;
	public final ItemStack stack;
    public final int x;
    public final int y;
    public final int z;
    public final EntityPlayer player;
    public final int side;
    public final float hitx;
    public final float hity;
    public final float hitz;

    /**
     * Called from {@link net.minecraft.item.ItemStack#tryPlaceItemIntoWorld(EntityPlayer, World, int, int, int, int, float, float, float)}
     * 
     * @param itemStack
     * @param player
     * @param world
     * @param x
     * @param y
     * @param z
     * @param side
     * @param hitx
     * @param hity
     * @param hitz
     */
    public BlockPlaceEvent(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz)
    {
        super();
        this.stack = itemStack;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.player = player;
        this.side = side;
        this.hitx = hitx;
        this.hity = hity;
        this.hitz = hitz;
    }
	
}
