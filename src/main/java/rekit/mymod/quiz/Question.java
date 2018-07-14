package rekit.mymod.quiz;

import java.util.ArrayList;
import java.util.Collections;

public class Question {
	
	private final String text;
	private final ArrayList<Answer> answers = new ArrayList<Answer>();
	
	public Question(String text, Answer answer1, Answer answer2, Answer answer3, Answer answer4) {
		this.text = text;
		answers.add(answer1);
		answers.add(answer2);
		answers.add(answer3);
		answers.add(answer4);
		Collections.shuffle(answers);
	}
	
	public String getText() {
		return text;
	}
	
	public ArrayList<Answer> getShuffledAnswers() {
		return answers;
	}
}