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
package de.minestar.cok.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AABBPool;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import de.minestar.cok.ClashOfKingdoms;

public class ItemWarhammer extends CoKWeapon {
	
	private static final int maxItemInUseDuration = 60;
	private static final double knockBackRadius = 3.0d;
	private static AABBPool pool = new AABBPool(300, 2000);

	public ItemWarhammer(int id, EnumToolMaterial material) {
		super(id);
		this.maxStackSize = 1;
		this.toolMaterial = material;
        this.setMaxDamage(material.getMaxUses() * 3);
        setUnlocalizedName("warhammer");
        this.setCreativeTab(ClashOfKingdoms.cokItemTab);
        this.weaponDamage = 5 + material.getDamageVsEntity();
        
        this.setFull3D(); //NEEDED for 3D Models
	}
	
	/**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
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
        	player.worldObj.playSoundAtEntity(player, "random.explode", 0.3f, 1.0f);
	    	double x1 = player.posX - knockBackRadius;
	    	double y1 = player.posY - knockBackRadius;
	    	double z1 = player.posZ - knockBackRadius;
	    	double x2 = player.posX + knockBackRadius;
	    	double y2 = player.posY + knockBackRadius;
	    	double z2 = player.posZ + knockBackRadius;
	    	AxisAlignedBB boundingBox = pool.getAABB(x1, y1, z1, x2, y2, z2);
	    	
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
