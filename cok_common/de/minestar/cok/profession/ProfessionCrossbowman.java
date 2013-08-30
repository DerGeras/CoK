package de.minestar.cok.profession;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.game.Team;

public class ProfessionCrossbowman extends Profession {

	public ProfessionCrossbowman() {
		super("Crossbowman");
	}

	@Override
	public void giveKit(EntityPlayer player, Team team) {
		//Armor
		super.giveKit(player, team);
		
		//Weaponry
		player.inventory.addItemStackToInventory(new ItemStack(ClashOfKingdoms.crossBowItem));
		player.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 64));
		player.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 64));
	}

}
