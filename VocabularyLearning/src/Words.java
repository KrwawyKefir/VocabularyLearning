import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Words {

	int numberOfwords;

	private String currentWord; // actual German word
	private String currentTranslation; // translated word
	private int currentScore;

	private String[] words;
	private String[] translations;
	private int[] scores;

	private Scanner input;

	// method for random integer from range min-max (inclusive), used for
	// choosing next word
	public static int randInt(int min, int max) {

		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	// constructor for loading text file into arrays
	public Words(String filePath) throws FileNotFoundException {

		File dictionary = new File(filePath);

		// -----------------------------------------------------
		// beginning of Scanner for counting lines
		input = new Scanner(dictionary);

		// incrementing counter until next line does not exist
		while (input.hasNextLine()) {
			numberOfwords++;
			input.nextLine();
		}

		input.close();
		// end of Scanner for counting lines
		// -----------------------------------------------------

		words = new String[numberOfwords];
		translations = new String[numberOfwords];
		scores = new int[numberOfwords];

		// -----------------------------------------------------
		// beginning of Scanner for loading whole lines

		input = new Scanner(dictionary);
		String tempLine;
		int counter = 0;

		// incrementing counter until next line does not exist
		while (input.hasNextLine()) {

			tempLine = input.nextLine();
			parseLine(tempLine, counter);

			counter++;
		}

		input.close();
		// end of Scanner for loading whole lines
		// -----------------------------------------------------
	}

	private void parseLine(String line, int index) {

		int indexOfColon = line.indexOf(":"); // checking where word ends and
												// translation starts

		//cutting the word and translation from the whole line and omitting white spaces
		words[index] = line.substring(0, indexOfColon).trim();
		translations[index] = line.substring(indexOfColon + 1).trim(); 
		scores[index] = 0; // initially all words have score = 0
	}

	public void learnWord() {

		int numberOfTries = 0;
		int index = loadNextRandomWord();

		input = new Scanner(System.in);

		while (true) {

			System.out.println("\nS³owo do t³umacznia: " + currentTranslation
					+ "\nä é ü ö ß");

			// giving prompt after 3rd wrong answer,
			// if more tries than letters - end the round and print the word
			if (numberOfTries > 2 && numberOfTries < currentWord.length() + 3) {
				System.out.println("PodpowiedŸ: ");
				numberOfTries = getPrompt(numberOfTries);

			} else if (numberOfTries == currentWord.length() + 3) {
				System.out.println("S³owo to: " + currentWord);
				System.out.println("Nastêpnym razem siê uda!");

				scores[index]--;

				break;
			}

			// checking if word was correctly guessed,
			// if so - leave loop, if not, next try
			if (input.nextLine().trim().equals(currentWord)) {
				System.out.println("\nWybornie!");

				scores[index]++;

				break;
			} else {
				System.out.println("Spróbuj jeszcze raz.\n");
				numberOfTries++;
			}
		}
	}

	// getting prompt depending on attempt number - more tries = more uncovered
	// letters
	// rest of word is replaced with underscores, spaces printed as dashes
	private int getPrompt(int counter) {

		int i = 0; // common counter for both loops

		// printing letters depending on number of tries
		for (; i < counter - 3; i++) {

			// in case space is about to be prompted, the following character is
			// given as well
			if (currentWord.charAt(i) == ' ') {
				System.out.print("- ");
				i++;
				
				if (counter - 3 == i){
					counter++;
				}
			}
			
			System.out.print(currentWord.charAt(i) + " ");
			
		}

		// filling rest of the word with '-' for spaces and '_' for letters
		for (; i < currentWord.length(); i++) {
			if (currentWord.charAt(i) == ' ') {
				System.out.print("- ");
			} else {
				System.out.print("_ ");
			}
		}
		return counter; // returning counter in case space was additionally discovered
	}

	private int loadNextRandomWord() {
		int randIndex = randInt(0, numberOfwords - 1);

		currentWord = words[randIndex];
		currentTranslation = translations[randIndex];
		currentScore = scores[randIndex];

		return randIndex;
	}

	// getters
	public String getWord() {
		return currentWord;
	}

	public String getTranslation() {
		return currentTranslation;
	}
}
