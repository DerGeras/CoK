package de.minestar.cok.preloader;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import de.minestar.cok.references.Reference;

public class CoKCoreModContainer extends DummyModContainer {

	public CoKCoreModContainer() {
		super(new ModMetadata());
		ModMetadata myMeta = super.getMetadata();
		myMeta.authorList = Arrays.asList(new String[]{"Geras"});
		myMeta.description = "Sets up some basic Events for CoK";
		myMeta.modId = "CoKCoreMod";
		myMeta.version = Reference.MOD_VERSION;
		myMeta.name = "Clash of Kingdoms Core Mod";
		myMeta.url = "";
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}

}
