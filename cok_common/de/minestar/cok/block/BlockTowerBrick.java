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

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.references.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

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
		this.setCreativeTab(ClashOfKingdoms.cokBlockTab);
		this.setBlockUnbreakable();
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
