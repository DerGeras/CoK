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
		
		ItemStack plate = player.inventory.armorItemInSlot(2);
		if(plate != null){
			if(!player.inventory.addItemStackToInventory(plate)){
				player.dropPlayerItem(plate);
			}
		}
		ItemStack plateStack = new ItemStack(Item.plateChain);
		setGivenItem(plateStack);
		player.inventory.armorInventory[2] = plateStack;
		
		//Weaponry
		ItemStack crossbowStack = new ItemStack(ClashOfKingdoms.crossBowItem);
		setGivenItem(crossbowStack);
		this.addItemStackToInventory(player, crossbowStack);
		ItemStack boltStack = new ItemStack(ClashOfKingdoms.boltItem, 64);
		setGivenItem(boltStack);
		this.addItemStackToInventory(player, boltStack);
	}

	@Override
	public float getPunishment() {
		return 0.5F;
	}

}
