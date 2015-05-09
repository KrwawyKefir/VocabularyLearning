import java.util.Scanner;

public class Play {

	public static void main(String[] args) {

		//location of a text file
		String filePath = "slowka.txt";
		
		//Creating object that will contain whole collections
		//of words, translations, scores and all methods for
		//learning process
		Words words = new Words(filePath);

		//For menu purposes only
		Scanner input = new Scanner(System.in);

		do {
			words.learnWord();
			
			System.out.println("\nAvailable options:");
			System.out.println("Next word - Enter");
			System.out.println("Exit - any other key");

		} while (input.nextLine().length() == 0);
		
		
		System.out.println("\nEND");
		input.close();
		
		//original file will be overwritten with changes
		words.writeChanges(filePath);
		
	}
	
}