package de.minestar.cok.profession;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.game.Team;

public class ProfessionCrossbowman extends Profession {

	public ProfessionCrossbowman() {
		super("Crossbowman");
		givenItems.add(ClashOfKingdoms.crossBowItem);
		givenItems.add(ClashOfKingdoms.boltItem);
	}

	@Override
	public void giveKit(EntityPlayer player, Team team) {
		//Armor
		super.giveKit(player, team);
		
		//Weaponry
		player.inventory.addItemStackToInventory(new ItemStack(ClashOfKingdoms.crossBowItem));
		player.inventory.addItemStackToInventory(new ItemStack(ClashOfKingdoms.boltItem, 64));
		player.inventory.addItemStackToInventory(new ItemStack(ClashOfKingdoms.boltItem, 64));
	}

	@Override
	public float getPunishment() {
		return 0;
	}

}
