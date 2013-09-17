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

import de.minestar.cok.model.ModelCrossbow;

public class ItemRendererCrossbow implements IItemRenderer {

	public ModelCrossbow crossbowModel = new ModelCrossbow();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type) {
			case EQUIPPED :
				return true;
			default :
				return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		switch (type) {
			case EQUIPPED : {
				GL11.glPushMatrix();

				Minecraft.getMinecraft().renderEngine.bindTexture("/mods/ClashOfKingdoms/models/ModelCrossBow.png");

				GL11.glRotatef(90f, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(312f, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(270f, 0.0f, 0.0f, 1.0f);

				if (data[1] != null && data[1] instanceof EntityPlayer) {
					// check for non-first person view
					if (!((EntityPlayer) data[1] == Minecraft.getMinecraft().renderViewEntity && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && !((Minecraft.getMinecraft().currentScreen instanceof GuiInventory || Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) && RenderManager.instance.playerViewY == 180.0F))) {
						GL11.glTranslatef(0.05f, -0.05f, -0.8f);
					} else {
						GL11.glTranslatef(0.05f, -0.05f, -0.8f); // <--- First
						                                         // Person view
					}
				} else {
					GL11.glTranslatef(0.05f, -0.05f, -0.8f);
				}

				crossbowModel.render((Entity) data[1], 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f, stack);

				GL11.glPopMatrix();
			}
			default :
				break; // Don't ask me. Tutorial said so
		}

	}

}
