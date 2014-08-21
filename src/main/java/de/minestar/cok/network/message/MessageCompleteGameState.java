package de.minestar.cok.network.message;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.minestar.cok.game.CoKGameRegistry;

public class MessageCompleteGameState implements IMessage, IMessageHandler<MessageCompleteGameState, IMessage> {

	@Override
	public void fromBytes(ByteBuf buf) {
		//NBTTagCompound compound = new NBTTagCompound();
		//CoKGameRegistry.writeToNBT(compound);		
		//TODO yay for redundant stuff...
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub

	}

	@Override
	public IMessage onMessage(MessageCompleteGameState message, MessageContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
