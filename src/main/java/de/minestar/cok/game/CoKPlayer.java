package de.minestar.cok.game;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import com.sun.istack.internal.Nullable;

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
	
	/**
	 * Returns the player entity corresponding to this Player.
	 * 
	 * @return
	 */
	@Nullable
	public EntityPlayerMP getPlayerEntity(){
		return PlayerHelper.getPlayerForUUID(uuid);
	}
	
	public String getUserName(){
		return PlayerHelper.getNameForUUID(uuid);
	}
	
	public UUID getUUID(){
		return uuid;
	}
	
	@Nullable
	public Team getTeam(){
		return team;
	}
	
	public void setProfession(Profession profession){
		this.profession = profession;
	}
	
	@Nullable
	public Profession getProfession(){
		return profession;
	}
	
	@Nullable
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
