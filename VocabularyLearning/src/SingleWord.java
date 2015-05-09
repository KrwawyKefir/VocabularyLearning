
public class SingleWord {
	
	private String word;
	private String translation;
	private int score;
	
	public SingleWord (String word, String translation, int score){
		this.word = word;
		this.translation = translation;
		this.score = score;
	}

	public String getWord() {
		return word;
	}

	public String getTranslation() {
		return translation;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
