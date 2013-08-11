package de.minestar.cok.block;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.references.Reference;
import de.minestar.cok.tileentity.TileEntitySocket;

public class BlockSocket extends BlockContainer {

	public BlockSocket(int id) {
		super(id, Material.wood);
		setUnlocalizedName("socket");
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
    
    @Override
    public int damageDropped (int metadata) {
    	return metadata;
    }

    //Suppresswarnings because of decompiling and the loss of generic information
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(int unknown, CreativeTabs tab, List subItems) {
    	for (int ix = 0; ix < 16; ix++) {
    		subItems.add(new ItemStack(this, 1, ix));
    	}
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySocket();
	}
    

}
