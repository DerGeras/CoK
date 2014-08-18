package de.minestar.cok.game;

import java.util.HashMap;
import java.util.HashSet;

public class CoKGame {

	private HashMap<String, Team> teams = new HashMap<String, Team>();
	
	private String name;
	
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
	
	public HashSet<Team> getAllTeams(){
		return new HashSet<Team>(teams.values());
	}
	
}
