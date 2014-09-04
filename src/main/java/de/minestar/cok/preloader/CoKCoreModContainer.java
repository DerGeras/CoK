package de.minestar.cok.preloader;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import de.minestar.cok.reference.Reference;

public class CoKCoreModContainer extends DummyModContainer {
	
	public CoKCoreModContainer(){
		super(new ModMetadata());
		ModMetadata meta = super.getMetadata();
		meta.modId = Reference.CORE_MOD_ID;
		meta.name = Reference.CORE_MOD_NAME;
		meta.version = Reference.VERSION;
		meta.url = Reference.MOD_URL;
		meta.parent = Reference.CORE_MOD_PARENT;
		meta.description = Reference.CORE_MOD_DESCRIPTION;
		meta.authorList = Arrays.asList(Reference.AUTHOR_LIST);
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller){
		bus.register(this);
		return true;
	}
	
}
