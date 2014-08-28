package de.minestar.cok.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.CoK;
import de.minestar.cok.event.BlockPlaceEvent;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.CoKPlayerRegistry;
import de.minestar.cok.game.GameSettings;
import de.minestar.cok.game.SocketRegistry;
import de.minestar.cok.game.Team;
import de.minestar.cok.game.TeamRegistry;
import de.minestar.cok.init.ModBlocks;
import de.minestar.cok.util.ChatSendHelper;

public class TileEntitySocket extends TileEntity {
	
	private Ticket chunkTicket;
	
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
	    	SocketRegistry.registerSocket(this);
	    }
    	//chunk loading
    	chunkTicket = ForgeChunkManager.requestTicket(CoK.instance, this.worldObj, ForgeChunkManager.Type.NORMAL);
    	ForgeChunkManager.forceChunk(chunkTicket, new ChunkCoordIntPair(xCoord >> 4, zCoord >> 4));
    }
    
    @Override
    public void invalidate(){
    	ForgeChunkManager.releaseTicket(chunkTicket);
    	super.invalidate();
    	if(!this.worldObj.isRemote){
    		SocketRegistry.removeSocket(this);
    	}
    }
    
    /**
     * here: change default block to tower bricks... hopefully only temporary
     */
    @Override
    public void updateEntity(){
    	for(int i = 1; i <= GameSettings.buildingHeight; i++){
    		Block block = this.worldObj.getBlock(xCoord, yCoord + i, zCoord);
    		if(block == GameSettings.defaultBuildingBlock) {
    			//Change to tower bricks
    			this.worldObj.setBlock(xCoord, yCoord + i, zCoord, ModBlocks.towerbrick);
    			//queue a score check
    			//TODO ServerTickHandler.isScoreCheckQueued = true;
    			//send notification to team
    			if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
    				ChatSendHelper.broadCastMessageToTeam(TeamRegistry.getTeam(getBlockMetadata()), "Clonk!");
    			}
    		} else{
    			if(block != ModBlocks.towerbrick){
    				this.worldObj.setBlockToAir(xCoord, yCoord + i, zCoord);
    			}
    		}
    	}
    }
    
    /**
     * count the number of valid blocks above the socket
     * @return
     */
    public int countBlocks(){
    	int res = 0;
    	for(int i = 1; i <= GameSettings.buildingHeight; i++){
    		Block block = this.worldObj.getBlock(xCoord, yCoord + i, zCoord);
		  	if(block == ModBlocks.towerbrick){
				res++;
			}    		
    	}
    	return res;
    }
    
    /**
     * Checks for possible rule violations in the vicinity
     * @param event
     */
    public void checkEvent(BlockEvent.BreakEvent event){
    	if(!event.isCanceled()){
    		Team team = CoKPlayerRegistry.getPlayerForUUID(event.getPlayer().getUniqueID()).getTeam();
    		if(team != null){
    			boolean sameTeam = team.getColorAsInt() == this.blockMetadata;
    			if(!sameTeam){
    				if(event.x == this.xCoord && event.z == this.zCoord && event.y >= this.yCoord){
    					event.setCanceled(true);
    				}
    			} else{
    				event.setCanceled(Math.abs(event.x - this.xCoord) <= GameSettings.protectedRadius && Math.abs(event.z - this.zCoord) <= GameSettings.protectedRadius);
    			}
    		}
    	}
    }
    
    
    /**
     * Checks for possible rule violations in the vicinity
     * @param event
     */
    public void checkEvent(BlockPlaceEvent event){
    	if(!event.isCanceled()){
    		Team team = CoKPlayerRegistry.getPlayerForUUID(event.player.getUniqueID()).getTeam();
    		if(team != null){
    			boolean sameTeam = team.getColorAsInt() == this.blockMetadata;
    			if(!sameTeam){
    				if(event.x == this.xCoord && event.z == this.zCoord && event.y >= this.yCoord){
    					if(Block.getBlockFromItem(event.stack.getItem()) != GameSettings.defaultBuildingBlock || event.y > this.yCoord + GameSettings.buildingHeight + 1){
    						event.setCanceled(true);
    					}
    				}
    			} else{
    				event.setCanceled(Math.abs(event.x - this.xCoord) <= GameSettings.protectedRadius && Math.abs(event.z - this.zCoord) <= GameSettings.protectedRadius);
    			}
    		}
    	}
    }
    
    /**
     * Add a block to the tower
     * @return whether a block could be placed ( < maxBuildingheight)
     */
    public boolean addBlock(){
    	boolean added = false;
    	for(int i = 1; i <= GameSettings.buildingHeight && !added; i++){
    		if(this.worldObj.isAirBlock(xCoord, yCoord + i, zCoord)){
				this.worldObj.setBlock(xCoord, yCoord + i, zCoord, ModBlocks.towerbrick);
    			//TODO ServerTickHandler.isScoreCheckQueued = true;
    			added = true;
    			break;
			}
    	}
    	return added;
    }
}
