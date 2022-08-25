import java.util.HashMap;

public class MadlibsController {
	
	private MadlibsModel model;

	public MadlibsController(MadlibsModel model) {
		this.model = model;
	}
	
	// Checks for Part of Speech with given key to see if valid
	public String grabPOS(String key) {
		if (grabToBeReplacedHashMap().containsKey(key) == false) {
			return "-1";
		} 
		return (String) grabToBeReplacedHashMap().get(key);
		
	}

	/**
	 * @author Ryan Luu 
	 * 
	 * replace does the actual work of replacing the user guess within the madlib quote. 
	 * It does this by cross-listing our words_data set HashMap and our toBeReplaced Hashmap. 
	 * If successful, it replaces the quote and then pops the key:value from the toBeReplcaed HashMap
	 * to avoid duplicate guesses
	 * 
	 * @param position is the user inputted index
	 * @param inputWord is the user inputted word
	 * @return an int indicating whether or not the replace was succesful. -1 for failure. 0 for success
	 */
	public int replace(String position, String inputWord) {
		String quote = grabMadLibQuote();
		HashMap toBeReplaced = grabToBeReplacedHashMap();
		HashMap dataSet = getWordsDataSet();
		String main_POS = (String) toBeReplaced.get(position);
		
		if (dataSet.containsKey(inputWord.toLowerCase())) { // Check to make sure word is in dataset
			String dataSetPOS = (String) dataSet.get(inputWord.toLowerCase());
			if (!dataSetPOS.equals(main_POS)) { // If the POS don't match up return -1
				return -1;
			}
		} else {
			return -1;
		}
		
		// UNDER ASSUMPTION THAT WE CANNOT REPLACE PREVIOUSLY GUESS SPOTS!!!!
		String oldQuote = position + ": " + toBeReplaced.get(position);
		String replacementQuote = position + ": " + inputWord.toUpperCase();
		String newQuote = quote.replace(oldQuote, replacementQuote); 
		model.setNewString(newQuote); // Updates our new quote in the model
		toBeReplaced.remove(position); // Removes entry from out HashMap so that we know when its gameOver.
		return 0;
		
	}
	
	// Checks for GameOver
	public boolean isGameOver() {
		return grabToBeReplacedHashMap().size() > 0;
	}
	
	// Grabs MadLib Phrase from Model
	public String grabMadLibQuote() {
		return model.getMadLibPhrase();
	}
	
	// Grabs HashMap of MadLib Entries to Replace
	public HashMap grabToBeReplacedHashMap() {
		return model.getToBeReplacedInMadLibs();
	}
	
	// Grabs HashMap of our WordsDataSet
	public HashMap getWordsDataSet() {
		return model.getWordsDataSet();
		
	}
	

}
