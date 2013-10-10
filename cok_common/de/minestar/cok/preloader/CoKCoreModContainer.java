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

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import de.minestar.cok.references.Reference;

public class CoKCoreModContainer extends DummyModContainer {

	public CoKCoreModContainer(){
		super(new ModMetadata());
		ModMetadata myMeta = super.getMetadata();
		myMeta.authorList = Arrays.asList(new String[]
		{ "Geras" });
		myMeta.description = "Sets up some basic Events for CoK";
		myMeta.modId = "CoKCoreMod";
		myMeta.version = Reference.MOD_VERSION;
		myMeta.name = "Clash of Kingdoms Core Mod";
		myMeta.url = "";
	}
	
	@Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
		bus.register(this);
        return true;
    }
	
}
