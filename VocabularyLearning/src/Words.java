import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.lang3.ArrayUtils;

public class Words {
	int numberOfWords;

	private String currentWord;
	private String currentTranslation;
	private int currentScore;

	private String[] words;
	private String[] translations;
	private int[] scores;
	
	private int[] currentChunkToLearn;

	private Scanner input;



	// constructor for loading text file into arrays
	public Words(String filePath) {
		
		numberOfWords = countWords(filePath);
		
		loadWords(filePath);
		
		currentChunkToLearn = Utilities.getMinValuesIndexes(scores);
	}

	public void learnWord() {

		if(currentChunkToLearn.length == 0){
			currentChunkToLearn = Utilities.getMinValuesIndexes(scores);
		}
		int currentRand = Utilities.randInt(0, currentChunkToLearn.length - 1);
		
		int index = currentChunkToLearn[currentRand];
		
		currentWord = words[index];
		currentTranslation = translations[index];
		currentScore = scores[index];
		
		int numberOfTries = 0;
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
				
				break;
			}
			
			System.out.println("ä é ü ö ß");

			// checking if word was correctly guessed,
			// if so - leave loop, if not, next try
			if (input.nextLine().trim().equals(currentWord)) {
				System.out.println("\nGreat!");

				scores[index] += 2;

				break;
			} else if (numberOfTries < currentWord.length() + 2){
				System.out.println("Try again");
				numberOfTries++;
			} else {
				numberOfTries++;
			}
			
			if (currentScore < scores[index] + 3){
				scores[index]--;
			}
		}
		
		currentChunkToLearn = ArrayUtils.removeElement(currentChunkToLearn, index);
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

	/*
	private int loadNextRandomWord(int max) {
		
		int randIndex = randInt(0, max - 1);

		currentWord = words[randIndex];
		currentTranslation = translations[randIndex];
		currentScore = scores[randIndex];

		return randIndex;
	}
	*/
	
	private void parseLine(String line, int index) {

		int indexOfColon = line.indexOf(":"); // looking for the score part
												
		int indexOfDash = line.indexOf("-"); // checking where word ends and translation starts

		// cutting the word and translation from the whole line and omitting
		// white spaces
		words[index] = line.substring(0, indexOfDash).trim();
		translations[index] = line.substring(indexOfDash + 1, indexOfColon).trim();
		scores[index] = Integer.parseInt( line.substring(indexOfColon + 1).trim() );
	}

	// getters
	public String getWord() {
		return currentWord;
	}

	public String getTranslation() {
		return currentTranslation;
	}
}