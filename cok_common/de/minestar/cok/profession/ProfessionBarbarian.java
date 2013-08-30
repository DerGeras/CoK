package de.minestar.cok.profession;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.game.Team;

public class ProfessionBarbarian extends Profession {

	public ProfessionBarbarian() {
		super("Barbarian");
	}

	@Override
	public void giveKit(EntityPlayer player, Team team) {
		//Armor
		super.giveKit(player, team);
		
		//Weaponry
		player.inventory.addItemStackToInventory(new ItemStack(ClashOfKingdoms.warhammerItem));
	}

}
