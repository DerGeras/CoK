package de.minestar.cok.client.keybinding;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;

public class KeyBindings {
	
	public static KeyBinding keyGUIInfo;

	public static void init(){
		keyGUIInfo = new KeyBinding("key.openguiinfo", Keyboard.KEY_G, "key.categories.mymod");
		ClientRegistry.registerKeyBinding(keyGUIInfo);
	}
	
}
