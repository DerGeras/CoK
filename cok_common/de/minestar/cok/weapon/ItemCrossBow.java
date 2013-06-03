package de.minestar.cok.weapon;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import de.minestar.cok.ClashOfKingdoms;

public class ItemCrossBow extends Item{

	private static int ticksToCharge = 5 * 20;
	
	public ItemCrossBow(int par1) {
		super(par1);
		this.maxStackSize = 1;
        this.setMaxDamage(ticksToCharge);
        this.setCreativeTab(ClashOfKingdoms.cokTab);
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
		
		//Shooting the arrow
		boolean flag = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;

        if (flag || par1ItemStack.getItemDamage() == 0)
        {
            float f = (float) 10 *72000 / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if ((double)f < 0.1D)
            {
                return par1ItemStack;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            EntityArrow entityarrow = new EntityArrow(par2World, par3EntityPlayer, f * 2.0F);

            if (f == 1.0F)
            {
                entityarrow.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);

            if (k > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);

            if (l > 0)
            {
                entityarrow.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
            {
                entityarrow.setFire(100);
            }

            par1ItemStack.setItemDamage(par1ItemStack.getMaxDamage() - 1);
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityarrow.canBePickedUp = 2;
            }
            

            if (!par2World.isRemote)
            {
                par2World.spawnEntityInWorld(entityarrow);
            }
        }
        else{
        	//reload
        	if(par3EntityPlayer.capabilities.isCreativeMode || par1ItemStack.getItemDamage() < par1ItemStack.getMaxDamage() && par3EntityPlayer.inventory.hasItem(Item.arrow.itemID)){
        		if(par3EntityPlayer.inventory.hasItem(Item.arrow.itemID) && par1ItemStack.getItemDamage() == par1ItemStack.getMaxDamage()-1){
        			par3EntityPlayer.inventory.consumeInventoryItem(Item.arrow.itemID);
        		}
        		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        	}
        }
        
		return par1ItemStack;
	}

	public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count){
		if(stack.getItemDamage() > 0 && stack.getItemDamage() != stack.getMaxDamage()){
			stack.setItemDamage(stack.getItemDamage() - 1);
		}else{
			player.clearItemInUse();
		}
	}
	
	/**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return ticksToCharge;
    }

}
