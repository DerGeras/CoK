package de.minestar.cok.gui;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.ForgeSubscribe;
import de.minestar.cok.references.Color;

public class CoKGUIOverlay {
	
	public static ArrayList<TeamScore> scoreList = new ArrayList<TeamScore>();
	
	@ForgeSubscribe
	public void onRenderGameOverlay(RenderGameOverlayEvent event){
		//Fetch resolution
		ScaledResolution res = event.resolution;
		
		if(event.type == ElementType.TEXT){	
			//set initial pos
			int x = res.getScaledWidth() - 40;
			int y = 20;
			
			//draw score
			FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
			try{
				for(TeamScore score : scoreList){
					renderer.drawString(score.getCurrPoints() + "/" + score.getMaxPoints(), x, y,
							Color.getHexColorFromInt(score.getColor()));
					y += 15;
				}
			} catch(ConcurrentModificationException e){
				System.err.println("Error while drawing the score!");
				e.printStackTrace();
			}
		}
	}

}
