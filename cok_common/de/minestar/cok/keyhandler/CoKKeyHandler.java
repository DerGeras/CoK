package de.minestar.cok.keyhandler;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import de.minestar.cok.ClashOfKingdoms;
import de.minestar.cok.references.Reference;

public class CoKKeyHandler extends KeyHandler{
	
	private EnumSet<TickType> tickTypes = EnumSet.of(TickType.CLIENT);

	public CoKKeyHandler(KeyBinding[] keyBindings, boolean[] repeatings) {
		super(keyBindings, repeatings);
	}

	@Override
    public String getLabel()
    {
            return "CoKKey";
    }

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,
			boolean tickEnd, boolean isRepeat) {
		//Check whether we are in a gui
		if (FMLClientHandler.instance().getClient().inGameHasFocus) {
			//Menu key
			if(kb.keyDescription.equals(Reference.CoKMenuKey)){
				EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
				if(player != null && (!MinecraftServer.getServer().isDedicatedServer()
						|| MinecraftServer.getServer().getConfigurationManager().getOps().contains(player.username.toLowerCase().trim()))){
					player.openGui(ClashOfKingdoms.instance, Reference.COK_GUI_ID, player.worldObj,
							(int) player.posX, (int) player.posY, (int) player.posZ);
				}
			}
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		//nothing
	}

	@Override
	public EnumSet<TickType> ticks() {
		return tickTypes;
	}
	
}
