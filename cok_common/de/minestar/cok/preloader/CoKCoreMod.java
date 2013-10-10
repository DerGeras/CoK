/*******************************************************************************
 * Clash of Kingdoms Minecraft Mod
 *     Copyright (C) 2013 Geras
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package de.minestar.cok.preloader;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class CoKCoreMod implements IFMLLoadingPlugin, IFMLCallHook {
	
	public static File	location;

	@Override
	public String[] getLibraryRequestClass() {
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		String[] res = { "de.minestar.cok.preloader.asm.CoKEventAdder" };
		return res;
	}

	@Override
	public String getModContainerClass() {
		return "de.minestar.cok.preloader.CoKCoreModContainer";
	}

	@Override
	public String getSetupClass() {
		return "de.minestar.cok.preloader.CoKCoreMod";
	}

	@Override
	public void injectData(Map<String, Object> data) {
		if (data.containsKey("coremodLocation"))
		{
			location = (File) data.get("coremodLocation");
		}
		
	}

	
	//Needed by RelaunchLibraryManager.handleLaunch
	@Override
	public Void call() throws Exception {
		return null;
	}

}
