package de.minestar.cok.game.profession;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import de.minestar.cok.game.Team;
import de.minestar.cok.init.ModItems;
import de.minestar.cok.util.ItemStackHelper;

public class ProfessionCrossbowman extends Profession {

	public ProfessionCrossbowman() {
		super("Crossbowman");
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
		ItemStack plateStack = new ItemStack(Items.chainmail_chestplate);
		ItemStackHelper.setGiven(plateStack);
		player.inventory.armorInventory[2] = plateStack;
		
		//Weaponry
		ItemStack crossbowStack = new ItemStack(ModItems.crossbow);
		ItemStackHelper.setGiven(crossbowStack);
		this.addItemStackToInventory(player, crossbowStack);
		ItemStack boltStack = new ItemStack(ModItems.bolt, 64);
		ItemStackHelper.setGiven(boltStack);
		this.addItemStackToInventory(player, boltStack);
	}

	@Override
	public float getPunishment() {
		return 0.5F;
	}

}
