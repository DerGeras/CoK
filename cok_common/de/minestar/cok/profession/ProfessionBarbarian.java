package de.minestar.cok.profession;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
		ItemStack warhammerStack = new ItemStack(ClashOfKingdoms.warhammerItem);
		setGivenItem(warhammerStack);
		this.addItemStackToInventory(player, warhammerStack);
	}

	@Override
	public float getPunishment() {
		return 0.3F;
	}

}
