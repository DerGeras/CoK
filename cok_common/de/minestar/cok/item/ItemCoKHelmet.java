package de.minestar.cok.item;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.model.ModelArmorHelmet;

public class ItemCoKHelmet extends ItemArmor{
	
	ModelBiped modelArmorHelmet = new ModelArmorHelmet();

	public ItemCoKHelmet(int id, EnumArmorMaterial material) {
		super(id, material, 2, 0);
        setUnlocalizedName("helmetcok");
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

}
