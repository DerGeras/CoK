/*******************************************************************************
 * Clash of Kingdoms Minecraft Mod
 *     Copyright (C) 2013 Geras
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package de.minestar.cok.network;

import java.io.DataInputStream;
import java.io.IOException;

import de.minestar.cok.game.CoKGame;
import de.minestar.cok.gui.CoKGUIOverlay;
import de.minestar.cok.gui.TeamScore;
import de.minestar.cok.references.Color;

public class CoKGamePacketClient {
	
	/**
	 * sets the gamestate of clients on login.
	 * @param inputStream
	 */
	public static void setGameState(DataInputStream inputStream){
		try {
			//read gameState
			CoKGame.gameRunning = inputStream.readBoolean();
			//read teams
			int numberOfTeams = inputStream.readInt();
			int numberOfPlayers = 0;
			CoKGame.teams.clear();
			for(int i = 0; i < numberOfTeams; i++){
				String teamName = inputStream.readUTF();
				CoKGame.addTeam(teamName, inputStream.readChar());
				//read users
				numberOfPlayers = inputStream.readInt();
				for(int j = 0; j < numberOfPlayers; i++){
					CoKGame.addPlayerToTeam(teamName, inputStream.readUTF());
				}
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void updateGameRunning(DataInputStream inputStream){
		try {
			CoKGame.gameRunning = inputStream.readBoolean();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void addTeam(DataInputStream inputStream){
		try {
			String teamName = inputStream.readUTF();
			String color = inputStream.readUTF();
			CoKGame.addTeam(teamName, Color.getColorFromString(color));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeTeam(DataInputStream inputStream){
		try {
			String teamName = inputStream.readUTF();
			CoKGame.removeTeam(teamName);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void addPlayer(DataInputStream inputStream){
		try {
			String teamName = inputStream.readUTF();
			String playerName = inputStream.readUTF();
			CoKGame.addPlayerToTeam(teamName, playerName);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void removePlayer(DataInputStream inputStream){
		try {
			String teamName = inputStream.readUTF();
			String playerName = inputStream.readUTF();
			CoKGame.removePlayerFromTeam(teamName, playerName);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void setScore(DataInputStream inputStream){
		try{
			CoKGUIOverlay.scoreList.clear();
			int numteams = inputStream.readInt();
			for(int i = 0; i < numteams; i++){
				CoKGUIOverlay.scoreList.add(new TeamScore(inputStream.readInt(), inputStream.readInt(),
						inputStream.readInt()));
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
}
