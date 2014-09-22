package de.minestar.cok.util;

import de.minestar.cok.reference.Reference;

public class PathHelper {

	public static String getPathForModel(String name){
		return String.format("models/%s", name);
	}
	
	public static String getPathForGUI(String name){
		return String.format("gui/%s", name);
	}
	
}
