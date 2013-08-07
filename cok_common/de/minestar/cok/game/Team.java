package de.minestar.cok.game;

import java.util.HashSet;

public class Team {
	
	public static final int RED = 0;
	public static final int BLUE = 1;
	public static final int GREEN = 2;
	public static final int BLACK = 3;
	
	private String name;
	private int color;
	private String captain;
	
	private HashSet<String> players = new HashSet<String>();
	
	public Team(String name, int color) {
		this.setName(name);
		this.setColor(color);
		this.captain = "";
	}
	
	public void addPlayer(String name){
		if(!players.contains(name)){
			players.add(name);
			if(captain == ""){
				captain = name;
			}
		}		
	}
	
	public void removePlayer(String name){
		if(players.contains(name)){
			players.remove(name);
		}
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

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		if(color < 0){
			this.color = 0;
			return;
		}
		if(color > 3){
			this.color = 0;
			return;
		}
		this.color = color;
	}
	
}
