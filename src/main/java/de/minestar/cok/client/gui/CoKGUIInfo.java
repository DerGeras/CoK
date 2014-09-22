package de.minestar.cok.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.CoKGameRegistry;
import de.minestar.cok.game.CoKPlayer;
import de.minestar.cok.game.CoKPlayerRegistry;
import de.minestar.cok.game.Team;
import de.minestar.cok.game.TeamRegistry;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.PathHelper;

public class CoKGUIInfo extends GuiScreen {
	
	public static ResourceLocation backGroundTexture =
				new ResourceLocation(Reference.MOD_ID.toLowerCase(), PathHelper.getPathForGUI("GuiBackground.png"));
	
	private static int backGroundWidth = 256;
	private static int backGroundHeight = 150;
	
	private static final int textIndent = 12;
	private static final int textOffset = 12;
	
	private GuiButton gamesButton;
	private GuiButton teamsButton;
	private GuiButton playerButton;
	
	//Terribly lazy, I know.
	private int guiMode = 0;
	
	private EntityPlayer player;

	public CoKGUIInfo(EntityPlayer player){
		this.player = player;
	}
	
	@Override
	public void drawScreen(int x, int y, float f){
		drawDefaultBackground();
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(backGroundTexture);

		int posX = (this.width - backGroundWidth) / 2;
		int posY = (this.height - backGroundHeight) / 2;

		drawTexturedModalRect(posX, posY, 0, 0, backGroundWidth, backGroundHeight);

		super.drawScreen(x, y, f);
		
		//draw info
		posX = this.width / 2;
		posY += 15;
		
		switch(guiMode){
		case 1: {
			drawGamesScreen(posX, posY);
			break;
		}
		case 2: {
			drawTeamsScreen(posX, posY);
			break;
		}
		case 3: {
			drawPlayersScreen(posX, posY);
			break;
		}
		default: {
			//Nothing
			break;
		}
		}
	}
	
	private void drawGamesScreen(int posX, int posY){
		int xOffset = posX;
		int yOffset = posY;
		Minecraft.getMinecraft().fontRenderer.drawString("Games:", posX, posY, 0x000000);
		xOffset += textIndent;
		for(CoKGame game : CoKGameRegistry.getAllGames()){
			yOffset += textOffset;
			Minecraft.getMinecraft().fontRenderer.drawString(game.getName(), xOffset, yOffset, 0x000000);
		}
	}
	
	private void drawTeamsScreen(int posX, int posY){
		int xOffset = posX;
		int yOffset = posY;
		Minecraft.getMinecraft().fontRenderer.drawString("Teams:", posX, posY, 0x000000);
		xOffset += textIndent;
		for(Team team: TeamRegistry.getAllTeams()){
			yOffset += textOffset;
			Minecraft.getMinecraft().fontRenderer.drawString(team.getFormattedName(), xOffset, yOffset, 0x000000);
			xOffset += textIndent;
			for(CoKPlayer player : team.getAllPlayers()){
				yOffset += textOffset;
				Minecraft.getMinecraft().fontRenderer.drawString(player.getUserName(), xOffset, yOffset, 0x000000);
			}
			xOffset -= textIndent;
		}
	}
	
	private void drawPlayersScreen(int posX, int posY){
		int xOffset = posX;
		int yOffset = posY;
		Minecraft.getMinecraft().fontRenderer.drawString("Players without teams:", posX, posY, 0x000000);
		xOffset += textIndent;
		for(CoKPlayer player : CoKPlayerRegistry.getAllPlayers()){
			if(player.getTeam() == null){
				yOffset += textOffset;
				Minecraft.getMinecraft().fontRenderer.drawString(player.getUserName(), posX, posY, 0x000000);				
			}
		}
	}
	
	@Override
	public void initGui(){
		this.buttonList.clear();

		int posX = (this.width - backGroundWidth) / 2;
		int posY = (this.height - backGroundHeight) / 2;

		posX += 15;
		posY += 15;
		gamesButton = new GuiButton(0, posX, posY, 75, 20, "Games");
		this.buttonList.add(gamesButton);
		posY += 25;
		teamsButton = new GuiButton(1, posX, posY, 75, 20, "Teams");
		this.buttonList.add(teamsButton);
		posY += 25;
		playerButton = new GuiButton(2, posX, posY, 75, 20, "Players");
		this.buttonList.add(playerButton);
	}
	
	@Override
	protected void actionPerformed(GuiButton guiButton){
		switch(guiButton.id){
		case 0:{
			guiMode = 1;
			break;
		}
		case 1:{
			guiMode = 2;
			break;
		}
		case 2:{
			guiMode = 3;
			break;
		}
		}
	}
	
}
