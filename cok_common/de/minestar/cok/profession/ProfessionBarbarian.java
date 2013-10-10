/*******************************************************************************
 * Clash of Kingdoms Minecraft Mod
 *     Copyright (C) 2013 Geras
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
