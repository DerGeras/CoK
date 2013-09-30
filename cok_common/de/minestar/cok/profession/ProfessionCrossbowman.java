package de.minestar.cok.profession;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.game.Team;

public class ProfessionCrossbowman extends Profession {

	public ProfessionCrossbowman() {
		super("Crossbowman");
		givenItems.add(ClashOfKingdoms.crossBowItem);
		givenItems.add(ClashOfKingdoms.boltItem);
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
		this.addItemStackToInventory(player, new ItemStack(ClashOfKingdoms.crossBowItem));
		this.addItemStackToInventory(player, new ItemStack(ClashOfKingdoms.boltItem, 64));
	}

	@Override
	public float getPunishment() {
		return 0.5F;
	}

}
