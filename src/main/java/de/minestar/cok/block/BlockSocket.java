package de.minestar.cok.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.minestar.cok.CoK;
import de.minestar.cok.tileentity.TileEntitySocket;

public class BlockSocket extends CoKBlockContainer {
	
	private IIcon[] icons;
	
	public static final String[] subNames = {
    	"black", "darkBlue", "darkGreen", "darkAqua",
    	"darkRed", "purple", "gold", "gray", "darkGray",
    	"blue", "green", "aqua", "red", "lightPurple",
    	"yellow", "white"
    	};

	public BlockSocket() {
		super(Material.rock);
		setBlockName("socket");
		this.setBlockUnbreakable();		
	}
	
	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion){
		//I shall not explode!
	}
	
	 /**
     * Returns the quantity of items to drop on block destruction.
     */
	@Override
    public int quantityDropped(Random par1Random){
        return 0; //I shall not drop!
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems){
		for(int meta = 0; meta < 16; meta++){
			subItems.add(new ItemStack(item, 1, meta));
		}
	}
	
	@Override
    public int damageDropped(int metaData){
        return metaData;
    }
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack){
		world.setBlockMetadataWithNotify(x, y, z, itemStack.getItemDamage(), 2);
    	//chunk loading
		TileEntitySocket socket = (TileEntitySocket) world.getTileEntity(x, y, z);
    	Ticket ticket = ForgeChunkManager.requestTicket(CoK.instance, world, ForgeChunkManager.Type.NORMAL);
		ticket.getModData().setInteger("socketX", x);
		ticket.getModData().setInteger("socketY", y);
		ticket.getModData().setInteger("socketZ", z);
		socket.forceChunkLoading(ticket);
	}
	
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister){
        icons = new IIcon[16];
        for (int i = 0; i < 16; i++){
        	icons[i] = iconRegister.registerIcon(String.format("%s.%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName()), subNames[i]));
        }
    }
	 
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metaData){
		 return icons[metaData];
    }

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntitySocket();
	}

}
