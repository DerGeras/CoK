package de.minestar.cok.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.minestar.cok.references.Reference;

public class CoKItem extends Item{
	
	
	public CoKItem(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub
	}

	@SideOnly(Side.CLIENT)
    public void registerIcon(IconRegister register){
    	this.itemIcon = register.registerIcon(Reference.MOD_ID + ":" + this.getUnlocalizedName().substring(5));
    }
}
