import java.io.FileNotFoundException;
import java.util.Scanner;

public class Play {
//master branch comment
	public static void main(String[] args) throws FileNotFoundException {

		Words words = new Words("slowka.txt");

		Scanner input = new Scanner(System.in);

		do {
			words.learnWord();

			// simple menu to load next word or leave
			System.out.println("\nDost�pne opcje:");
			System.out.println("Nast�pne s�owo - Enter");
			System.out.println("Wyj�cie - dowolny inny klawisz");

		} while (input.nextLine().length() == 0);
		
		
		System.out.println("Koniec!");
		input.close();
	}
	
}
