package de.minestar.cok.listener;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.game.CoKPlayer;
import de.minestar.cok.game.CoKPlayerRegistry;
import de.minestar.cok.game.profession.Profession;
import de.minestar.cok.network.NetworkHandler;
import de.minestar.cok.network.message.MessageCompleteGameState;
import de.minestar.cok.util.ItemStackHelper;
import de.minestar.cok.util.PlayerHelper;

public class PlayerTracker {
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event){
		PlayerHelper.clearGivenItemsFromInventory(event.player); //hotfix for unconventional situations
		CoKPlayer player = CoKPlayerRegistry.getOrCreatPlayerForUUID(event.player.getUniqueID());
		if(player.getTeam() != null){
			player.getTeam().playerJoined(player);
		}
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
			NetworkHandler.network.sendTo(new MessageCompleteGameState(), (EntityPlayerMP) event.player);
		}
	}
	
	@SubscribeEvent
	public void onPlayerLogout(PlayerLoggedOutEvent event){
		CoKPlayer player = CoKPlayerRegistry.getOrCreatPlayerForUUID(event.player.getUniqueID());
		if(player.getTeam() != null){
			player.getTeam().playerLeft(player);
		}
	}
	
	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event){
		if(event.entityLiving instanceof EntityPlayer){
			PlayerHelper.clearGivenItemsFromInventory((EntityPlayer) event.entityLiving);
			CoKPlayer player = CoKPlayerRegistry.getPlayerForUUID(event.entityLiving.getUniqueID());
			if(player != null && player.getGame() != null){
				player.getGame().punishTeam(player.getTeam(), player.getProfession());
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event){
		CoKPlayer player = CoKPlayerRegistry.getOrCreatPlayerForUUID(event.player.getUniqueID());
		if(player.getTeam() != null){
			player.getTeam().playerJoined(player);
		}
	}
	
	@SubscribeEvent
	public void onPlayerTossItem(ItemTossEvent event){
		ItemStack stack = event.entityItem.getEntityItem();
		if(ItemStackHelper.isGiven(stack)){
			event.setCanceled(true);
			event.player.inventory.addItemStackToInventory(stack);
		}
	}
	
	@SubscribeEvent
	public void onPlayerDeathDrops(PlayerDropsEvent event){
		//drop head
		CoKPlayer player = CoKPlayerRegistry.getPlayerForUUID(event.entityPlayer.getUniqueID());
		if(player.getProfession() != null && player.getProfession() == Profession.king){
			ItemStack headStack = new ItemStack(Items.skull);
			headStack.setItemDamage(3); //Skulltype head
			NBTTagCompound headTag = headStack.getTagCompound();
			if(headTag == null){
				headStack.setTagCompound(new NBTTagCompound());
				headTag = headStack.getTagCompound();
			}
			headTag.setString("SkullOwner", event.entityPlayer.getCommandSenderName());
			event.drops.add(new EntityItem(event.entityPlayer.worldObj, event.entityPlayer.posX,
					event.entityPlayer.posY, event.entityPlayer.posZ, headStack));
		}
		if(player.getTeam() != null){
			player.getTeam().playerLeft(player);
		}
	}
	
	@SubscribeEvent
	public void onPlayerAttack(LivingAttackEvent event){
		if(event.entityLiving instanceof EntityPlayer){
			CoKPlayer player = CoKPlayerRegistry.getPlayerForUUID(event.entityLiving.getUniqueID());
			if(player != null){
				if(player.getGame() == null || !player.getGame().isRunning()){
					event.setCanceled(true);
				}
			}
		}
		if(event.source.getEntity() instanceof EntityPlayer){
			if(((EntityPlayer) event.source.getEntity()).capabilities.isCreativeMode){
				return;
			}
			CoKPlayer player = CoKPlayerRegistry.getPlayerForUUID(event.source.getEntity().getUniqueID());
			if(player != null){
				if(player.getGame() == null || !player.getGame().isRunning()){
					event.setCanceled(true);
				}
			}
		}
	}

}
