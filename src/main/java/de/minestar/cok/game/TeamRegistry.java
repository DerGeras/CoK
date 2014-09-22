package de.minestar.cok.game;

import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.HashSet;

import de.minestar.cok.game.worlddata.CoKGameWorldData;
import de.minestar.cok.util.LogHelper;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class TeamRegistry {

	private static HashMap<String, Team> registeredTeams = new HashMap<String, Team>();
	
	public static HashSet<Team> getAllTeams(){
		return new HashSet<Team>(registeredTeams.values());
	}
	
	public static void addTeam(String name, char color){
		addTeam( new Team(name, color));
	}
	
	public static void addTeam(Team team){
		CoKGameWorldData.markDataDirty();
		registeredTeams.put(team.getName(), team);
	}
	
	public static void removeTeam(String name){
		CoKGameWorldData.markDataDirty();
		Team team = registeredTeams.get(name);
		if(team != null){
			if(team.getGame() != null){
				team.getGame().removeTeam(name);
			}
			team.clearPlayers();
			registeredTeams.remove(name);
		}
	}
	
	public static Team getTeam(char color){
		for(Team team : registeredTeams.values()){
			if(team.getColor() == color){
				return team;
			}
		}
		return null;
	}
	


	public static Team getTeam(int color) {
		for(Team team : registeredTeams.values()){
			if(team.getColorAsInt() == color){
				return team;
			}
		}
		return null;
	}
	
	public static Team getTeam(String name){
		return registeredTeams.get(name);
	}
	
	public static void clearTeams(){
		CoKGameWorldData.markDataDirty();
		for(Team team : registeredTeams.values()){
			team.clearPlayers();
			if(team.getGame() != null){
				team.getGame().removeTeam(team.getName());
			}
		}
		registeredTeams.clear();
	}
	
	public static void readFromNBT(NBTTagCompound compound){
		clearTeams();
		NBTTagList teamList = compound.getTagList("teams", NBT.TAG_COMPOUND);
		for(int i = 0; i < teamList.tagCount(); i++){
			NBTTagCompound teamCompound = teamList.getCompoundTagAt(i);
			addTeam(new Team(teamCompound));
		}
	}
	
	public static void writeToNBT(NBTTagCompound compound){
		NBTTagList teamList = new NBTTagList();
		for(Team team : registeredTeams.values()){
			NBTTagCompound teamCompound = new NBTTagCompound();
			team.writeToNBT(teamCompound);
			teamList.appendTag(teamCompound);
		}
		compound.setTag("teams", teamList);
	}
	
	public static void readFromBuffer(ByteBuf buf){
		clearTeams();
		int teamCount = buf.readInt();
		for(int i = 0; i < teamCount; i++){
			addTeam(new Team(buf));
		}
	}
	
	public static void writeToBuffer(ByteBuf buf){
		buf.writeInt(registeredTeams.size());
		for(Team team : registeredTeams.values()){
			team.writeToBuffer(buf);
		}
	}
	
}
