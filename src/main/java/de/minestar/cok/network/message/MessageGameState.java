package de.minestar.cok.network.message;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageGameState implements IMessage, IMessageHandler<MessageGameState, IMessage> {

	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub

	}

	@Override
	public IMessage onMessage(MessageGameState message, MessageContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
