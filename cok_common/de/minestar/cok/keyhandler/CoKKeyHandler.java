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
package de.minestar.cok.keyhandler;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.references.Reference;

public class CoKKeyHandler extends KeyHandler{
	
	private EnumSet<TickType> tickTypes = EnumSet.of(TickType.CLIENT);

	public CoKKeyHandler(KeyBinding[] keyBindings, boolean[] repeatings) {
		super(keyBindings, repeatings);
	}

	@Override
    public String getLabel()
    {
            return "CoKKey";
    }

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,
			boolean tickEnd, boolean isRepeat) {
		//Check whether we are in a gui
		if (FMLClientHandler.instance().getClient().inGameHasFocus) {
			//Menu key
			if(kb.keyDescription.equals(Reference.CoKMenuKey)){
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				System.out.println(MinecraftServer.getServer() == null);
				if(player != null){
					player.openGui(ClashOfKingdoms.instance, Reference.COK_GUI_ID, player.worldObj,
							(int) player.posX, (int) player.posY, (int) player.posZ);
				}
			}
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		//nothing
	}

	@Override
	public EnumSet<TickType> ticks() {
		return tickTypes;
	}
	
}
