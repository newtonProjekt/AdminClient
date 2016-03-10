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
    private String dateCreated;
    private List<Question> questions;

    public SchoolTest(String name, String subject, int testTime) {
        questions = new ArrayList<>();

        this.name = name;
        this.subject = subject;
        this.testTime = testTime;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTestTime() {
        return testTime;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    public void addQuestion (Question question){
        questions.add(question);
    }

    public void removeQuestion (Question question){
        questions.remove(question);
    }

    @Override
    public String toString() {
        return "SchoolTest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", testTime=" + testTime +
                ", dateCreated='" + dateCreated + '\'' +
                ", questions=" + questions +
                '}';
    }
}
