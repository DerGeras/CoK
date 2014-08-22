package de.minestar.cok.game;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

import cpw.mods.fml.client.config.GuiCheckBox;
import cpw.mods.fml.client.config.GuiConfig;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.Constants.NBT;
import de.minestar.cok.game.profession.Profession;
import de.minestar.cok.util.PlayerHelper;

public class CoKPlayer {

	private UUID uuid;
	private Team team;
	private Profession profession;
	
	public CoKPlayer(UUID uuid){
		this.uuid = uuid;
	}
	
	public CoKPlayer(NBTTagCompound compound){
		readFromNBT(compound);
	}
	
	public CoKPlayer(ByteBuf buf){
		readFromBuffer(buf);
	}
	
	public void setTeam(Team team){
		this.team = team;
	}
	
	public EntityPlayerMP getPlayerEntity(){
		return PlayerHelper.getPlayerForUUID(uuid);
	}
	
	public String getUserName(){
		return PlayerHelper.getNameForUUID(uuid);
	}
	
	public UUID getUUID(){
		return uuid;
	}
	
	public Team getTeam(){
		return team;
	}
	
	public void setProfession(Profession profession){
		this.profession = profession;
	}
	
	public Profession getProfession(){
		return profession;
	}
	
	public CoKGame getGame(){
		if(team != null){
			return team.getGame();
		}
		return null;
	}
	
	public void readFromNBT(NBTTagCompound compound){
		//read uuid
		this.uuid = UUID.fromString(compound.getString("uuid"));
	}
	
	public void writeToNBT(NBTTagCompound compound){
		//write uuid
		compound.setString("uuid", uuid.toString());
	}
	
	
	public void writeToBuffer(ByteBuf buf){
		//write uuid
		String uuidString = uuid.toString();
		buf.writeInt(uuidString.length());
		buf.writeBytes(uuidString.getBytes());
	}
	
	public void readFromBuffer(ByteBuf buf){
		//read uuid
		int uuidStringLength = buf.readInt();
		this.uuid = UUID.fromString(new String(buf.readBytes(uuidStringLength).array()));
	}
	
}
