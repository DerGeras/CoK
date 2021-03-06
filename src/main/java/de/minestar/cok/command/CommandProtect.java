package de.minestar.cok.command;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.ChatSendHelper;
import de.minestar.cok.worldguard.WorldRegion;
import de.minestar.cok.worldguard.Worldguard;

public class CommandProtect extends CoKCommand {
	
	public static HashMap<UUID, WorldRegion> startedProtections = new HashMap<UUID, WorldRegion>();

	@Override
	public String getCommandName() {
		return Reference.COMMAND_PROTECT;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return String.format("Usage: %s [add|clear] {{dimID for clear| x y z for add}}", getCommandName());
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length < 1){
			ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender));
			return;
		}
		if(args[0].equals("add") && args.length >= 1){
			EntityPlayer player = getCommandSenderAsPlayer(sender);
			WorldRegion region = startedProtections.get(player.getUniqueID());
			ChunkCoordinates coords;
			if(args.length == 4){
				coords = new ChunkCoordinates(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
			} else {
				coords = sender.getPlayerCoordinates();
			}
			if(region != null){
				startedProtections.remove(player.getUniqueID());
				region.setPosB(coords);
				Worldguard.addProtectedRegion(player.dimension, region);
				ChatSendHelper.sendMessageToPlayer(sender, String.format("Protecting area (%d %d %d) (%d %d %d)!", 
						region.getPosA().posX, region.getPosA().posY, region.getPosA().posZ, 
						coords.posX, coords.posY, coords.posZ));
			} else {
				startedProtections.put(player.getUniqueID(), new WorldRegion(coords));
				ChatSendHelper.sendMessageToPlayer(sender, String.format("Protection started at %d %d %d!", 
						coords.posX, coords.posY, coords.posZ));
			}
			return;
		}
		if(args[0].equals("clear")){
			if(args.length == 2){
				int dim = Integer.parseInt(args[1]);
				Worldguard.clearProtectedRegionsFromDim(dim);
				ChatSendHelper.sendMessageToPlayer(sender, String.format("Protection cleared from dimension %d!", dim));
			} else {
				Worldguard.clearProtectedRegions();
				ChatSendHelper.sendMessageToPlayer(sender, "Cleared all protected regions!");
			}
			return;
		}
		
		ChatSendHelper.sendMessageToPlayer(sender, getCommandUsage(sender));		

	}

	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender,
			String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		if(args.length <= 1){
			addIfPrefixMatches(list, args[0], "add", "clear");
		}
		if(args.length == 2){
			for(int dimId : Worldguard.protectedRegions.keySet()){
				list.add(String.valueOf(dimId));
			}
		}
		return list;
	}

}
