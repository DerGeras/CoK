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
package de.minestar.cok.entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;


public class EntityBolt extends EntityArrow {
	
	private static final double baseDamage = 2.5d;
	private static final int baseKnockBack = 1;

	public EntityBolt(World par1World) {
		super(par1World);
		setKnockbackStrength(0);
		setDamage(0);
	}
	
	public EntityBolt(World world, EntityLiving entity, float par3) {
		super(world, entity, par3);
		setKnockbackStrength(0);
		setDamage(0);
	}
	
	/**
	 * sets the damage. Might be slightly over the top :/
	 */
	@Override
	public void setDamage(double damage){
		super.setDamage(baseDamage + damage);
	}
	
	/**
	 * sets the knockback
	 */
	@Override
	public void setKnockbackStrength(int strength){
		super.setKnockbackStrength(baseKnockBack + strength);
	}
	
	/**
     * Called by a player entity when they collide with an entity
     */
	@Override
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer){
        //Currently cannot be picked up anymore
    }

}
