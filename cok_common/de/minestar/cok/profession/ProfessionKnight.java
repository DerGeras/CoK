package de.minestar.cok.profession;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import de.minestar.cok.game.Team;

public class ProfessionKnight extends Profession {

	public ProfessionKnight() {
		super("Knight");
	}

	@Override
	public void giveKit(EntityPlayer player, Team team) {
		//Armour
		player.inventory.armorInventory[3] = new ItemStack(Item.helmetDiamond);
		player.inventory.armorInventory[2] = new ItemStack(Item.plateDiamond);
		player.inventory.armorInventory[1] = new ItemStack(Item.legsDiamond);
		player.inventory.armorInventory[0] = new ItemStack(Item.bootsDiamond);
		
		//Weaponry
		player.inventory.addItemStackToInventory(new ItemStack(Item.swordDiamond));
	}

}
