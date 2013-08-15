package de.minestar.cok.block;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.references.Reference;
import de.minestar.cok.tileentity.TileEntitySocket;

public class BlockSocket extends BlockContainer {

	private Icon[] iconArray;
	
	public BlockSocket(int id) {
		super(id, Material.wood);
		setUnlocalizedName("socket");
		this.setCreativeTab(ClashOfKingdoms.cokTab);
		this.setBlockUnbreakable();
	}
    
	 /**
     * From the specified side and block metadata retrieves the blocks texture.
     */
    public Icon getIcon(int side, int metadata)
    {
    	return iconArray[metadata];
    }
	
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register)
    {
    	this.iconArray = new Icon[16];

        for (int i = 0; i < this.iconArray.length; ++i)
        {
            this.iconArray[i] = register.registerIcon(Reference.MOD_ID + ":" + this.getUnlocalizedName().substring(5) + "_" + i);
        }
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
    
	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion){
		//I shall not explode!
	}

}
