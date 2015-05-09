import java.util.Random;

public final class Utilities {

	
	public static int randInt(int min, int max) {

		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

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
	
	public static int getPrompt(String word, int counter) {

		int i = 0; // common counter for both loops

		// printing letters depending on number of tries
		for (; i < counter - 3; i++) {

			// in case space is about to be prompted, the following character is
			// given as well
			if (word.charAt(i) == ' ') {
				System.out.print("- ");
				i++;

				if (counter - 3 == i) {
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
	
	public static SingleWord parseLine(String line) {

		int indexOfColon = line.indexOf(":"); // looking for the score part
		int indexOfDash = line.indexOf("-"); // checking where word ends and translation starts

		// cutting the word and translation from the whole line and omitting
		// white spaces
		String word = line.substring(0, indexOfDash).trim();;
		String translation = line.substring(indexOfDash + 1, indexOfColon).trim();
		int score = Integer.parseInt( line.substring(indexOfColon + 1).trim() );
		
		return new SingleWord(word, translation, score);
	}
	
}


