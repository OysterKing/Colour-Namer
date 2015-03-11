//Comhghall McKeating 12328401
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ColourCalculator {
	private HashMap<String, String> colour_map;
	
	public ColourCalculator(){
		String temp;
		colour_map = new HashMap<String, String>();
		try {
			FileReader colourFile = new FileReader("Colours.txt");
			@SuppressWarnings("resource")
			Scanner in = new Scanner(colourFile);
			while(in.hasNextLine()){
				temp = in.nextLine().toLowerCase();
				colour_map.put(temp.substring(0, temp.lastIndexOf('\t')), temp.substring(temp.lastIndexOf('\t') + 1));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getColourCode(String colourKey){
		String colour = colourKey + '\t' + colour_map.get(colourKey);
		return colour;
	}
	
	private int getRedValue(String rgb){
		String hexCode = rgb.substring(1);
		int red = Integer.parseInt(hexCode.substring(0, 2), 16);
		return red;
	}
	
	private int getGreenValue(String rgb){
		String hexCode = rgb.substring(1);
		int green = Integer.parseInt(hexCode.substring(2, 4), 16);
//		System.out.println(hexCode.substring(2, 4));
//		System.out.println(green);
		return green;
	}
	
	private int getBlueValue(String rgb){
		String hexCode = rgb.substring(1);
		int blue = Integer.parseInt(hexCode.substring(4, 6), 16);
//		System.out.println(hexCode.substring(4, 6));
//		System.out.println(blue);
		return blue;
	}
	
	public Vector getAllShadesOf(String colour){
		Vector shades = new Vector();
		int indexOfTab;
		Iterator j = colour_map.entrySet().iterator();
		while(j.hasNext()){
			Map.Entry<String, String> pairs = (Map.Entry<String, String>)j.next();
			indexOfTab = pairs.getKey().indexOf("\t");
			if(pairs.getKey().substring(indexOfTab + 1).equals(colour)){
				shades.add(pairs.getKey());
			}
		}
		return shades;
	}
	
	public Vector<String> getShadeOf(String colour){
		Vector shade = new Vector();
		int indexOfTab;
		Iterator j = colour_map.entrySet().iterator();
		while(j.hasNext() && shade.isEmpty()){
			Map.Entry<String, String> pairs = (Map.Entry<String, String>)j.next();
			indexOfTab = pairs.getKey().indexOf("\t");
			if(pairs.getKey().substring(indexOfTab + 1).equals(colour)){
				shade.add(pairs.getKey());
			}
		}
		return shade;
	}
	
	public Vector<String> getAllColoursOf(String shade){
		Vector colours = new Vector();
		int indexOfTab;
		Iterator j = colour_map.entrySet().iterator();
		while(j.hasNext()){
			Map.Entry<String, String> pairs = (Map.Entry<String, String>)j.next();
			indexOfTab = pairs.getKey().indexOf("\t");
			if(pairs.getKey().substring(0, indexOfTab).equals(shade)){
				colours.add(pairs.getKey());
			}
		}
		return colours;
	}
	
	public String blend(String colourKey1, String colourKey2, double weightOfMix){
		int red, green, blue;
		String redHex, greenHex, blueHex;
		double weight = weightOfMix;
		String hexCode1, hexCode2, newHexCode;
		
		hexCode1 = colour_map.get(colourKey1);
		hexCode2 = colour_map.get(colourKey2);
		
		red = (int) (weight * getRedValue(hexCode1) + (1 - weight) * getRedValue(hexCode2));
		green = (int) (weight * getGreenValue(hexCode1) + (1 - weight) * getGreenValue(hexCode2));
		blue = (int) (weight * getBlueValue(hexCode1) + (1 - weight) * getBlueValue(hexCode2));
		redHex = Integer.toHexString(red);
		greenHex = Integer.toHexString(green);
		blueHex = Integer.toHexString(blue);
		
		if(red < 16){
			redHex = '0' + Integer.toHexString(red);
		}
		
		if(green < 16){
			greenHex = '0' + Integer.toHexString(green);
		}
		
		if(blue < 16){
			blueHex = '0' + Integer.toHexString(blue);
		}
		
		newHexCode = '#' + redHex + greenHex + blueHex;
		
		return newHexCode;
	}
	
	public int distance(String hexCode1, String hexCode2){
		int square1, square2, square3;
		
		square1 = (int) Math.pow((getRedValue(hexCode1) - getRedValue(hexCode2)), 2);
		square2 = (int) Math.pow((getGreenValue(hexCode1) - getGreenValue(hexCode2)), 2);
		square3 = (int) Math.pow((getBlueValue(hexCode1) - getBlueValue(hexCode2)), 2);
		
		return (int) Math.sqrt(square1 + square2 + square3);
		
	}
	
//	public static void main(String[] args) {
//		ColourCalculator colourCalc = new ColourCalculator();
//		System.out.println(colourCalc.getColourCode("apple" + '\t' + "red"));
//		System.out.println(colourCalc.getColourCode("banana" + '\t' + "yellow"));
//		System.out.println("Mix = " + colourCalc.blend("shamrock" + '\t' + "green", "park" + '\t' + "green", 0.7));
//		System.out.println("Distance = " + colourCalc.distance("#7FDD4C", "#C3C728"));
//		Iterator i = colourCalc.getAllShadesOf("red").iterator();
//		while(i.hasNext()){
//			System.out.println(colourCalc.getColourCode(i.next().toString()));
//		}
//		System.out.println(colourCalc.getColourCode("oven" + '\t' + "brown"));
//		Iterator j = colourCalc.getAllColoursOf("shit").iterator();
//		while(j.hasNext()){
//			System.out.println(j.next());
//		}
//	}
}
