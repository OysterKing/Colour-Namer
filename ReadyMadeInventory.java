//Comhghall McKeating 12328401
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Vector;


public class ReadyMadeInventory {
	private HashMap<String, Vector<String>> readyMades = new HashMap<String, Vector<String>>();
	private ColourCalculator colourMap;
	
	public ReadyMadeInventory(ColourCalculator colours){
		colourMap = colours;
		loadBigrams();
		loadUnigrams();
	}
	
	public HashMap<String, Vector<String>> getReadyMades(){
		return readyMades;
	}
	
	private void loadBigrams(){
		String temp;
		Vector<String> codes = new Vector<String>();
		Vector<String> modColours = new Vector<String>();
		Vector<String> headColours = new Vector<String>();
		try {
			FileReader colourFile = new FileReader("Unbracketed_Colour_Bigrams.txt");
			@SuppressWarnings("resource")
			Scanner in = new Scanner(colourFile);
			
			while(in.hasNextLine()){
				temp = in.nextLine();
				temp = temp.toLowerCase();
				modColours = colourMap.getAllColoursOf(temp.substring(0, temp.indexOf('\t')));
				headColours = colourMap.getAllColoursOf(temp.substring(temp.indexOf('\t') + 1));
				codes = createRGBCodes(modColours, headColours);
				readyMades.put(temp, codes);
			}
			in.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void loadUnigrams(){
		String temp;
		Vector<String> codes = new Vector<String>();
		Vector<String> modColours = new Vector<String>();
		Vector<String> headColours = new Vector<String>();
		int i = 1;
		try {
			FileReader colourFile = new FileReader("Colour_Unigrams.txt");
			@SuppressWarnings("resource")
			Scanner in = new Scanner(colourFile);
			while(in.hasNextLine()){
				temp = in.nextLine();
				temp = temp.toLowerCase();
				while(modColours.isEmpty() || headColours.isEmpty()){
					modColours = colourMap.getAllColoursOf(temp.substring(0, i));
					headColours = colourMap.getAllColoursOf(temp.substring(i));
					if(headColours.isEmpty()){
						headColours = colourMap.getShadeOf(temp.substring(i));
					}
					i++;
				}
				
				i = 0;
				codes = createRGBCodes(modColours, headColours);
				modColours.clear();
				headColours.clear();
				readyMades.put(temp, codes);
			}
			
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Vector<String> createRGBCodes(Vector<String> modColours, Vector<String> headColours){
		Vector<String> rgbCodes = new Vector<String>();
		double weight;
		String[] modColourArray = (String[]) modColours.toArray(new String[modColours.size()]);
		String[] headColourArray = (String[]) headColours.toArray(new String[headColours.size()]);
		
		for(int i = 0; i < modColourArray.length; i++){
			for(int j = 0; j < headColourArray.length; j++){
				weight = linguisticRules(modColourArray[i], headColourArray[j]);
				rgbCodes.add(colourMap.blend(modColourArray[i], headColourArray[j], weight));
			}
		}
		return rgbCodes;
	}
	
	private double linguisticRules(String modifier, String head){
		double weight = 0.5;
		if(!colourMap.getAllColoursOf(head).isEmpty()){ //check if head is colour i.e. white, blue etc
			weight = 0.35;
		}
		return weight;
	}
	
	public static void main(String args[]){
		ColourCalculator colourCatalogue = new ColourCalculator();
		ReadyMadeInventory inventory = new ReadyMadeInventory(colourCatalogue);
		String temp = "";
		Iterator i = inventory.readyMades.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry<String, Vector<String>> pairs = (Map.Entry<String, Vector<String>>)i.next();
			for(int j = 0; j < pairs.getValue().size(); j++){
				temp += pairs.getValue().elementAt(j) + "\t";
			}
			System.out.println(pairs.getKey() + "\t\t\t" + temp);
			temp = "";
		}
	}
}
