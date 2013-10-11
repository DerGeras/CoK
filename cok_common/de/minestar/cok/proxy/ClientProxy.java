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
package de.minestar.cok.proxy;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.gui.CoKGUIOverlay;
import de.minestar.cok.gui.CoKGui;
import de.minestar.cok.keyhandler.CoKKeyHandler;
import de.minestar.cok.references.Reference;
import de.minestar.cok.renderer.ItemRendererCrossbow;
import de.minestar.cok.renderer.ItemRendererWarhammer;



public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderThings(){
		super.registerRenderThings();
		
		MinecraftForgeClient.registerItemRenderer(ClashOfKingdoms.crossBowItem.itemID, new ItemRendererCrossbow());
		MinecraftForgeClient.registerItemRenderer(ClashOfKingdoms.warhammerItem.itemID, new ItemRendererWarhammer());
		
		MinecraftForge.EVENT_BUS.register(new CoKGUIOverlay());
	}
	
	@Override
	public void registerKeyHandlers(){
		KeyBinding[] key = {new KeyBinding(Reference.CoKMenuKey, Keyboard.KEY_G)};
        boolean[] repeat = {false};
        KeyBindingRegistry.registerKeyBinding(new CoKKeyHandler(key, repeat));
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {

		switch(ID){
		case Reference.COK_GUI_ID : return new CoKGui();
		default: return null;
		}
	}
	
}
