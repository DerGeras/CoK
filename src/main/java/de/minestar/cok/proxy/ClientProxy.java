package de.minestar.cok.proxy;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;
import de.minestar.cok.CoK;
import de.minestar.cok.client.keybinding.KeyBindings;
import de.minestar.cok.client.renderer.ItemRendererCrossbow;
import de.minestar.cok.client.renderer.ItemRendererWarhammer;
import de.minestar.cok.init.ModItems;
import de.minestar.cok.util.LogHelper;

public class ClientProxy extends CommonProxy {

	@Override
	public void init() {
		super.init();
		registerItemrenderers();
		//register KeyBindings
		KeyBindings.init();
	}
	
	private void registerItemrenderers(){
		MinecraftForgeClient.registerItemRenderer(ModItems.warhammer, new ItemRendererWarhammer());
		MinecraftForgeClient.registerItemRenderer(ModItems.crossbow, new ItemRendererCrossbow());
		LogHelper.info("Registered item renderers");
	}
	
}
