package adminClient;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Jonas on 2016-03-03.
 */
public class Test {
    private SimpleStringProperty subject;
    private SimpleIntegerProperty maxTime;
    private SimpleIntegerProperty maxScore;

    public Test(String subject, int maxTime, int maxScore) {
        this.subject = new SimpleStringProperty(subject);
        this.maxTime = new SimpleIntegerProperty(maxTime);
        this.maxScore = new SimpleIntegerProperty(maxScore);
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

}
