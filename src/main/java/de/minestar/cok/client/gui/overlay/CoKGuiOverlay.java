package de.minestar.cok.client.gui.overlay;

import java.util.LinkedList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.minestar.cok.game.ScoreContainer;

public class CoKGuiOverlay extends Gui{
	
	public static LinkedList<ScoreContainer> scores = new LinkedList<ScoreContainer>();
	
	//score offsets
	private static final int scoreOverlayXOffset = 100; //100 pixels from the right corner
	private static final int scoreOverlayYOffset = 15;
	
	private static final int scoreOverlayWidth = 90;
	
	/**
	 * draw all of the overlay
	 * @param event
	 */
	@SubscribeEvent
	public void drawOverlay(RenderGameOverlayEvent event){
		drawScore(event);
	}
	
	/**
	 * draws the score overlay
	 * @param event
	 */
	private void drawScore(RenderGameOverlayEvent event){
		//avoids drawing to many times
		if(event.type != ElementType.ALL){      
	      return;
	    }
		//draw score overlay
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(),
				Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		int x = res.getScaledWidth() - scoreOverlayXOffset;
		int y = scoreOverlayYOffset;
		int width = scoreOverlayWidth;
		int height = scores.size() * 12 + 6;
		//draw background
		this.drawGradientRect(x, y, x+width, y+height, 0xb0000000, 0xb0000000);
		//draw scores
		x += 3;
		y += 3;
		for(ScoreContainer score : scores){
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(score.getFormattedString(), x, y, 0xff0000);
			y += 12;
		}
	}

}
