package adminClient;

/**
 * Bean-class for a Test.
 */

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Jonas on 2016-03-03.
 */

public class Test {

    private SimpleStringProperty name;
    private SimpleStringProperty subject;
    private SimpleIntegerProperty maxTime;
    private SimpleIntegerProperty maxScore;
    private SimpleStringProperty dateCreated;

    public Test(String name, String subject, String dateCreated) {
        this.name = new SimpleStringProperty(name);
        this.subject = new SimpleStringProperty(subject);
        this.dateCreated = new SimpleStringProperty(dateCreated);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSubject() {
        return subject.get();
    }

    public SimpleStringProperty subjectProperty() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public int getMaxTime() {
        return maxTime.get();
    }

    public SimpleIntegerProperty maxTimeProperty() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime.set(maxTime);
    }

    public int getMaxScore() {
        return maxScore.get();
    }

    public SimpleIntegerProperty maxScoreProperty() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore.set(maxScore);
    }

    public String getDateCreated() {
        return dateCreated.get();
    }

    public SimpleStringProperty dateCreatedProperty() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated.set(dateCreated);
    }
}
