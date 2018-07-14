package rekit.mymod.quiz.questgenerator;

import rekit.mymod.quiz.Answer;
import rekit.mymod.quiz.Question;

/**
 * Creates MathQuestions with answers
 * 
 * @author Jianan Ye
 *
 */
public class MathQuestGenerator {

	public MathQuestGenerator() {
	}
	
	/**
	 * generates a 3 x 3 matrix determinant quest
	 */
	public Question determinantQuest() {
		
		int[][] matrix = matrixGenerator(3, 3, 5);
		String matrixS = "";
		for (int i = 0; i < 3; i++) {
			matrixS += "| ";
			for (int j = 0; j < 3; j++) {
				matrixS += matrix[i][j] + " ";
			}
			matrixS += "|" + '\n';
		}

		String frage = "Was ist die determinante von: "  + '\n' + matrixS;
		int solution = determinantCalc(matrix);
		int[] falseanswer = generateFalseanswer(solution, true, 2, 2, 3);
		Question mathQuestion = new Question(frage, new Answer(Integer.toString(solution), true), 
				new Answer(Integer.toString(falseanswer[0])), 
				new Answer(Integer.toString(falseanswer[1])),
				new Answer(Integer.toString(falseanswer[2])));
		return mathQuestion;
	}
	
	/**
	 * generates an 8-Bit two's complement to decimal quest
	 */
	public Question binearToDezimalQuest() {
		
		String eightBit = "";
		for (int i = 0; i < 8; i++) {
			eightBit += Math.round(Math.random());
		}
		String frage = "Wandeln Sie folgende Zweierkomplement in Dezimal-System um " + '\n' + eightBit;
		int solution = binearToDezimalCalc(eightBit);
		int[] falseanswer = generateFalseanswer(solution, false, 2, 2, 3);
		Question mathQuestion = new Question(frage, new Answer(Integer.toString(solution), true), 
                new Answer(Integer.toString(falseanswer[0])), 
                new Answer(Integer.toString(falseanswer[1])),
                new Answer(Integer.toString(falseanswer[2])));
		return mathQuestion;
	}
	
	/**
	 * generates an n x m matrix with the random numbers from 0 to difficulty
	 * @param n number of rows
	 * @param m number of columns
	 * @param difficulty, random numbers from 0 to difficulty
	 * @return the generated matrix
	 */
	public int[][] matrixGenerator(int n, int m , int difficulty) {
		int matrix[][] = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				matrix[i][j] = (int) (Math.random() * difficulty);
			}
		}
		return matrix;
	}
	
	/**
	 * solves the determinant of an 3 x 3 matrix
	 * @param matrix to solve
	 * @return the determinant
	 */
	public int determinantCalc(int[][] matrix) {
		
		int determinante = matrix[0][0] * matrix[1][1] * matrix[2][2] 
						 + matrix[0][1] * matrix[1][2] * matrix[2][0]
						 + matrix[0][2] * matrix[1][0] * matrix[2][1]
						 - matrix[2][0] * matrix[1][1] * matrix[0][2]
						 - matrix[2][1] * matrix[1][2] * matrix[0][0]
						 - matrix[2][2] * matrix[1][0] * matrix[0][1];
		return determinante;
	}
	
	/**
	 * solves the 8-Bit two's complement to decimal quest
	 * @param eightBit the 8-Bit to solve
	 * @return the decimal value
	 */
	public int binearToDezimalCalc(String eightBit) {
		
		int solution = Integer.valueOf(eightBit, 2);
		if (solution > 127) {
			solution = -(256 - solution);
		}
		return solution;
	}
	
	/**
	 * generates unique false answers close to the right one
	 * @param answer to generate 3 false answers from
	 * @return array of false answers
	 */
	public int[] generateFalseanswer(int answer, boolean invert, int faktor1, int faktor2, int faktor3) {
		int solution;
		if (answer == 0) {
			solution = 1;
		} else {
			solution = answer;
		}
		int[] falseAnswer = new int[3];
		double faktor;
		do {
			faktor = Math.random() * faktor1;
			falseAnswer[0] = (int) (faktor * solution);
			if (invert) {
				falseAnswer[0] = (int) (Math.pow(-1, Math.round(Math.random())) * falseAnswer[0]);
			}
		} while (falseAnswer[0] == answer);
		
		do {
			faktor = Math.random() * faktor2;
			falseAnswer[1] = (int) (faktor * solution);
			if (invert) {
				falseAnswer[1] = (int) (Math.pow(-1, Math.round(Math.random())) * falseAnswer[1]);
			}
		} while (falseAnswer[1] == answer || falseAnswer[1] == falseAnswer[0]);
		
		do {
			faktor = Math.random() * faktor3;
			falseAnswer[2] = (int) (faktor * solution);
			if (invert) {
				falseAnswer[2] = (int) (Math.pow(-1, Math.round(Math.random())) * falseAnswer[2]);
			}
		} while (falseAnswer[2] == answer || falseAnswer[2] == falseAnswer[0]
				 || falseAnswer[2] == falseAnswer[1]);

		return falseAnswer;
	}
}
