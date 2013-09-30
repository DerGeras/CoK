package de.minestar.cok.profession;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.game.Team;

public class ProfessionBarbarian extends Profession {

	public ProfessionBarbarian() {
		super("Barbarian");
		givenItems.add(ClashOfKingdoms.warhammerItem);
		givenItems.add(Item.plateChain);
	}

	@Override
	public void giveKit(EntityPlayer player, Team team) {
		//Armor
		super.giveKit(player, team);
		ItemStack plate = player.inventory.armorItemInSlot(2);
		if(plate != null){
			if(!player.inventory.addItemStackToInventory(plate)){
				player.dropPlayerItem(plate);
			}
		}
		player.inventory.armorInventory[2] = new ItemStack(Item.plateChain);
		
		//Weaponry
		this.addItemStackToInventory(player, new ItemStack(ClashOfKingdoms.warhammerItem));
	}

	@Override
	public float getPunishment() {
		return 0.3F;
	}

}
