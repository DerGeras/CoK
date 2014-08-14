package de.minestar.cok.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.minestar.cok.creativetab.CreativeTabCoK;
import de.minestar.cok.reference.Reference;

public abstract class CoKItem extends Item{
	
	public static final CoKItem itemDough = new ItemDough();
	public static final CoKItem itemFlour = new ItemFlour();
	public static final CoKItem itemBolt = new ItemBolt();
	
	
	
	public CoKItem(){
		super();
		setCreativeTab(CreativeTabCoK.COK_TAB);
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
