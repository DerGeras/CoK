package de.minestar.cok.profession;

import net.minecraft.entity.player.EntityPlayer;
import de.minestar.cok.game.Team;

public class ProfessionKing extends Profession {

	public ProfessionKing() {
		super("King");
	}
	
	@Override
	public void giveKit(EntityPlayer player, Team team) {
		//Armor
		super.giveKit(player, team);
		
		//Weaponry
	}

	@Override
	public float getPunishment() {
		return 0;
	}

}
