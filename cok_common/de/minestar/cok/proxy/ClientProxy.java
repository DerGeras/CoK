package de.minestar.cok.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.renderer.ItemRendererCrossbow;



public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderThings(){
		super.registerRenderThings();
		
		MinecraftForgeClient.registerItemRenderer(ClashOfKingdoms.crossBowItem.itemID, new ItemRendererCrossbow());
	}
	
}
