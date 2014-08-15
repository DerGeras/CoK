package de.minestar.cok.weapon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import de.minestar.cok.creativetab.CreativeTabCoK;
import de.minestar.cok.item.CoKItem;
import de.minestar.cok.reference.Reference;

public abstract class CoKWeapon extends ItemSword{
	
	public CoKWeapon(ToolMaterial material){
		super(material);
        this.setCreativeTab(CreativeTabCoK.COK_ITEM_TAB);
        this.maxStackSize = 1;
	}
	
	@Override
	public String getUnlocalizedName(){
		return String.format("item.%s:%s", Reference.MOD_ID.toLowerCase(), getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack){
		return getUnlocalizedName();
	}
	
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName){
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register){
		itemIcon = register.registerIcon(
				this.getUnlocalizedName()
					.substring(getUnlocalizedName().indexOf(".") + 1));
	}

}
