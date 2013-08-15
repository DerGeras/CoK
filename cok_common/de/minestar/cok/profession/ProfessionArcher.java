package de.minestar.cok.profession;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import de.minestar.cok.game.Team;

public class ProfessionArcher extends Profession{

	public ProfessionArcher() {
		super("Archer");
	}

	@Override
	public void giveKit(EntityPlayer player, Team team) {
		//Armor
		player.inventory.armorInventory[3] = new ItemStack(Item.helmetLeather);
		player.inventory.armorInventory[2] = new ItemStack(Item.plateLeather);
		player.inventory.armorInventory[1] = new ItemStack(Item.legsLeather);
		player.inventory.armorInventory[0] = new ItemStack(Item.bootsLeather);
		
		//Weaponry
		player.inventory.addItemStackToInventory(new ItemStack(Item.bow));
		player.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 64));
		player.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 64));
	}

}
