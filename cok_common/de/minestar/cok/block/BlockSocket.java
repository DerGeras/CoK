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
package de.minestar.cok.block;

import java.util.List;
import java.util.Random;

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
		this.setCreativeTab(ClashOfKingdoms.cokBlockTab);
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
	
	/**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

}
