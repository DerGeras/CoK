package de.minestar.cok.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import de.minestar.cok.client.model.ModelWarhammer;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.PathHelper;

public class ItemRendererWarhammer implements IItemRenderer {

public ModelWarhammer warhammerModel = new ModelWarhammer();

	public static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID.toLowerCase(), PathHelper.getPathForModel("ModelWarhammer.png"));
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type){
		case EQUIPPED : return true;
		case EQUIPPED_FIRST_PERSON: return true;
		case ENTITY : return true;
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
				
				Minecraft.getMinecraft().renderEngine.bindTexture(texture);
				
				GL11.glRotatef(0f, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(-10f, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(220f, 0.0f, 0.0f, 1.0f);
				
				if(data[1] != null && data[1] instanceof EntityPlayer){
					GL11.glTranslatef(-0.7f, -1.3f, -0.1f);
				} else{
					GL11.glTranslatef(0.0f, 0.0f, 0.0f);
				}
				
				warhammerModel.render((Entity) data[1], 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
				
				GL11.glPopMatrix();
				
				break;
			}
		case EQUIPPED_FIRST_PERSON: {
			GL11.glPushMatrix();
			
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			
			GL11.glRotatef(0f, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(-10f, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(220f, 0.0f, 0.0f, 1.0f);
			
			
			GL11.glTranslatef(-0.7f, -1.0f, -0.1f);
			
			warhammerModel.render(Minecraft.getMinecraft().thePlayer, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
			
			GL11.glPopMatrix();
			
			break;
		}
		case ENTITY: {
GL11.glPushMatrix();
			
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			
			GL11.glRotatef(107.0f, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(0.0f, 0.0f, 0.0f, 1.00f);
			
			
			GL11.glTranslatef(0.5f, 0.0f, 0.0f);
			
			warhammerModel.render(Minecraft.getMinecraft().thePlayer, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
			
			GL11.glPopMatrix();
			
			break;
		}
		default: break;
		}
	}


}
