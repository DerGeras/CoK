package de.minestar.cok.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemWarhammer extends CoKWeapon {
	
	private static final int maxItemInUseDuration = 60;
	private static final double knockBackRadius = 5.0d;
	

	public ItemWarhammer() {
		super(ToolMaterial.IRON);
		setMaxDamage(ToolMaterial.IRON.getMaxUses() * 3);
		setUnlocalizedName("warhammer");
		setFull3D(); //needed for 3D Items
	}
	
	/**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return  EnumAction.bow;
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
    	par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
    	return par1ItemStack;
    }
    
    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return maxItemInUseDuration;
    }
    
    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int count){
    	player.clearItemInUse();
    	if(count < getMaxItemUseDuration(stack) - 20){
        	//player.worldObj.playSoundAtEntity(player, "random.explode", 0.3f, 1.0f);
	    	double x1 = player.posX - knockBackRadius;
	    	double y1 = player.posY - knockBackRadius;
	    	double z1 = player.posZ - knockBackRadius;
	    	double x2 = player.posX + knockBackRadius;
	    	double y2 = player.posY + knockBackRadius;
	    	double z2 = player.posZ + knockBackRadius;
	    	AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(x1, y1, z1, x2, y2, z2);
	    	
	    	for(Object object : world.getEntitiesWithinAABBExcludingEntity(player, boundingBox)){
	    		if(object instanceof Entity){
	    			Entity entity = (Entity) object;
	    			//find distance
	    			double distX = Math.abs(entity.posX - player.posX);
	    			double distY = Math.abs(entity.posY - player.posY);
	    			double distZ = Math.abs(entity.posZ - player.posZ);
	    			//normalize distance
	    			float dist = MathHelper.sqrt_double(distX + distY + distZ);
	    			distX /= dist;
	    			distY /= dist;
	    			distZ /= dist;
	    			//knockback
	    			double knockBackStrength = (double)(getMaxItemUseDuration(stack)) * 0.01d;
	    			entity.addVelocity(distX * knockBackStrength, 1.0d, distZ * knockBackStrength);
	    			entity.isAirBorne = true;
	    			//attack
	    			player.attackTargetEntityWithCurrentItem(entity);
	    		}
	    	}
    	}
    }

}
