package de.minestar.cok.item;

import de.minestar.cok.ClashOfKingdoms;

public class ItemDough extends CoKItem {

	public ItemDough(int id) {
		super(id);
		setUnlocalizedName("dough");
        this.setCreativeTab(ClashOfKingdoms.cokItemTab);
	}

}
