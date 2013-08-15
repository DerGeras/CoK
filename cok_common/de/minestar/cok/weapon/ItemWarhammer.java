package de.minestar.cok.weapon;

import net.minecraft.item.EnumToolMaterial;
import de.minestar.cok.ClashOfKingdoms;

public class ItemWarhammer extends CoKWeapon {

	public ItemWarhammer(int id, EnumToolMaterial material) {
		super(id);
		this.maxStackSize = 1;
		this.toolMaterial = material;
        this.setMaxDamage(material.getMaxUses() * 3);
        setUnlocalizedName("warhammer");
        this.setCreativeTab(ClashOfKingdoms.cokItemTab);
        this.weaponDamage = 5 + material.getDamageVsEntity();
        
        this.setFull3D(); //NEEDED for 3D Models
	}

}
