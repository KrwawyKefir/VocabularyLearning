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

	private SingleWord[] words;
	private SingleWord[] currentChunkToLearn;

	private Scanner input;


	public Words(String filePath) {
		
		words = loadWords(filePath);
		currentChunkToLearn = Utilities.getMinValuesIndexes(words);
	}

	public void learnWord() {

		if(currentChunkToLearn.length == 0){
			currentChunkToLearn = Utilities.getMinValuesIndexes(words);
		}
		
		int currentRand = Utilities.randInt(0, currentChunkToLearn.length - 1);
		
		SingleWord currentWord = currentChunkToLearn[currentRand];
		
		int numberOfTries = 0;
		input = new Scanner(System.in);

		while (true) {

			System.out.println("\nWord for translation: " + currentWord.getTranslation());

			if (numberOfTries > 2 && numberOfTries < currentWord.getWord().length() + 3) {
				System.out.println("\nPrompt: ");
				numberOfTries = Utilities.getPrompt(currentWord.getWord(), numberOfTries);
				System.out.println("");

			} else if (numberOfTries == currentWord.getWord().length() + 3) {
				System.out.println("Word was: " + currentWord);
				System.out.println("Next time will be better!");
				
				break;
			}
			
			System.out.println("ä é ü ö ß");

			if (input.nextLine().trim().equals(currentWord.getWord())) {
				System.out.println("\nGreat!");

				currentWord.setScore(currentWord.getScore() + 2);

				break;
			} else if (numberOfTries < currentWord.getWord().length() + 2){
				System.out.println("Try again");
				numberOfTries++;
			} else {
				numberOfTries++;
			}
			
			currentWord.setScore(currentWord.getScore() - 2);
		}
		
		currentChunkToLearn = ArrayUtils.removeElement(currentChunkToLearn, currentWord);
	}
	
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