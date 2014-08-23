package de.minestar.cok.reference;

public class Reference {
	
	public static final String MOD_ID = "CoK";
	public static final String MOD_NAME = "Clash of Kingdoms";
	public static final String VERSION = "0.7.0-MC.1.7.10";
	public static final String MOD_URL = "http://www.minestar.de";
	public static final String CHANNEL_NAME = "cok";
	public static final String[] AUTHOR_LIST = new String[] { "Geras" };
	public static final String CLIENT_PROXY_CLASS = "de.minestar.cok.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "de.minestar.cok.proxy.ServerProxy";
	public static final String GUI_FACTORY_CLASS = "de.minestar.cok.client.gui.GuiFactory";
	
	//Preloader Stuff
	public static final String CORE_MOD_ID = "CoKPreloader";
	public static final String CORE_MOD_NAME = "Clash of Kingdoms|Preloader";
	public static final String CORE_MOD_DESCRIPTION = "Sets up Events and stuff for Clash of Kingdoms";
	public static final String CORE_MOD_PARENT = MOD_ID;
	
	
	//COMMANDS
	public static final String COMMAND_COK = "cok";
	public static final String COMMAND_TEAM = "team";
	public static final String COMMAND_PLAYER = "player";
	public static final String COMMAND_SET_SPAWN = "setspawn";
	
	//NBT Tags
	public static final String GIVEN_ITEM = "given";
}
