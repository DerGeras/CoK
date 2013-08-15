package de.minestar.cok.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.opengl.GL11;

public class CoKGui extends GuiScreen {
	
	private static final int backgroundX = 176;
	private static final int backgroundY = 88;
	
	private static final int START_GAME_BUTTON_ID = 0;
	
	
	@Override
	public void drawScreen(int x, int y, float f){
		drawDefaultBackground();
		
		Minecraft.getMinecraft().renderEngine.bindTexture("/mods/ClashOfKingdoms/gui/GuiBackground.png");		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		int posX = (this.width - backgroundX) / 2;
		int posY = (this.height - backgroundY) / 2;
		
		drawTexturedModalRect(posX, posY, 0, 0, backgroundX, backgroundY);
		
		super.drawScreen(x, y, f);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		this.buttonList.clear();
		
		int posX = (this.width - backgroundX) / 2;
		int posY = (this.height - backgroundY) / 2;
		
		this.buttonList.add(new GuiButton(START_GAME_BUTTON_ID, posX + 40, posY + 40, "Start Game"));
	}

}
