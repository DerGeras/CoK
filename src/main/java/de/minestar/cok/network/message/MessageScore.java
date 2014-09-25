package de.minestar.cok.network.message;

import java.util.Collections;
import java.util.HashSet;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.minestar.cok.client.gui.overlay.CoKGuiOverlay;
import de.minestar.cok.game.ScoreContainer;
import de.minestar.cok.util.LogHelper;

public class MessageScore implements IMessage, IMessageHandler<MessageScore, IMessage> {
	
	private HashSet<ScoreContainer> localScores = new HashSet<ScoreContainer>();
	private String localGameName;
	
	public MessageScore() {
		//default constructor must be present
	}
	
	public MessageScore(String gameName, HashSet<ScoreContainer> scores){
		localGameName = gameName;
		localScores.clear();
		localScores.addAll(scores);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		localScores.clear();
		int gameNameLength = buf.readInt();
		localGameName = new String(buf.readBytes(gameNameLength).array());
		int scoreCount = buf.readInt();
		for(int i = 0; i < scoreCount; i++){
			localScores.add(new ScoreContainer(buf));
		}

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(localGameName.length());
		buf.writeBytes(localGameName.getBytes());
		buf.writeInt(localScores.size());
		for(ScoreContainer score : localScores){
			score.toBytes(buf);
		}
		localScores.clear();
	}

	@Override
	public IMessage onMessage(MessageScore message, MessageContext ctx) {
		//Changes occour in "fromBytes".
		//LogHelper.info("Received score package data from server.");
		CoKGuiOverlay.gameName = message.localGameName;
		CoKGuiOverlay.scores.clear();
		CoKGuiOverlay.scores.addAll(message.localScores);
		Collections.sort(CoKGuiOverlay.scores);
		return null;
	}

}
