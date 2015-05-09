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

	private SingleWord[] words; //Table of all words stored in objects
	private SingleWord[] currentChunkToLearn; //Table of words to learn, basing on lowest scores

	private Scanner input;


	public Words(String filePath) {
		words = loadWords(filePath);
		currentChunkToLearn = Utilities.getMinValuesIndexes(words);
	}

	public void learnWord() {

		//Called in case all words with lowest score were already displayed to user
		if(currentChunkToLearn.length == 0){
			currentChunkToLearn = Utilities.getMinValuesIndexes(words);
		}
		
		//Words are randomly chosen from collection
		int currentRand = Utilities.randInt(0, currentChunkToLearn.length - 1);
		
		SingleWord currentWord = currentChunkToLearn[currentRand];
		
		int numberOfTries = 0;
		input = new Scanner(System.in);

		while (true) {

			System.out.println("\nWord for translation: " + currentWord.getTranslation());

			if (numberOfTries > 1 && numberOfTries < currentWord.getWord().length() + 2) {
				System.out.println("\nPrompt: ");
				numberOfTries = Utilities.getPrompt(currentWord.getWord(), numberOfTries);
				System.out.println("");

			} else if (numberOfTries == currentWord.getWord().length() + 2) {
				System.out.println("Word was: " + currentWord.getWord());
				System.out.println("Current score for the word is: " + currentWord.getScore());
				System.out.println("Next time will be better!");
				
				break;
			}
			
			System.out.println("ä é ü ö ß");

			//Break in case word was correct, otherwise increase number of tries - else if was
			//introduced to avoid printing "Try again!" when current try was the last one
			if (input.nextLine().trim().equals(currentWord.getWord())) {
				
				currentWord.amendScore(2);
				
				System.out.println("\nGreat!");
				System.out.println("Current score for the word is: " + currentWord.getScore());

				break;
			} else if (numberOfTries < currentWord.getWord().length() + 1){
				System.out.println("Try again!");
				
				//Score decreased by 1 every time word was incorrectly guessed
				//maximum -5 score in single round
				if(numberOfTries < 5) {
					currentWord.amendScore(-1);
				}
				
				numberOfTries++;
				
			} else {
				numberOfTries++;
			}
			
			
		}
		
		//Deleting already taught word from temporary table
		currentChunkToLearn = ArrayUtils.removeElement(currentChunkToLearn, currentWord);
	}
	
	//Loads words from file into table
	private SingleWord[] loadWords(String filePath){
		
		int numberOfWords = 0;
		File dictionaryFile = new File(filePath);
		
		try(BufferedReader bReader = new BufferedReader(new FileReader(dictionaryFile))){
			
			while((bReader.readLine()) != null ){
				numberOfWords++;
			}
			
			System.out.println("There are " + numberOfWords + " words in dictionary");
			
		} catch (FileNotFoundException e) {
			System.out.println("File " + dictionaryFile.toString() + " was not found");
		} catch (IOException e) {
			System.out.println("There was a problem with I/O operation, reading file aborted");
		}
		
		SingleWord[] words = new SingleWord[numberOfWords];
		
		try(BufferedReader bReader = new BufferedReader(new FileReader(dictionaryFile))){
			
			String line;
			int index = 0;
			
			while((line = bReader.readLine()) != null ){
				words[index] = Utilities.parseLine(line);
				index++;				
			}
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File " + dictionaryFile.toString() + " was not found");
		} catch (IOException e) {
			System.out.println("There was a problem with I/O operation, reading file aborted");
		}
		
		return words;
	}

	//Overwrites changes
	public void writeChanges(String filePath){
		
		File dictionaryFile = new File(filePath);
		
		try(BufferedWriter bReader = new BufferedWriter(new FileWriter(dictionaryFile))){
			
			int numberOfLines = words.length;
			
			for(int i = 0; i < numberOfLines; i++){
				bReader.write(words[i].getWord() + " - " + words[i].getTranslation() + " : " + words[i].getScore());
				
				if(i < numberOfLines - 1){
					bReader.newLine();
				}
			}
			
		} catch (IOException e) {
			System.out.println("There was a problem with I/O operation, reading file aborted");
		}	
	}
}