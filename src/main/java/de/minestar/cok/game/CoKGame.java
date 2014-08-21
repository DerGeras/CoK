package de.minestar.cok.game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.minestar.cok.game.worlddata.CoKGameWorldData;
import de.minestar.cok.util.ChatSendHelper;

public class CoKGame {

	private HashMap<String, Team> teams = new HashMap<String, Team>();
	
	private String name;
	private boolean isRunning = false;
	
	public CoKGame(String name){
		this.name = name;
	}
	
	public CoKGame(NBTTagCompound compound){
		readFromNBT(compound);
	}
	
	public void addTeam(Team team){
		if(CoKGameWorldData.data != null){
			CoKGameWorldData.data.markDirty();
		}
		if(team != null){
			team.setGame(this);
			teams.put(team.getName(), team);
		}
	}
	
	public void removeTeam(Team team){
		teams.remove(team.getName());
	}
	
	public void removeTeam(String name){
		if(CoKGameWorldData.data != null){
			CoKGameWorldData.data.markDirty();
		}
		teams.remove(name);
	}
	
	public Team getTeam(int color){
		for(Team team : teams.values()){
			if(team.getColorAsInt() == color){
				return team;
			}
		}
		return null;
	}
	
	public Team getTeam(String name){
		return teams.get(name);
	}
	
	public Collection<Team> getAllTeams(){
		return teams.values();
	}
	
	public Collection<String> getAllTeamNames(){
		return teams.keySet();
	}
	
	public boolean isRunning(){
		return isRunning;
	}
	
	public String getName(){
		return name;
	}
	
	@SideOnly(Side.SERVER)
	public void startGame(){
		ChatSendHelper.broadCastError("Winter is comming!");
		ChatSendHelper.broadCastMessage("Started the game " + name +  "!");
		isRunning = true;
	}
	
	@SideOnly(Side.SERVER)
	public void stopGame(){
		ChatSendHelper.broadCastError("The game " + name + " has ended!");
		isRunning = false;
	}
	
	public void readFromNBT(NBTTagCompound compound){
		this.name = compound.getString("name");
		NBTTagList teamList = compound.getTagList("teams", NBT.TAG_COMPOUND);
		for(int i = 0; i < teamList.tagCount(); i++){
			NBTTagCompound teamCompound = teamList.getCompoundTagAt(i);
			Team team = new Team(teamCompound);
			addTeam(team);
		}
	}
	
	public void writeToNBT(NBTTagCompound compound){
		compound.setString("name", name);
		NBTTagList teamList = new NBTTagList();
		for(Team team : teams.values()){
			NBTTagCompound teamCompound = new NBTTagCompound();
			team.writeToNBT(teamCompound);
			teamList.appendTag(teamCompound);
		}
		compound.setTag("teams", teamList);
	}
	
}
