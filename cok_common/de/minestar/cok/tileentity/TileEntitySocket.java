package de.minestar.cok.tileentity;

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
    	if(!world.isRemote){
    		//remove unwanted stuff
    	}
    }

}
