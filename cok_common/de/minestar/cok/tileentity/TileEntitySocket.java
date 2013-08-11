package de.minestar.cok.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import de.minestar.cok.event.EventBlockBreak;
import de.minestar.cok.event.EventBlockPlace;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Settings;
import de.minestar.cok.game.Team;

/**
 * 
 * @author Geras
 *	Tile Entity for the "socket" block
 */
public class TileEntitySocket extends TileEntity {
	
	public TileEntitySocket(){
		super();
	}
    
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound){
    	super.readFromNBT(par1NBTTagCompound);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound){
    	super.writeToNBT(par1NBTTagCompound);
    }
    
    @Override
    public void validate(){
    	super.validate();
    	if(!this.worldObj.isRemote && this.zCoord != 0)
	    {
	    	CoKGame.registerSocket(this);
	    }
    }
    
    @Override
    public void invalidate(){
    	super.invalidate();
    	if(!this.worldObj.isRemote && this.zCoord != 0){
    		CoKGame.removeSocket(this);
    	}
    }
    
    
    public void checkEvent(EventBlockBreak event){
    	if(!event.isCanceled()){
    		Team team = CoKGame.getTeamOfPlayer(event.player.username);
    		if(team != null){
    			boolean sameTeam = team.getColorAsInt() == this.blockMetadata;
    			if(sameTeam){
    				if(event.blockX == this.xCoord && event.blockZ == this.zCoord && event.blockY >= this.yCoord){
    					event.setCanceled(true);
    				}
    			} else{
    				event.setCanceled(Math.abs(event.blockX - this.xCoord) <= Settings.protectedRadius && Math.abs(event.blockZ - this.zCoord) <= Settings.protectedRadius);
    			}
    		}
    	}
    }
    
    
    public void checkEvent(EventBlockPlace event){
    	if(!event.isCanceled()){
    		Team team = CoKGame.getTeamOfPlayer(event.player.username);
    		if(team != null){
    			boolean sameTeam = team.getColorAsInt() == this.blockMetadata;
    			if(sameTeam){
    				if(event.blockX == this.xCoord && event.blockZ == this.zCoord && event.blockY >= this.yCoord){
    					if(event.stack.getItem().itemID != Settings.defaultbuildingBlockID || event.blockY > this.yCoord + Settings.buildingHeight){
    						event.setCanceled(true);
    					}
    				}
    			} else{
    				event.setCanceled(Math.abs(event.blockX - this.xCoord) <= Settings.protectedRadius && Math.abs(event.blockZ - this.zCoord) <= Settings.protectedRadius);
    			}
    		}
    	}
    }

}
