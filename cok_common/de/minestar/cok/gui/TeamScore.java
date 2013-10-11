package de.minestar.cok.gui;

public class TeamScore {

	private int color;
	private int currPoints;
	private int maxPoints;
	
	
	public TeamScore(int color, int currPoints, int maxPoints){
		this.color = color;
		this.currPoints = currPoints;
		this.maxPoints = maxPoints;
	}
	
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getMaxPoints() {
		return maxPoints;
	}
	public void setMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}
	public int getCurrPoints() {
		return currPoints;
	}
	public void setCurrPoints(int currPoints) {
		this.currPoints = currPoints;
	}
	
	
}
