package de.minestar.cok.references;

public class Color {

	public static final char BLACK = '0';
	public static final char DARK_BLUE = '1';
	public static final char DARK_GREEN = '2';
	public static final char DARK_AQUA = '3';
	public static final char DARK_RED = '4';
	public static final char PURPLE = '5';
	public static final char GOLD = '6';
	public static final char GRAY = '7';
	public static final char DARK_GRAY = '8';
	public static final char BLUE = '9';
	public static final char GREEN = 'a';
	public static final char AQUA = 'b';
	public static final char RED = 'c';
	public static final char LIGHT_PURPLE = 'd';
	public static final char YELLOW = 'e';
	public static final char WHITE = 'f';
	
	public static char getColorFromString(String color){
		//SCREW YOU JAVA 1.5!!!!!!
		if(color.equalsIgnoreCase("black")){
			return BLACK;
		}
		if(color.equalsIgnoreCase("darkblue")){
			return DARK_BLUE;
		}
		if(color.equalsIgnoreCase("darkgreen")){
			return DARK_GREEN;
		}
		if(color.equalsIgnoreCase("darkaqua")){
			return DARK_AQUA;
		}
		if(color.equalsIgnoreCase("darkred")){
			return DARK_RED;
		}
		if(color.equalsIgnoreCase("purple")){
			return PURPLE;
		}
		if(color.equalsIgnoreCase("gold")){
			return GOLD;
		}
		if(color.equalsIgnoreCase("gray")){
			return GRAY;
		}
		if(color.equalsIgnoreCase("darkgray")){
			return DARK_GRAY;
		}
		if(color.equalsIgnoreCase("blue")){
			return BLUE;
		}
		if(color.equalsIgnoreCase("green")){
			return GREEN;
		}
		if(color.equalsIgnoreCase("aqua")){
			return AQUA;
		}
		if(color.equalsIgnoreCase("red")){
			return RED;
		}
		if(color.equalsIgnoreCase("lightpurple")){
			return LIGHT_PURPLE;
		}
		if(color.equalsIgnoreCase("yellow")){
			return YELLOW;
		}
		if(color.equalsIgnoreCase("white")){
			return WHITE;
		}
		return 'g';
	}
	
	public static String getColorCodeFromChar(char color){
		if(color == 'g'){
			color = 'f';
		}
		return "\u00A7" + color;
	}
	
	public static String getColorCodeFromString(String color){
		char colorcode = getColorFromString(color);
		return getColorCodeFromChar(colorcode);
	}
	
}
