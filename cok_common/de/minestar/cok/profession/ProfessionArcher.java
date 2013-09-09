package de.minestar.cok.profession;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import de.minestar.cok.game.Team;

public class ProfessionArcher extends Profession{

	public ProfessionArcher() {
		super("Archer");
		givenItems.add(Item.bow);
		givenItems.add(Item.arrow);
	}

	@Override
	public void giveKit(EntityPlayer player, Team team) {
		super.giveKit(player, team);
		
		//Weaponry
		player.inventory.addItemStackToInventory(new ItemStack(Item.bow));
		player.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 64));
		player.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 64));
	}

	@Override
	public float getPunishment() {
		return 0;
	}

}
