package de.minestar.cok.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockTowerBrick extends CoKBlock {

	public BlockTowerBrick() {
		super(Material.rock);
		setBlockName("towerbrick");
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
    public int quantityDropped(Random par1Random)
    {
        return 0; //I shall not drop!
    }

}
