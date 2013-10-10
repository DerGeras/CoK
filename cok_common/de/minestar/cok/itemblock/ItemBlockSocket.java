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
package de.minestar.cok.itemblock;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockSocket extends ItemBlock {

	public static final String[] subNames = {
    	"black", "darkBlue", "darkGreen", "darkAqua",
    	"darkRed", "purple", "gold", "gray", "darkGray",
    	"blue", "green", "aqua", "red", "lightPurple",
    	"yellow", "white"
    	};
	
	public ItemBlockSocket(int id) {
		super(id);
		setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
	}
	
	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}

}
