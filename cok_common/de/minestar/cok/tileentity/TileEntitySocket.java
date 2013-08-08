package de.minestar.cok.tileentity;

import net.minecraft.entity.passive.EntityPig;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * 
 * @author Geras
 *	Tile Entity for the "socket" block
 */
public class TileEntitySocket extends TileEntity {
	
	/**
     * Allows the entity to update its state.
     */
    public void updateEntity() {
    	World world = this.getWorldObj();
    	
//    	EntityPig pig = new EntityPig(world);
//    	pig.setPosition(xCoord, yCoord, zCoord);
//    	world.spawnEntityInWorld(pig);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound){
    	super.readFromNBT(par1NBTTagCompound);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound){
    	super.writeToNBT(par1NBTTagCompound);
    }

}
