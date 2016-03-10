package adminClient.beans;

/**
 * Bean-class for answers to test.
 *
 * Contains text and boolean to indicate if it's the correct answer.
 */

public class Answer {

	private int id;
	private String answerText;
	private boolean correctAnswer;
	
	public Answer(String answerText){
		this.answerText = answerText;
	}

	public Answer(String answerText, boolean correctAnswer) {
		this.answerText = answerText;
		this.correctAnswer = correctAnswer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public boolean isCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(boolean correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", answerText='" + answerText + '\'' +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}
