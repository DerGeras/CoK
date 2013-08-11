package de.minestar.cok.hook;

import net.minecraftforge.event.ForgeSubscribe;
import de.minestar.cok.event.EventBlockBreak;
import de.minestar.cok.event.EventBlockPlace;

public class BlockListener {
	
	@ForgeSubscribe
	public void onBlockBreak(EventBlockBreak event){
		System.out.println("Block broke at " + event.blockX + " " + event.blockY  + " " +
				event.blockZ + " by " + event.player.username);
	}
	
	@ForgeSubscribe
	public void onBlockPlace(EventBlockPlace event){
		System.out.println("Block placed at " + event.blockX + " " + event.blockY  + " " +
				event.blockZ + " by " + event.player.username);
	}

}
