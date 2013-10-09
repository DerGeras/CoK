package de.minestar.cok.weapon;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.entity.EntityBolt;
import de.minestar.cok.item.CoKItem;

public class ItemCrossBow extends CoKItem{

	private static int ticksToCharge = 2 * 20 + 200;
	
	public static final String CHARGED_STRING = "charged";
	public static final String CLIENT_CHARGED = "clientcharged";
	
	public ItemCrossBow(int par1) {
		super(par1);
		this.maxStackSize = 1;
        this.setMaxDamage(ticksToCharge);
        setUnlocalizedName("crossbow");
        this.setCreativeTab(ClashOfKingdoms.cokItemTab);
        this.setFull3D(); //NEEDED for 3D Models
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
		
		//Shooting the bolt
		boolean flag = par3EntityPlayer.capabilities.isCreativeMode;
		
		if(par1ItemStack.stackTagCompound == null){
			par1ItemStack.setTagCompound(new NBTTagCompound());
		}
		
        if (flag || par1ItemStack.stackTagCompound.getBoolean(CHARGED_STRING))
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

            EntityBolt entityBolt = new EntityBolt(par2World, par3EntityPlayer, f * 2.0F);

            if (f == 1.0F)
            {
                entityBolt.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);

            if (k > 0)
            {
                entityBolt.setDamage(entityBolt.getDamage() + (double)k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);

            if (l > 0)
            {
                entityBolt.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
            {
                entityBolt.setFire(100);
            }
            
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityBolt.canBePickedUp = 2;
            }
            
            if (!par2World.isRemote)
            {
            	par1ItemStack.stackTagCompound.setBoolean(CHARGED_STRING, false);
            	par1ItemStack.stackTagCompound.setBoolean(CLIENT_CHARGED, false);
                par2World.spawnEntityInWorld(entityBolt);
            }
        }
        else{
        	//reload
        	if(par3EntityPlayer.inventory.hasItem(ClashOfKingdoms.boltItem.itemID)){
            	par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        	}
        }
        
		return par1ItemStack;
	}

	/**
	 * Called while using the item
	 */
	public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count){
		super.onUsingItemTick(stack, player, count);
		if(count < 200){
			stack.stackTagCompound.setBoolean(CLIENT_CHARGED, true);
		}
	}
	
	/**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int count){
		if(count < 200 && (!stack.stackTagCompound.hasKey(CHARGED_STRING) || !stack.stackTagCompound.getBoolean(CHARGED_STRING))){
			if(EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0){
				stack.stackTagCompound.setBoolean(CHARGED_STRING, true);
			} else{
				if(player.inventory.hasItem(ClashOfKingdoms.boltItem.itemID)){
					player.inventory.consumeInventoryItem(ClashOfKingdoms.boltItem.itemID);
		    		stack.stackTagCompound.setBoolean(CHARGED_STRING, true);
				}
				
			}
			
		}
	}
	
	/**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return (par1ItemStack.stackTagCompound.hasKey(CHARGED_STRING) && par1ItemStack.stackTagCompound.getBoolean(CHARGED_STRING)) ? EnumAction.none : EnumAction.bow;
    }
    
    /**
     * used to cycle through icons based on their used duration, i.e. for the bow
     */
    public Icon getItemIconForUseDuration(int duration)
    {
        return this.itemIcon;
    }
	
	/**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return ticksToCharge;
    }
    
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

}
