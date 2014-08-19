package de.minestar.cok.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.network.message.MessageGameState;
import de.minestar.cok.reference.Reference;

public class NetworkHandler{

	
	public static SimpleNetworkWrapper network;
	
	public static void preInit(){
		network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.CHANNEL_NAME);
		//register messages
		network.registerMessage(MessageGameState.class, MessageGameState.class, 0, Side.CLIENT);
	}

}
