package de.minestar.cok.block;

import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.tileentity.TileEntitySocket;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSocket extends BlockContainer {

	protected BlockSocket(int id) {
		super(id, Material.wood);
		this.setCreativeTab(ClashOfKingdoms.cokTab);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySocket();
	}

}
