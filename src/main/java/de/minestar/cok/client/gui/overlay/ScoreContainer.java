package de.minestar.cok.client.gui.overlay;

import de.minestar.cok.util.Color;

/**
 * basic scorecontainer, should only be used for the gui overlay
 * @author Oliver
 *
 */
public class ScoreContainer {

	private char teamColor;
	private String teamName;
	private int currentScore;
	private int maxScore;
	
	public ScoreContainer(char teamColor, String teamName, int score, int maxScore){
		this.teamColor = teamColor;
		this.teamName = teamName;
		this.currentScore = score;
		this.maxScore = maxScore;
	}
	
	public String getFormattedString(){
		return String.format("%s%s%s:%s/%s",
				Color.getColorCodeFromChar(teamColor),
				teamName,
				Color.getColorCodeFromString("white"),
				currentScore,
				maxScore);
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	public char getTeamColor() {
		return teamColor;
	}

	public String getTeamName() {
		return teamName;
	}
	
	
	
}
