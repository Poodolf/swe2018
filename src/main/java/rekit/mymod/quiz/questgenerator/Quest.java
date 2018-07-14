package rekit.mymod.quiz.questgenerator;

/**
 * A Mathquestion has one question string and four answer strings
 * 
 * @author Jianan Ye
 *
 */
public class Quest {
	
	private String frage;
	private String AntwortT;
	private String AntwortF1;
	private String AntwortF2;
	private String AntwortF3;
	
	public Quest(String frage, String AntwortT, String AntwortF1, String AntwortF2, String AntwortF3) {
		this.frage = frage;
		this.AntwortT = AntwortT;
		this.AntwortF1 = AntwortF1;
		this.AntwortF2 = AntwortF2;
		this.AntwortF3 = AntwortF3;
	}

	public String getFrage() {
		return frage;
	}

	public String getAntwortT() {
		return AntwortT;
	}

	public String getAntwortF1() {
		return AntwortF1;
	}

	public String getAntwortF2() {
		return AntwortF2;
	}

	public String getAntwortF3() {
		return AntwortF3;
	}

	@Override
	public String toString() {
		return "Quest [ \n frage = " + frage + ", AntwortT = " + AntwortT + ", AntwortF1 = " + AntwortF1 + ", AntwortF2 = "
				+ AntwortF2 + ", AntwortF3 = " + AntwortF3 + "]";
	}
}
