package adminClient.beans;

/**
 * Bean-class for a Test.
 */

/**
 * Created by Jonas on 2016-03-03.
 */

public class Test {

    private String name;
    private String subject;
    private String maxTime;
    private String maxScore;
    private String dateCreated;

    public Test(String name, String subject, String dateCreated) {
        this.name = name;
        this.subject = subject;
        this.dateCreated = dateCreated;
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

    public String getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
