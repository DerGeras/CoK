package de.minestar.cok.game;

import java.util.HashSet;

public class Team {
	
	private String name;
	private char color;
	private String captain;


	private HashSet<String> players = new HashSet<String>();
	
	public Team(String name, char color) {
		this.setName(name);
		this.setColor(color);
		this.captain = "";
	}
	
	public Team(String name, char color, String captain){
		this(name, color);
		this.captain = captain;
	}
	
	public boolean addPlayer(String name){
		boolean res = players.contains(name);
		if(!res){
			players.add(name);
			if(captain == ""){
				captain = name;
			}
		}	
		return !res;
	}
	
	public boolean removePlayer(String name){
		boolean res = players.contains(name);
		if(res){
			players.remove(name);
		}
		return res;
	}
	
	public boolean hasPlayer(String name){
		return players.contains(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getColor() {
		return color;
	}

	public void setColor(char color) {
		if(color < 0){
			this.color = 0;
			return;
		}
		if(color > 'f'){
			this.color = 'f';
			return;
		}
		this.color = color;
	}
	
	
	public String getCaptain() {
		return captain;
	}

	public void setCaptain(String captain) {
		this.captain = captain;
	}
	
}
