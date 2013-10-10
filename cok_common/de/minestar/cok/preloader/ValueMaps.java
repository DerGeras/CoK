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
package de.minestar.cok.preloader;

import java.util.HashMap;

public class ValueMaps {
	
	//Values for the itemstack (BlockPlace)
	public HashMap<String,String> ISob = new HashMap<String,String>();
	public HashMap<String,String> ISdev = new HashMap<String,String>();
	
	//Values for the ItemInWorldManager (BlockBreak)
	public HashMap<String,String> IIWMob = new HashMap<String,String>();
	public HashMap<String,String> IIWMdev = new HashMap<String,String>();
	
	public ValueMaps(){
		IIWMob.put("className", "jd");
		IIWMob.put("javaClassName", "jd");
		IIWMob.put("targetMethodName", "d");// searge name func_73079_d
		IIWMob.put("worldFieldName", "a"); // searge name field_73092_a
		IIWMob.put("entityPlayerFieldName", "b");// searge name field_73090_b
		IIWMob.put("worldJavaClassName", "aab");
		IIWMob.put("getBlockMetadataMethodName", "h");// searge name func_72805_g
		IIWMob.put("blockJavaClassName", "apa");
		IIWMob.put("blocksListFieldName", "p");// searge name field_71973_m
		IIWMob.put("entityPlayerJavaClassName", "sq");
		IIWMob.put("entityPlayerMPJavaClassName", "jc");

	    IIWMdev.put("className", "net.minecraft.item.ItemInWorldManager");
        IIWMdev.put("javaClassName", "net/minecraft/item/ItemInWorldManager");
        IIWMdev.put("targetMethodName", "removeBlock");
        IIWMdev.put("worldFieldName", "theWorld");
        IIWMdev.put("entityPlayerFieldName", "thisPlayerMP");
        IIWMdev.put("worldJavaClassName", "net/minecraft/world/World");
        IIWMdev.put("getBlockMetaiiwmHMdevMethodName", "getBlockMetadata");
        IIWMdev.put("blockJavaClassName", "net/minecraft/block/Block");
        IIWMdev.put("blocksListFieldName", "blocksList");
        IIWMdev.put("entityPlayerJavaClassName", "net/minecraft/entity/player/EntityPlayer");
        IIWMdev.put("entityPlayerMPJavaClassName", "net/minecraft/entity/player/EntityPlayerMP");

        ISob.put("className", "wm");
        ISob.put("javaClassName", "wm");
        ISob.put("targetMethodName", "a");
        ISob.put("itemstackJavaClassName", "wm");
        ISob.put("entityPlayerJavaClassName", "sq");
        ISob.put("worldJavaClassName", "aab");

        ISdev.put("className", "net.minecraft.item.ItemStack");
        ISdev.put("javaClassName", "net/minecraft/item/ItemStack");
        ISdev.put("targetMethodName", "tryPlaceItemIntoWorld");// searge name func_77943_a
        ISdev.put("itemstackJavaClassName", "net/minecraft/item/ItemStack");
        ISdev.put("entityPlayerJavaClassName", "net/minecraft/entity/player/EntityPlayer");
        ISdev.put("worldJavaClassName", "net/minecraft/world/World");
	}
	
}
