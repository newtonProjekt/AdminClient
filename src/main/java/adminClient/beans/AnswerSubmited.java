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
}
