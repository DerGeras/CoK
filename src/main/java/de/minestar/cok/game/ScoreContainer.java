package de.minestar.cok.game;

import io.netty.buffer.ByteBuf;
import de.minestar.cok.util.Color;

/**
 * basic scorecontainer, should only be used for the gui overlay
 * @author Oliver
 *
 */
public class ScoreContainer implements Comparable<ScoreContainer>{

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
	
	public ScoreContainer(ByteBuf buf){
		fromBytes(buf);
	}
	
	public String getFormattedString(){
		return String.format("%s%s%s:%s/%s",
				Color.getColorCodeFromChar(teamColor),
				teamName,
				Color.getColorCodeFromString("white"),
				currentScore,
				maxScore);
	}
	
	/**
	 * read content from byte buffer
	 * @param buf
	 */
	public void fromBytes(ByteBuf buf){
		this.teamColor = buf.readChar();
		int teamNameSize = buf.readInt();
		this.teamName = new String(buf.readBytes(teamNameSize).array());
		this.currentScore = buf.readInt();
		this.maxScore = buf.readInt();
	}
	
	/**
	 * write content to byte buffer
	 * @param buf
	 */
	public void toBytes(ByteBuf buf){
		buf.writeChar(teamColor);
		buf.writeInt(teamName.length());
		buf.writeBytes(teamName.getBytes());
		buf.writeInt(currentScore);
		buf.writeInt(maxScore);
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

	@Override
	public int compareTo(ScoreContainer o) {
		return this.teamColor < o.teamColor ? -1 : 1;
	}
	
	
	
}
