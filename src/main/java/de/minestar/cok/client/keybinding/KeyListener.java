package de.minestar.cok.client.keybinding;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import de.minestar.cok.CoK;
import de.minestar.cok.client.gui.CoKGUIInfo;
import de.minestar.cok.reference.Reference;
import de.minestar.cok.util.LogHelper;

public class KeyListener {
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event){
		if(KeyBindings.keyGUIInfo.isPressed()){
			LogHelper.info("Key pressed" + KeyBindings.keyGUIInfo.isPressed());
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			Minecraft.getMinecraft().thePlayer.openGui(CoK.instance,
					Reference.GUI_INFO_ID, player.worldObj,
					(int)player.posX, (int)player.posY, (int)player.posZ);
		}
	}

}
