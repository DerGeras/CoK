package de.minestar.cok.profession;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import de.minestar.cok.game.Team;

public class ProfessionKing extends Profession {

	public ProfessionKing() {
		super("King");
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
		ItemStack plateStack = new ItemStack(Item.plateDiamond);
		setGivenItem(plateStack);
		player.inventory.armorInventory[2] = plateStack;
		
		ItemStack legs = player.inventory.armorItemInSlot(1);
		if(legs != null){
			if(!player.inventory.addItemStackToInventory(legs)){
				player.dropPlayerItem(legs);
			}
		}
		ItemStack legsStack = new ItemStack(Item.legsDiamond);
		setGivenItem(legsStack);
		player.inventory.armorInventory[1] = legsStack;
		
		ItemStack boots = player.inventory.armorItemInSlot(0);
		if(boots != null){
			if(!player.inventory.addItemStackToInventory(boots)){
				player.dropPlayerItem(boots);
			}
		}
		ItemStack bootsStack = new ItemStack(Item.bootsGold);
		setGivenItem(bootsStack);
		player.inventory.armorInventory[0] = bootsStack;
		
		//Weaponry
		ItemStack swordStack = new ItemStack(Item.swordDiamond);
		setGivenItem(swordStack);
		this.addItemStackToInventory(player, swordStack);
	}

	@Override
	public float getPunishment() {
		return 1.0f;
	}

}
