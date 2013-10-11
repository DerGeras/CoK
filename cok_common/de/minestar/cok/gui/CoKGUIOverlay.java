package de.minestar.cok.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.ForgeSubscribe;
import de.minestar.cok.references.Color;

public class CoKGUIOverlay {
	
	public static ArrayList<TeamScore> scoreList = new ArrayList<TeamScore>();
	
	@ForgeSubscribe
	public void onRenderGameOverlay(RenderGameOverlayEvent event){
		//Fetch MC instance
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution res = new ScaledResolution(mc.gameSettings, mc.displayWidth, 
				mc.displayHeight);
		
		//set initial pos
		int x = res.getScaledWidth() - 40;
		int y = 20;
		
		//draw score
		FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
		for(TeamScore score : scoreList){
			renderer.drawString(score.getCurrPoints() + "/" + score.getMaxPoints(), x, y,
					Color.getHexColorFromInt(score.getColor()));
			y += 15;
		}
	}

}
