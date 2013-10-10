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

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import de.minestar.cok.item.CoKItem;

public class CoKWeapon extends CoKItem {
	

	protected int weaponDamage;
	protected EnumToolMaterial toolMaterial;

	public CoKWeapon(int id) {
		super(id);
	}
	
	public int func_82803_g()
    {
        return this.toolMaterial.getDamageVsEntity();
    }
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
    {
        stack.damageItem(1, par3EntityLiving);
        return true;
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, int par3, int par4, int par5, int par6, EntityLiving entity)
    {
        if ((double)Block.blocksList[par3].getBlockHardness(world, par4, par5, par6) != 0.0D)
        {
            stack.damageItem(2, entity);
        }

        return true;
    }
	
	/**
     * Returns the damage against a given entity.
     */
	@Override
    public int getDamageVsEntity(Entity entity)
    {
        return this.weaponDamage;
    }
    
    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    @Override
    public int getItemEnchantability()
    {
        return this.toolMaterial.getEnchantability();
    }

}
