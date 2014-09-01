package de.minestar.cok.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import de.minestar.cok.client.model.ModelCrossbow;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.PathHelper;

public class ItemRendererCrossbow implements IItemRenderer  {
	
	public static final ResourceLocation textureResource = new ResourceLocation(Reference.MOD_ID.toLowerCase(), PathHelper.getPathForModel("crossbow.png"));
	public static final ResourceLocation crossbowModelResource = new ResourceLocation(Reference.MOD_ID.toLowerCase(), PathHelper.getPathForModel("crossbow.obj"));

    public IModelCustom crossbowModel = AdvancedModelLoader.loadModel(crossbowModelResource);
	
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
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		switch(type){
		case EQUIPPED : {
				GL11.glPushMatrix();
				
				Minecraft.getMinecraft().renderEngine.bindTexture(textureResource);

				GL11.glRotatef(90f, 1.0f, 0.0f, 0.0f);
				GL11.glRotatef(312f, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(90f, 0.0f, 0.0f, 1.0f);
				
				if(data[1] != null && data[1] instanceof EntityPlayer){
					GL11.glTranslatef(0.05f, -0.05f, -0.5f);
				} else{
					GL11.glTranslatef(0.05f, -0.05f, -0.8f);
				}
				
				crossbowModel.renderAll();
				
				GL11.glPopMatrix();
				break;
			}
		case EQUIPPED_FIRST_PERSON: {
			GL11.glPushMatrix();
			
			Minecraft.getMinecraft().renderEngine.bindTexture(textureResource);

			GL11.glRotatef(90f, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(312f, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(90f, 0.0f, 0.0f, 1.0f);
			
			GL11.glTranslatef(0.05f, -0.3f, -0.8f);
			
			crossbowModel.renderAll();
			
			GL11.glPopMatrix();
			break;
		}
		case ENTITY: {
			GL11.glPushMatrix();
			
			Minecraft.getMinecraft().renderEngine.bindTexture(textureResource);

			GL11.glRotatef(90f, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(260.0f, 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(270f, 0.0f, 0.0f, 1.0f);
			
			GL11.glTranslatef(0.0f, 0.0f, 0.0f);
			
			crossbowModel.renderAll();
			
			GL11.glPopMatrix();
			break;
		}
		default: break; //Don't ask me. Tutorial said so
		}

	}

}
