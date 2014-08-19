package de.minestar.cok.game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.minestar.cok.util.ChatSendHelper;

public class CoKGame {

	private HashMap<String, Team> teams = new HashMap<String, Team>();
	
	private String name;
	private boolean isRunning = false;
	
	public CoKGame(String name){
		this.name = name;
	}
	
	public void addTeam(Team team){
		if(team != null){
			team.setGame(this);
			teams.put(team.getName(), team);
		}
	}
	
	public void removeTeam(Team team){
		teams.remove(team.getName());
	}
	
	public void removeTeam(String name){
		teams.remove(name);
	}
	
	public Team getTeam(int color){
		for(Team team : teams.values()){
			if(team.getColorAsInt() == color){
				return team;
			}
		}
		return null;
	}
	
	public Team getTeam(String name){
		return teams.get(name);
	}
	
	public Collection<Team> getAllTeams(){
		return teams.values();
	}
	
	public Collection<String> getAllTeamNames(){
		return teams.keySet();
	}
	
	public boolean isRunning(){
		return isRunning;
	}
	
	public String getName(){
		return name;
	}
	
	@SideOnly(Side.SERVER)
	public void startGame(){
		ChatSendHelper.broadCastError("Winter is comming!");
		ChatSendHelper.broadCastMessage("Started the game " + name +  "!");
		isRunning = true;
	}
	
	@SideOnly(Side.SERVER)
	public void stopGame(){
		ChatSendHelper.broadCastError("The game " + name + " has ended!");
		isRunning = false;
	}
	
}
