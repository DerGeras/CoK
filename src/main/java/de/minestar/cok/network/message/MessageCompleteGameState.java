package de.minestar.cok.network.message;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.minestar.cok.game.CoKGameRegistry;
import de.minestar.cok.util.LogHelper;

public class MessageCompleteGameState implements IMessage, IMessageHandler<MessageCompleteGameState, IMessage> {
	
	public MessageCompleteGameState(){
		//default constructor must be present
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		CoKGameRegistry.readFromBuffer(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		CoKGameRegistry.writeToBuffer(buf);
	}

	@Override
	public IMessage onMessage(MessageCompleteGameState message, MessageContext ctx) {
		//Changes occour in "fromBytes".
		LogHelper.info("Received game package data from server.");
		return null;
	}

}
