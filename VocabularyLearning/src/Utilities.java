import java.util.Random;

public final class Utilities {

	public static int randInt(int min, int max) {

		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static int[] getMinValuesIndexes(int[] array) {

		int minValue = array[0];
		int countValues = 1;

		for (int i = 1; i < array.length; i++) {

			if (array[i] < minValue) {
				minValue = array[i];
				countValues = 1;
			} else if (array[i] == minValue) {
				countValues++;
			}
		}

		int[] minValues = new int[countValues];

		int index = 0;

		for (int i = 0; i < array.length; i++) {

			if (array[i] == minValue) {
				minValues[index] = i;

				index++;
			}
		}

		return minValues;
	}
}
