package adminClient.beans;

/**
 * A bean-class that stores the usersubmited answers.
 * 
 * @author Johan (jolindse@hotmail.com)
 *
 */

public class AnswerSubmited {

	private int id;
	private int testId;
	private int questionId;
	private String answerString;
	private boolean correctAnswer;
	private boolean corrected;
	
	public AnswerSubmited(){

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getAnswerString() {
		return answerString;
	}

	public void setAnswerString(String answerString) {
		this.answerString = answerString;
	}

	public boolean isCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(boolean correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public boolean isCorrected() {
		return corrected;
	}

	public void setCorrected(boolean corrected) {
		this.corrected = corrected;
	}
}
