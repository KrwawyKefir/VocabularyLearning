import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Words {
	int numberOfWords;

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
	public Words(String filePath) {
		
		numberOfWords = countWords(filePath);
		
		loadWords(filePath);
	
		loadNextRandomWord();
	}
	
	public void writeChanges(String filePath){
		
		File dictionaryFile = new File(filePath);
		
		try(BufferedWriter bReader = new BufferedWriter(new FileWriter(dictionaryFile))){
			
			int numberOfLines = words.length;
			
			for(int i = 0; i < numberOfLines; i++){
				bReader.write(words[i] + " - " + translations[i] + " : " + scores[i]);
				
				if(i < numberOfLines - 1){
					bReader.newLine();
				}
			}
			
			
		} catch (IOException e) {
			System.out.println("There was a problem with I/O operation, reading file aborted");
		}
		
	}
	
	private void loadWords(String filePath){
		
		words = new String[numberOfWords];
		translations = new String[numberOfWords];
		scores = new int[numberOfWords];
		
		File dictionaryFile = new File(filePath);
		
		try(BufferedReader bReader = new BufferedReader(new FileReader(dictionaryFile))){
			
			String line;
			int index = 0;
			
			while((line = bReader.readLine()) != null ){
				parseLine(line, index);
				index++;
			}
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File " + dictionaryFile.toString() + " was not found");
		} catch (IOException e) {
			System.out.println("There was a problem with I/O operation, reading file aborted");
		}
		
	}

	private int countWords(String filePath){
		
		int tempNumberOfWords = 0;
		File dictionaryFile = new File(filePath);
		
		try(BufferedReader bReader = new BufferedReader(new FileReader(dictionaryFile))){
			
			while((bReader.readLine()) != null ){
				tempNumberOfWords++;
			}
			
			System.out.println("There are " + tempNumberOfWords + " words in dictionary");
			
		} catch (FileNotFoundException e) {
			System.out.println("File " + dictionaryFile.toString() + " was not found");
		} catch (IOException e) {
			System.out.println("There was a problem with I/O operation, reading file aborted");
		}
		
		return tempNumberOfWords;
	}
	
	private void parseLine(String line, int index) {

		int indexOfColon = line.indexOf(":"); // looking for the score part
												
		int indexOfDash = line.indexOf("-"); // checking where word ends and translation starts

		// cutting the word and translation from the whole line and omitting
		// white spaces
		words[index] = line.substring(0, indexOfDash).trim();
		translations[index] = line.substring(indexOfDash + 1, indexOfColon).trim();
		scores[index] = Integer.parseInt( line.substring(indexOfColon + 1).trim() );
	}

	public void learnWord() {

		int numberOfTries = 0;
		int index = loadNextRandomWord();

		input = new Scanner(System.in);

		while (true) {

			System.out.println("\nWord for translation: " + currentTranslation);

			// giving prompt after 3rd wrong answer,
			// if more tries than letters - end the round and print the word
			if (numberOfTries > 2 && numberOfTries < currentWord.length() + 3) {
				System.out.println("\nPrompt: ");
				numberOfTries = getPrompt(numberOfTries);
				System.out.println("");

			} else if (numberOfTries == currentWord.length() + 3) {
				System.out.println("Word was: " + currentWord);
				System.out.println("Next time will be better!");

				
				System.out.println(scores[index]);
				break;
			}
			
			System.out.println("� � � � �");

			// checking if word was correctly guessed,
			// if so - leave loop, if not, next try
			if (input.nextLine().trim().equals(currentWord)) {
				System.out.println("\nGreat!");

				scores[index]++;

				break;
			} else if (numberOfTries < currentWord.length() + 2){
				System.out.println("Try again");
				numberOfTries++;
			} else {
				numberOfTries++;
			}
			
			scores[index]--;
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

				if (counter - 3 == i) {
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

		return counter; // returning counter in case space was additionally
						// discovered
	}

	private int loadNextRandomWord() {
		
		int randIndex = randInt(0, numberOfWords - 1);

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