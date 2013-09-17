package de.minestar.cok.profession;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import de.minestar.cok.game.Team;

public class ProfessionKnight extends Profession {

	public ProfessionKnight() {
		super("Knight");
		givenItems.add(Item.swordDiamond);
	}

	@Override
	public void giveKit(EntityPlayer player, Team team) {
		//Armor
		super.giveKit(player, team);
		
		//Weaponry
		player.inventory.addItemStackToInventory(new ItemStack(Item.swordDiamond));
	}

	@Override
	public float getPunishment() {
		return 0;
	}

}
