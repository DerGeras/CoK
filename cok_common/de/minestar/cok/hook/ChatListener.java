package de.minestar.cok.hook;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Team;
import de.minestar.cok.references.Color;

public class ChatListener {

	@ForgeSubscribe
	public void serverChatSend(ServerChatEvent event) {
		String name = event.username;
		Team team = CoKGame.getTeamOfPlayer(name);
		if (team == null) {
			event.line = name + ": " + event.message;
			return;
		}
		String colorcode = Color.getColorCodeFromChar(team.getColor());
		event.line = colorcode + team.getName() + " " + Color.getColorCodeFromString("white") + name + ": " + event.message;
	}

}
