package de.minestar.cok.preloader;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class CoKPreloader implements IFMLLoadingPlugin, IFMLCallHook{

	@Override
	public String[] getASMTransformerClass() {
		return PreloaderReference.transformers;
	}

	@Override
	public String getModContainerClass() {
		return PreloaderReference.MOD_CONTAINER_CLASS;
	}

	@Override
	public String getSetupClass() {
		return PreloaderReference.PRELOADER_CLASS;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getAccessTransformerClass() {
		return PreloaderReference.TRANSFORMER_CLASS;
	}

	//FMl wants to cast this class to IFMLHook for whatever reason :D
	@Override
	public Void call() throws Exception {
		return null;
	}

}
