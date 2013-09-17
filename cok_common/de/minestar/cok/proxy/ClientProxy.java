package de.minestar.cok.proxy;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.gui.CoKGui;
import de.minestar.cok.keyhandler.CoKKeyHandler;
import de.minestar.cok.references.Reference;
import de.minestar.cok.renderer.ItemRendererCrossbow;
import de.minestar.cok.renderer.ItemRendererWarhammer;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderThings() {
		super.registerRenderThings();

		MinecraftForgeClient.registerItemRenderer(ClashOfKingdoms.crossBowItem.itemID, new ItemRendererCrossbow());
		MinecraftForgeClient.registerItemRenderer(ClashOfKingdoms.warhammerItem.itemID, new ItemRendererWarhammer());
	}

	@Override
	public void registerKeyHandlers() {
		KeyBinding[] key = {new KeyBinding(Reference.CoKMenuKey, Keyboard.KEY_G)};
		boolean[] repeat = {false};
		KeyBindingRegistry.registerKeyBinding(new CoKKeyHandler(key, repeat));
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		switch (ID) {
			case Reference.COK_GUI_ID :
				return new CoKGui();
			default :
				return null;
		}
	}

}
