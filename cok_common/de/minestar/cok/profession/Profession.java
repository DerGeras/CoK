package de.minestar.cok.profession;

import net.minecraft.entity.player.EntityPlayer;
import de.minestar.cok.game.Team;

public abstract class Profession {

	private String name = "";
	
	public Profession(String name){
		this.name = name;
	}
	
	/**
	 * give the player the kit for the profession
	 * @param player
	 */
	public abstract void giveKit(EntityPlayer player, Team team);
	
	/**
	 * returns the % of blocks that increase in case of a player death.
	 * @return
	 */
	public int getPunishment(){
		return 0;
	}
	
	/**
	 * @return the className
	 */
	public String getClassName() 
	{
		return name;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String name) 
	{
		this.name = name;
	}
}
