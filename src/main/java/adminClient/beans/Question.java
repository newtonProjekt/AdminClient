package adminClient.beans;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * The beans class for questions that contains the answers refering to the tests, question type (multi or text), question
 * text and optionally question image.
 */

public class Question {

	private int id;
	private boolean multiQuestion;
	private int points;
	private boolean vgQuestion;
	private String questionText;
	private String questionImage;
	private Image questionImageFile;
	private List<Answer> answers;
	
	public Question(){
		answers = new ArrayList<Answer>();
	}

	public Question(String questionText, int points){
		this.points = points;
		this.questionText = questionText;
		answers = new ArrayList<Answer>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isMultiQuestion() {
		return multiQuestion;
	}

	public void setMultiQuestion(boolean multiQuestion) {
		this.multiQuestion = multiQuestion;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public void addAnswer(Answer currAnswer){
		answers.add(currAnswer);
	}

	public void removeAnswer(Answer currAnswer){
		answers.remove(currAnswer);
	}
	
}
