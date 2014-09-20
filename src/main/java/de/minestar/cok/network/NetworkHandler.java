package de.minestar.cok.network;

import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import de.minestar.cok.game.CoKGame;
import de.minestar.cok.network.message.MessageCompleteGameState;
import de.minestar.cok.network.message.MessageScore;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.PlayerHelper;

public class NetworkHandler{

	
	public static SimpleNetworkWrapper network;
	
	/**
	 * Initialize the network and register messages
	 */
	public static void preInit(){
		network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.CHANNEL_NAME);
		//register messages
		network.registerMessage(MessageCompleteGameState.class, MessageCompleteGameState.class, 0, Side.CLIENT);
		network.registerMessage(MessageScore.class, MessageScore.class, 1, Side.CLIENT);
	}
	
	/**
	 * Sends a message to all players of a specified game
	 * 
	 * @param game - target game
	 * @param message - message to be send
	 */
	public static void sendMessageToGame(CoKGame game, IMessage message){
		for(EntityPlayerMP player : PlayerHelper.getPlayerEntitiesForGame(game)){
			network.sendTo(message, player);
		}
	}

}
