package de.minestar.cok.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import de.minestar.cok.client.renderer.ItemRendererCrossbow;
import de.minestar.cok.client.renderer.ItemRendererWarhammer;
import de.minestar.cok.init.ModItems;
import de.minestar.cok.util.LogHelper;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerItemRenderers() {
		MinecraftForgeClient.registerItemRenderer(ModItems.warhammer, new ItemRendererWarhammer());
		MinecraftForgeClient.registerItemRenderer(ModItems.crossbow, new ItemRendererCrossbow());
		LogHelper.info("Registered item renderers");
	}
	
}
