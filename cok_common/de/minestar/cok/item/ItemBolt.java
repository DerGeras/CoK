package de.minestar.cok.item;

import de.minestar.cok.ClashOfKingdoms;

public class ItemBolt extends CoKItem{

	public ItemBolt(int id) {
		super(id);
		setUnlocalizedName("bolt");
        this.setCreativeTab(ClashOfKingdoms.cokItemTab);
	}

}
