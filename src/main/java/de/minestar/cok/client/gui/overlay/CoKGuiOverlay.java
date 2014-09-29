package de.minestar.cok.client.gui.overlay;

import java.util.LinkedList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.minestar.cok.game.CoKPlayer;
import de.minestar.cok.game.ScoreContainer;
import de.minestar.cok.game.Team;
import de.minestar.cok.game.TeamRegistry;
import de.minestar.cok.util.Color;

public class CoKGuiOverlay extends Gui{
	
	public static LinkedList<ScoreContainer> scores = new LinkedList<ScoreContainer>();
	public static String gameName;
	
	/**
	 * draw all of the overlay
	 * @param event
	 */
	@SubscribeEvent
	public void drawOverlay(RenderGameOverlayEvent event){
		drawScore(event);
		drawPlayerList(event);
	}
	

	
	//score offsets
	private static final int scoreOverlayXOffset = 100; //100 pixels from the right corner
	private static final int scoreOverlayYOffset = 15;
	
	private static final int scoreOverlayWidth = 90;

	/**
	 * draws the score overlay
	 * @param event
	 */
	private void drawScore(RenderGameOverlayEvent event){
		//avoids drawing to many times
		if(event.type != ElementType.ALL || scores.size() == 0 || gameName == null){      
	      return;
	    }
		//draw score overlay
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(),
				Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		int x = res.getScaledWidth() - scoreOverlayXOffset;
		int y = scoreOverlayYOffset;
		int width = scoreOverlayWidth;
		int height = scores.size() * 12 + 12 + 6;
		//draw background
		this.drawGradientRect(x, y, x+width, y+height, 0xb0000000, 0xb0000000);
		//draw gamename
		x += 3;
		y += 3;
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		fontRenderer.drawStringWithShadow(Color.getColorCodeFromString("white") + "Game: " + gameName, x, y, 0xff0000);
		y += 12;
		for(ScoreContainer score : scores){
			fontRenderer.drawStringWithShadow(score.getFormattedString(), x, y, 0xff0000);
			y += 12;
		}
	}
	
	
	private static final int playerListColoumnWidth = 75;
	private static final int playerListRowHeight = 10;
	private static final int playerListTopMargin = 10;
	
	private void drawPlayerList(RenderGameOverlayEvent event) {
		if(event.type != ElementType.PLAYER_LIST){      
			return;
		}
		event.setCanceled(true);
		int coloumns = TeamRegistry.getTeamCount();
		int rows = 0;
		for(Team team : TeamRegistry.getAllTeams()){
			rows = Math.max(rows, team.getPlayerCount());
		}
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(),
				Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		int posX = (res.getScaledWidth() - coloumns * playerListColoumnWidth) / 2;
		int posY = playerListTopMargin;
		int xOffset = posX;
		int yOffset = posY;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		for(Team team : TeamRegistry.getAllTeams()){
			drawRect(xOffset, yOffset, xOffset + playerListColoumnWidth - 1, yOffset + playerListRowHeight, 0xa0000000);
			fontRenderer.drawStringWithShadow(team.getFormattedName(), xOffset + 1 , yOffset + 1, 0xff0000);
			for(CoKPlayer player : team.getAllPlayers()){
				yOffset += playerListRowHeight;
				drawRect(xOffset, yOffset, xOffset + playerListColoumnWidth - 1, yOffset + playerListRowHeight, 0xa0000000);
				fontRenderer.drawStringWithShadow(Color.getColorCodeFromString("white") + player.getUserName(), xOffset + 1, yOffset + 1, 0xff0000);
			}
			xOffset += playerListColoumnWidth;
			yOffset = posY;
		}
	}

}
