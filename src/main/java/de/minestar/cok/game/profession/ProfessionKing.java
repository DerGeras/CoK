package de.minestar.cok.game.profession;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import de.minestar.cok.game.Team;
import de.minestar.cok.util.ItemStackHelper;

public class ProfessionKing extends Profession {

	public ProfessionKing() {
		super("King");
	}
	
	@Override
	public void giveKit(EntityPlayerMP player, Team team) {
		//Armor
		super.giveKit(player, team);
		
		ItemStack plate = player.inventory.armorItemInSlot(2);
		if(plate != null){
			if(!player.inventory.addItemStackToInventory(plate)){
				player.dropPlayerItemWithRandomChoice(plate, false);
			}
		}
		ItemStack plateStack = new ItemStack(Items.diamond_chestplate);
		ItemStackHelper.setGiven(plateStack);
		player.inventory.armorInventory[2] = plateStack;
		
		ItemStack legs = player.inventory.armorItemInSlot(1);
		if(legs != null){
			if(!player.inventory.addItemStackToInventory(legs)){
				player.dropPlayerItemWithRandomChoice(legs, false);
			}
		}
		ItemStack legsStack = new ItemStack(Items.chainmail_leggings);
		ItemStackHelper.setGiven(legsStack);
		player.inventory.armorInventory[1] = legsStack;
		
		ItemStack boots = player.inventory.armorItemInSlot(0);
		if(boots != null){
			if(!player.inventory.addItemStackToInventory(boots)){
				player.dropPlayerItemWithRandomChoice(boots, false);
			}
		}
		ItemStack bootsStack = new ItemStack(Items.golden_boots);
		ItemStackHelper.setGiven(bootsStack);
		player.inventory.armorInventory[0] = bootsStack;
		
		//Weaponry
		ItemStack swordStack = new ItemStack(Items.diamond_sword);
		ItemStackHelper.setGiven(swordStack);
		this.addItemStackToInventory(player, swordStack);
	}

	@Override
	public float getPunishment() {
		return 1.0f;
	}

}
