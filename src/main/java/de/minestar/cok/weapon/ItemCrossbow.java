package de.minestar.cok.weapon;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import de.minestar.cok.entity.EntityBolt;
import de.minestar.cok.init.ModItems;
import de.minestar.cok.item.CoKItem;
import de.minestar.cok.util.LogHelper;

public class ItemCrossbow extends CoKItem {

	private static int ticksToCharge = 2 * 20 + 200;
	
	public static final String CHARGED_STRING = "charged";
	public static final String CLIENT_CHARGED = "clientcharged";
	
	public ItemCrossbow(){
		super();
		setUnlocalizedName("crossbow");
		setFull3D(); //needed for 3D Items
	}
	
	 /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return ticksToCharge;
    }
    
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
    	
    	boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
		
		if(stack.stackTagCompound == null){
			stack.setTagCompound(new NBTTagCompound());
		}
		
		if(flag || stack.stackTagCompound.getBoolean(CHARGED_STRING)){
			//shoot the bolt
            EntityBolt entityBolt = new EntityBolt(world, player, 2.0F);
            
            entityBolt.setIsCritical(true);

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);

            if (k > 0)
            {
                entityBolt.setDamage(entityBolt.getDamage() + (double)k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

            if (l > 0)
            {
                entityBolt.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
            {
                entityBolt.setFire(100);
            }

            stack.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);

            if (flag)
            {
                entityBolt.canBePickedUp = 2;
            }

            if (!world.isRemote)
            {
                world.spawnEntityInWorld(entityBolt);
            }
            stack.getTagCompound().setBoolean(CHARGED_STRING, false);
            stack.getTagCompound().setBoolean(CLIENT_CHARGED, false);
		} else {
			//start charging
			if(player.inventory.hasItem(ModItems.bolt)){
            	player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        	}
		}
		
    	return stack;
    }
    
    /**
	 * Called while using the item
	 */
    @Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count){
		super.onUsingTick(stack, player, count);
		if(count < 200){
			stack.stackTagCompound.setBoolean(CLIENT_CHARGED, true);
		}
	}
    
    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int itemInUseCount){
    	if(itemInUseCount < 200 && (!stack.stackTagCompound.getBoolean(CHARGED_STRING))){
			if(EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0){
				stack.stackTagCompound.setBoolean(CHARGED_STRING, true);
			} else{
				if(player.inventory.hasItem(ModItems.bolt)){
					player.inventory.consumeInventoryItem(ModItems.bolt);
		    		stack.stackTagCompound.setBoolean(CHARGED_STRING, true);
				}
				
			}
			
		}
    }
    
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_){
        return EnumAction.bow;
    }
	
}
