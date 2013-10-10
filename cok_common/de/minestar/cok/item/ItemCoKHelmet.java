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
package de.minestar.cok.item;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.model.ModelArmorHelmet;
import de.minestar.cok.references.Reference;

public class ItemCoKHelmet extends ItemArmor{
	
	ModelBiped modelArmorHelmet = new ModelArmorHelmet();

	public ItemCoKHelmet(int id, EnumArmorMaterial material) {
		super(id, material, 1, 0);
        setUnlocalizedName("cokhelmet");
        this.setCreativeTab(ClashOfKingdoms.cokItemTab);
	}
	
	/**
	* Override this method to have an item handle its own armor rendering.
	* 
	* @param  entityLiving  The entity wearing the armor 
	* @param  itemStack  The itemStack to render the model of 
	* @param  armorSlot  0=head, 1=torso, 2=legs, 3=feet
	* 
	* @return  A ModelBiped to render instead of the default
	*/
	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(EntityLiving entityLiving, ItemStack itemStack, int armorSlot){
		modelArmorHelmet.setLivingAnimations(entityLiving, 0, 0, 0);
		return modelArmorHelmet;
	}
	
	/**
     * Called by RenderBiped and RenderPlayer to determine the armor texture that 
     * should be use for the currently equiped item.
     * This will only be called on instances of ItemArmor. 
     * 
     * Returning null from this function will use the default value.
     * 
     * @param stack ItemStack for the equpt armor
     * @param entity The entity wearing the armor
     * @param slot The slot the armor is in
     * @param layer The render layer, either 1 or 2, 2 is only used for CLOTH armor by default
     * @return Path of texture to bind, or null to use default
     */
	@Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer){
		return "/mods/ClashOfKingdoms/models/ModelArmorHelmet.png";
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public void registerIcons(IconRegister register){
    	this.itemIcon = register.registerIcon(Reference.MOD_ID + ":" + this.getUnlocalizedName().substring(5));
    }

}
