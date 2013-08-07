package de.minestar.cok.references;

public class Color {

	public static final char BLACK = 0;
	public static final char DARK_BLUE = 1;
	public static final char DARK_GREEN = 2;
	public static final char DARK_AQUA = 3;
	public static final char DARK_RED = 4;
	public static final char PURPLE = 5;
	public static final char GOLD = 6;
	public static final char GRAY = 7;
	public static final char DARK_GRAY = 8;
	public static final char BLUE = 9;
	public static final char GREEN = 'a';
	public static final char AQUA = 'b';
	public static final char RED = 'c';
	public static final char LIGHT_PURPLE = 'd';
	public static final char YELLOW = 'e';
	public static final char WHITE = 'f';
	
	public static char getColorFromString(String color){
		//SCREW YOU JAVA 1.5!!!!!!
		if(color.equalsIgnoreCase("gray")){
			return GRAY;
		}
		if(color.equalsIgnoreCase("blue")){
			return BLUE;
		}
		if(color.equalsIgnoreCase("red")){
			return RED;
		}
		return 'g';
	}
	
	public static String getColorCodeFromString(String color){
		char colorcode = getColorFromString(color);
		if(colorcode == 'g'){
			colorcode = 0;
		}
		return "\u00A7" + colorcode;
	}
	
}
