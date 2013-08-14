package de.minestar.cok.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import de.minestar.cok.ClashOfKingdoms;
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
    	if(!this.worldObj.isRemote)
	    {
	    	CoKGame.registerSocket(this);
	    }
    }
    
    @Override
    public void invalidate(){
    	super.invalidate();
    	if(!this.worldObj.isRemote){
    		CoKGame.removeSocket(this);
    	}
    }
    
    /**
     * here: change default block to tower bricks... hopefully only temporary
     */
    @Override
    public void updateEntity(){
    	for(int i = 0; i < Settings.buildingHeight; i++){
    		int blockID = this.worldObj.getBlockId(xCoord, yCoord + i, zCoord);
    		if(blockID == Settings.defaultbuildingBlockID) {
    			//Change to tower bricks
    			this.worldObj.setBlock(xCoord, yCoord + i, zCoord, ClashOfKingdoms.towerBrickID);
    		}
    	}
    }
    
    /**
     * count the number of valid blocks above the socket
     * @return
     */
    public int countBlocks(){
    	int res = 0;
    	for(int i = 0; i < Settings.buildingHeight; i++){
    		int blockID = this.worldObj.getBlockId(xCoord, yCoord + i, zCoord);
		  	if(blockID == ClashOfKingdoms.towerBrickID){
				res++;
			}    		
    	}
    	return res;
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
