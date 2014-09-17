package de.minestar.cok.client.gui.overlay;

import java.util.HashSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.minestar.cok.util.LogHelper;

public class CoKGuiOverlay extends Gui{
	
	//score offsets in %
	private static float scoreOverlayXOffset = 0.8f;
	private static float scoreOverlayYOffset = 0.1f;
	
	private static int scoreOverlayWidth = 50;
	
	public static HashSet<ScoreContainer> scores = new HashSet<ScoreContainer>();
	
	//TODO testing stuff!
	static{
		scores.add(new ScoreContainer('0', "Test1", 13, 15));
		scores.add(new ScoreContainer('5', "Test2", 13, 15));
	}
	
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
		int x = (int) (scoreOverlayXOffset * res.getScaledWidth());
		int y = (int) (scoreOverlayYOffset * res.getScaledHeight());
		int width = scoreOverlayWidth;
		int height = scores.size() * 12;
		this.drawGradientRect(x, y, x+width, y+height, 0xb0000000, 0xb0000000);
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("TestString", x+3, y+3, 0xff0000);
	}

}
