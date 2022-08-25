import java.util.Scanner;

/**
 * 
 * @author Ryan Luu 
 * This is a beta-design of the MadLibs Project for Alex since he has better things to do then parse through text files.
 * 
 *
 */

public class Madlibs {
	
	public static void main(String[] args) throws Exception {
		MadlibsModel model = new MadlibsModel();
		MadlibsController controller = new MadlibsController(model);
		
		System.out.println("Welcome to MadLibs BetaTesting! Tested under assumption you can't guess for previous guessed spots!\n");
		System.out.println(controller.grabMadLibQuote() + "\n");
		
		// Start of our Madlib game
		while(controller.isGameOver() != false) {
			
			// Grabs Position from User
			Scanner scan = new Scanner(System.in);
			System.out.print("Enter a position to replace: ");
			String position = scan.nextLine();
			String part_of_speech = controller.grabPOS(position);
			
			// Error check for valid position
			if (part_of_speech.equals("-1")) {
				System.out.println("That's not a valid position. Try again.\n");
				continue;
			}
			
			// Grabs Word from User
			System.out.print("Enter a replacement " + part_of_speech + ": ");
			String inputWord = scan.nextLine();
			int c_status = controller.replace(position, inputWord);
			
			// Error Checking for Valid Word
			if (c_status == -1) {
				System.out.println("That's not a valid word in our database. Try again.\n");
				continue;
			}
			// Prints out the updated Quote otherwise
			System.out.println("\n" + controller.grabMadLibQuote() + "\n");	
		}
		
		// GameOver if we reach here.
		System.out.println("\nPuzzle complete! Would you like to play again?");
		System.out.println("Just Kidding. I haven't implmenented that yet. Night-Night!");
		
	}

}
