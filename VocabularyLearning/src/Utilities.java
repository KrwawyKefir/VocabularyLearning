import java.util.Random;

public final class Utilities {

	//Random intiger, from given range - inclusive
	public static int randInt(int min, int max) {

		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	//Returns an array of SingleWord objects that have the lowest score from all
	public static SingleWord[] getMinValuesIndexes(SingleWord[] array) {

		int minValue = array[0].getScore();
		int countValues = 1;

		for (int i = 1; i < array.length; i++) {

			if (array[i].getScore() < minValue) {
				minValue = array[i].getScore();
				countValues = 1;
			} else if (array[i].getScore() == minValue) {
				countValues++;
			}
		}

		SingleWord[] minValues = new SingleWord[countValues];

		int index = 0;

		for (int i = 0; i < array.length; i++) {

			if (array[i].getScore() == minValue) {
				minValues[index] = array[i];

				index++;
			}
		}

		return minValues;
	}
	
	//Displays prompt basing on a number of tries, replacing some
	//of the letters with dashes. In case space was prompted, additional
	//character is displayed
	public static int getPrompt(String word, int counter) {

		int i = 0; // common counter for both loops

		// printing letters depending on number of tries
		for (; i < counter - 2; i++) {

			if (word.charAt(i) == ' ') {
				System.out.print("- ");
				i++;

				if (counter - 2 == i) {
					counter++;
				}
			}

			System.out.print(word.charAt(i) + " ");
		}

		// filling rest of the word with '-' for spaces and '_' for letters
		for (; i < word.length(); i++) {
			if (word.charAt(i) == ' ') {
				System.out.print("- ");
			} else {
				System.out.print("_ ");
			}
		}

		return counter; // returning counter in case space was additionally
						// discovered
	}
	
	//Parsing line, while looking for word, translation and score,
	//if score is not found, initializes it as 0.
	//Returns complete object
	public static SingleWord parseLine(String line) {

		int indexOfColon = line.indexOf(":"); //Looking for the score part
		int indexOfDash = line.indexOf("-"); //Checking where word ends and translation starts
		
		int score;
		
		if (indexOfColon == -1){
			score = 0;
			indexOfColon = line.length();
		} else {
			score = Integer.parseInt( line.substring(indexOfColon + 1).trim() );
		}

		//Cutting the word and translation from the whole line and omitting white spaces
		String word = line.substring(0, indexOfDash).trim();
		String translation = line.substring(indexOfDash + 1, indexOfColon).trim();
		

		
		return new SingleWord(word, translation, score);
	}
	
}


