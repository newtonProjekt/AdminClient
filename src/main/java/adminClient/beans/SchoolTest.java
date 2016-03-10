package adminClient.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * The entity class that represents the tests. Contains the type of test and the questions in the test.
 */

public class SchoolTest {

	private int id;
	private String name;
	private String subject;
    private int testTime;
    private List<Question> questions;
	
	public SchoolTest(){
		questions = new ArrayList<Question>();
	}

	public SchoolTest(String subject){
		this.subject = subject;
		questions = new ArrayList<Question>();
	}

    public int getTestTime() {
        return testTime;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public void addQuestion(Question currQuestion){
		questions.add(currQuestion);
	}

	public void removeQuestion(Question currQuestion){
		questions.remove(currQuestion);
	}
}
