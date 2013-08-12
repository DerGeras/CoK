package de.minestar.cok.game;

import java.util.LinkedList;
import java.util.Random;

public class Team {
	
	private String name;
	private char color;
	private String captain;


	private LinkedList<String> players = new LinkedList<String>();
	
	public LinkedList<String> getAllPlayers(){
		return players;
	}
	
	public String getRandomPlayer(Random rand){
		return players.get(rand.nextInt(players.size()));
	}
	
	public Team(String name, char color) {
		this.setName(name);
		this.setColor(color);
		this.captain = "";
	}
	
	public Team(String name, char color, String captain){
		this(name, color);
		this.captain = captain;
	}
	
	public int getColorAsInt(){
		return color >= 97 ? color - 87 : color - 48;
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
