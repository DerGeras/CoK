package de.minestar.cok.profession;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import de.minestar.cok.game.Team;

public class ProfessionKing extends Profession {

	public ProfessionKing() {
		super("King");
		givenItems.add(Item.plateDiamond);
		givenItems.add(Item.legsDiamond);
		givenItems.add(Item.bootsGold);
		givenItems.add(Item.swordDiamond);
	}

	@Override
	public void giveKit(EntityPlayer player, Team team) {
		// Armor
		super.giveKit(player, team);
		player.inventory.armorInventory[2] = new ItemStack(Item.plateDiamond);
		player.inventory.armorInventory[1] = new ItemStack(Item.legsDiamond);
		player.inventory.armorInventory[0] = new ItemStack(Item.bootsGold);

		// Weaponry
		player.inventory.addItemStackToInventory(new ItemStack(Item.swordDiamond));
	}

	@Override
	public float getPunishment() {
		return 1.0f;
	}

}
