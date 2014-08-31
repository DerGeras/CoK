package de.minestar.cok.client.gui;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import de.minestar.cok.handler.ConfigurationHandler;
import de.minestar.cok.reference.Reference;

public class CoKGuiConfig extends GuiConfig{

	public CoKGuiConfig(GuiScreen parentScreen) {
		super(  parentScreen,
				new ConfigElement(ConfigurationHandler.config.getCategory(ConfigurationHandler.CATEGORY_LOCAL_SETTINGS)).getChildElements(),
				Reference.MOD_ID,
				false,
				false,
				GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
	}

}
