import java.util.Scanner;

public class Play {

	public static void main(String[] args) {

		Words words = new Words("slowka.txt");

		Scanner input = new Scanner(System.in);

		do {
			words.learnWord();

			// simple menu to load next word or leave
			System.out.println("\nAvailable options:");
			System.out.println("Next word - Enter");
			System.out.println("Exit - any other key");

		} while (input.nextLine().length() == 0);
		
		
		System.out.println("\nEND");
		input.close();
		
		words.writeChanges("slowka.txt");
		
	}
	
}