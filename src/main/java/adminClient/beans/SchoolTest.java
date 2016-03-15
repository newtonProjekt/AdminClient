package adminClient.beans;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The entity class that represents the tests. Contains the type of test and the questions in the test.
 */

public class SchoolTest {

	private Integer id;
	private String name;
	private String subject;
    private int testTime;
    private List<Question> questions;
    private Date dateCreated;
    private int gThreshold;
    private int vgThreshold;

    public SchoolTest() {
        questions = new ArrayList<>();
    }

    public SchoolTest(String name, String subject, int testTime) {
        questions = new ArrayList<>();

        this.name = name;
        this.subject = subject;
        this.testTime = testTime;
    }


    public List<Question> getQuestions() {
        return questions;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getgThreshold() {
        return gThreshold;
    }

    public void setgThreshold(int gThreshold) {
        this.gThreshold = gThreshold;
    }

    public int getVgThreshold() {
        return vgThreshold;
    }

    public void setVgThreshold(int vgThreshold) {
        this.vgThreshold = vgThreshold;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void updateQuestion (Question oldQuestion, Question newQuestion){
        questions.set(questions.indexOf(oldQuestion),newQuestion);
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
