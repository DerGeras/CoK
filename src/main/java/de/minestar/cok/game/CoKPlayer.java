package de.minestar.cok.game;

import java.util.UUID;

import cpw.mods.fml.client.config.GuiCheckBox;
import cpw.mods.fml.client.config.GuiConfig;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayerMP;
import de.minestar.cok.game.profession.Profession;
import de.minestar.cok.util.PlayerHelper;

public class CoKPlayer {

	private UUID uuid;
	private Team team;
	private Profession profession;
	
	public CoKPlayer(UUID uuid){
		this.uuid = uuid;
	}
	
	public void setTeam(Team team){
		this.team = team;
	}
	
	public EntityPlayerMP getPlayerEntity(){
		return PlayerHelper.getPlayerForUUID(uuid);
	}
	
	public String getUserName(){
		return PlayerHelper.getNameForUUID(uuid);
	}
	
	public UUID getUUID(){
		return uuid;
	}
	
	public Team getTeam(){
		return team;
	}
	
	public void setProfession(Profession profession){
		this.profession = profession;
	}
	
	public Profession getProfession(){
		return profession;
	}
	
	public CoKGame getGame(){
		if(team != null){
			return team.getGame();
		}
		return null;
	}
	
}
