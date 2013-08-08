package de.minestar.cok.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.references.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

/**
 * The Block Towers are made of. Not craftable,
 * but build towers with the specified blocks are
 * being replaced with this.
 * @author Geras
 *
 */
public class BlockTowerBrick extends Block{

	public BlockTowerBrick(int id) {
		super(id, Material.rock);
		setUnlocalizedName("towerbrick");
		this.setCreativeTab(ClashOfKingdoms.cokTab);
	}
	
	/**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register)
    {
        this.blockIcon = register.registerIcon(Reference.MOD_ID + ":" + this.getUnlocalizedName().substring(5));
    }
	
}
