import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MadlibsModel {
	
	private HashMap<String, String> wordsDataSet = new HashMap<String, String>();
	private HashMap<String, String> toBeReplacedInMadLib = new HashMap<String, String>();
	private String madLibPhrase;
	
	
	public MadlibsModel() throws Exception {
		initmadLibPhrase();
		initToBeReplacedInMadLib();
		initWordsDataSet();
	}
	
	
	/**
	 * 
	 * @author Ryan Luu 
	 * 
	 * initToBeReplcaedInMadLib grabs the segments in the MadLibs quote surrounded by ( ) and maps them out 
	 * to the toBeReplcaedInMadLib HashMap. 
	 * 
	 * Ex) (1: ADJ) ---> {1:ADJ"}
	 * 
	 */
	private void initToBeReplacedInMadLib() {
		ArrayList<String> targetKeyValues = new ArrayList<String>();
		ArrayList<String[]> readytoPutInHashMapLines = new ArrayList<String[]>();
		Pattern regex = Pattern.compile("\\((.*?)\\)");  // Using Regular Expressions because I'm lazy
		Matcher regexMatcher = regex.matcher(madLibPhrase);
		
		// Finds all the values between ( and ) and adds them to targetKeyValues List
		while (regexMatcher.find() == true) {
			targetKeyValues.add(regexMatcher.group(1));
		}
		
		for (String line: targetKeyValues) {
			String[] toBeMapped = line.split(":");
			toBeMapped[1] = toBeMapped[1].trim(); // To get rid of leading whitespace
			readytoPutInHashMapLines.add(toBeMapped);
		}
		makeMaps(readytoPutInHashMapLines, 2);
		
	}
	
	/**
	 * @author Ryan Luu 
	 * 
	 * initmadLibPhrase grabs a random madLib phrase from templates.txt to be used in the game
	 * It initializes it to the private field madLibPhrase.
	 * 
	 */
	private void initmadLibPhrase() {
			// Picks random quote from the text file
			ArrayList<String> quotes = new ArrayList<String>();
			Scanner scanner = null;
			try {
				scanner = new Scanner(new File("templates.txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			// Adds lines in file to ArrayList
			quotes.add(scanner.nextLine());
			while (scanner.hasNextLine()) {
				quotes.add(scanner.nextLine());
			}
			scanner.close();
			Random rand = new Random();
			madLibPhrase = quotes.get(rand.nextInt(quotes.size())).toUpperCase();
		
	}
	
	/**
	 * 
	 * @author Ryan Luu
	 * 
	 * initWordsDataSet parses parts_of_speech.txt into an array map of the 
	 * word to its respective part of speech.
	 * 
	 */
	private void initWordsDataSet() {
		ArrayList<String> needToBeParsedLines = new ArrayList<String>();
		ArrayList<String[]> readytoPutInHashMapLines = new ArrayList<String[]>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("parts_of_speech.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		needToBeParsedLines.add(scanner.nextLine());
		while(scanner.hasNextLine()) {
			needToBeParsedLines.add(scanner.nextLine());
		}
		scanner.close();
		// For Loop to split in String[] to be more easily mapped out.
		for (String line: needToBeParsedLines) {
			String need = line.replaceAll("\\s+", " "); // Replaces multiple spaces between words to just one space between words
			String[] toBeMapped = need.split(" ");
			readytoPutInHashMapLines.add(toBeMapped);
		}
		makeMaps(readytoPutInHashMapLines, 1);
		
	}
	
	/**
	 * @author Ryan Luu
	 * 
	 * makeWordsDataSet is a helper method for initWordsDataSet or init 
	 * 
	 * For POS, It loops over the parts_of_speechs_list and puts information to the HashMap wordsDataSet.
	 * Value's can be larger than a single word and must be handled accordingly. 
	 * Ex) NOUN vs PROPER NOUN
	 * 
	 * 
	 * @param parts_of_speechList is a list containing information we need to map to the HashMap, wordsDataSet
	 * @param flag details whether to put in wordsDataSet or toBeReplacedInMadLib 
	 * 1 == words 
	 * 2 == replaced in Madlib
	 */
	private void makeMaps(ArrayList<String[]> parts_of_speechList, int flag) {
		for (String[] line: parts_of_speechList) {
			String key = line[0]; // Key will always be first element in parts_of_speech list
			String value = "";

			if (line.length > 2) {  // Checks for values containing multiple words
				for (int i = 1; i < line.length; i++) {
					value += line[i].toUpperCase() + " ";
				}
				value = value.trim(); // To Remove WhiteSpace at end
			} else {
				value = line[1].toUpperCase();
			}
			if (flag == 1) {
				wordsDataSet.put(key, value);
			}
			else if (flag == 2) {
				toBeReplacedInMadLib.put(key,value);
			}
		}
	}
	
	// Getter Methods
	public String getMadLibPhrase() {
		return madLibPhrase;
	}
	
	public HashMap getWordsDataSet() {
		return wordsDataSet;
		
	}
	
	public HashMap getToBeReplacedInMadLibs() {
		return toBeReplacedInMadLib;
		
	}
	
	// Setter Methods
	public void setNewString(String newString) {
		madLibPhrase = newString;
	}
}
