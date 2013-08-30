package de.minestar.cok.hook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.game.Team;
import de.minestar.cok.helper.ChatSendHelper;

public class PlayerListener {

	@ForgeSubscribe
	public void onPlayerDeath(PlayerDropsEvent event){
		if(!CoKGame.gameRunning){
			return;
		}
		String name = event.entityPlayer.username;
		if(name == null){
			return;
		}
		Team team = CoKGame.getTeamOfPlayer(name);
		if(team == null){
			return;
		}
		//Set a new captain
		if(team.getCaptain().equals(name)){
			team.setRandomCaptain();
			ChatSendHelper.broadCastError("THE RULER OF THE KINGDOM " + team.getName() + " " + name + " HAS DIED!");
			if(team.getCaptain().equals("")){
				ChatSendHelper.broadCastError("THERE IS NO NEW RULER TO ANOUNCE... THIS IS BAD!");
			} else{
				ChatSendHelper.broadCastError("LONG LIFE KING " + team.getCaptain() + "!");
			}
		}
	}
	
	@ForgeSubscribe
	public void onPlayerHit(LivingAttackEvent event){
		if(event.entity instanceof EntityPlayer){
			System.out.println("Hi");
			EntityPlayer attacker = (EntityPlayer) event.entity;
			if(CoKGame.spectators.contains(attacker.username)){
				event.setCanceled(true);
			}
		}
	}
	
}
