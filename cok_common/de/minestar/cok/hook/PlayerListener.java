package de.minestar.cok.hook;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Team;
import de.minestar.cok.helper.ChatSendHelper;

public class PlayerListener {

	@ForgeSubscribe
	public void onPlayerDeath(PlayerDropsEvent event){
		String name = event.entityPlayer.username;
		if(name == null){
			return;
		}
		Team team = CoKGame.getTeamOfPlayer(name);
		if(team == null){
			return;
		}
		if(team.getCaptain().equals(name)){
			team.setCaptain(team.getRandomPlayer(CoKGame.rand));
			ChatSendHelper.broadCastError("THE RULER OF THE KINGDOM " + team.getName() + " " + name + " HAS DIED!");
			ChatSendHelper.broadCastError("LONG LIFE KING " + team.getCaptain() + "!");
		}
	}
	
}
