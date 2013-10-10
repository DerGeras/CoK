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
package de.minestar.cok.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import de.minestar.cok.model.ModelWarhammer;

public class ItemRendererWarhammer implements IItemRenderer {

	public ModelWarhammer warhammerModel = new ModelWarhammer();
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type){
		case EQUIPPED : return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type){
		case EQUIPPED : {
				GL11.glPushMatrix();
				
				Minecraft.getMinecraft().renderEngine.bindTexture("/mods/ClashOfKingdoms/models/ModelWarhammer.png");
				
				GL11.glRotatef(0f, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(-10f, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(220f, 0.0f, 0.0f, 1.0f);
				
				if(data[1] != null && data[1] instanceof EntityPlayer){
					//check for non-first person view
					if(!((EntityPlayer)data[1] == Minecraft.getMinecraft().renderViewEntity && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && !((Minecraft.getMinecraft().currentScreen instanceof GuiInventory || Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) && RenderManager.instance.playerViewY == 180.0F))){
						GL11.glTranslatef(-0.7f, -1.3f, -0.1f);
					} else{
						GL11.glTranslatef(-0.7f, -1.0f, -0.1f); // <--- First Person view
					}
				} else{
					GL11.glTranslatef(0.0f, 0.0f, 0.0f);
				}
				
				warhammerModel.render((Entity) data[1], 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
				
				GL11.glPopMatrix();
			}
		default: break; //Don't ask me. Tutorial said so
		}
	}

}
