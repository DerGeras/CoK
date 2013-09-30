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
		//Armor
		super.giveKit(player, team);
		
		ItemStack plate = player.inventory.armorItemInSlot(2);
		if(plate != null){
			if(!player.inventory.addItemStackToInventory(plate)){
				player.dropPlayerItem(plate);
			}
		}
		player.inventory.armorInventory[2] = new ItemStack(Item.plateDiamond);
		
		ItemStack legs = player.inventory.armorItemInSlot(1);
		if(legs != null){
			if(!player.inventory.addItemStackToInventory(legs)){
				player.dropPlayerItem(legs);
			}
		}
		player.inventory.armorInventory[1] = new ItemStack(Item.legsDiamond);
		
		ItemStack boots = player.inventory.armorItemInSlot(0);
		if(boots != null){
			if(!player.inventory.addItemStackToInventory(boots)){
				player.dropPlayerItem(boots);
			}
		}
		player.inventory.armorInventory[0] = new ItemStack(Item.bootsGold);
		
		//Weaponry
		this.addItemStackToInventory(player, new ItemStack(Item.swordDiamond));
	}

	@Override
	public float getPunishment() {
		return 1.0f;
	}

}
