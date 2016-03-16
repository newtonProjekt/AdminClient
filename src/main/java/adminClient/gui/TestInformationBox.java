package adminClient.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * Created by Jonas on 2016-03-16.
 */
public class TestInformationBox extends GridPane {
    private ColumnConstraints column1 = new ColumnConstraints();
    private Label testName = new Label("Provnamn:");
    private Label subjectLabel = new Label("Ämne:");
    private Label timeLabel = new Label("Tid i minuter:");
    private Label questionAmount = new Label("Antal frågor:");
    private Label created = new Label("Skapad:");

    private Label currTestName = new Label();
    private Label currSubjectLabel = new Label();
    private Label currTimeLabel = new Label();
    private Label currQuestionAmount = new Label();
    private Label currCreated = new Label();


    public TestInformationBox(String testNameIn, String subjectLabelIn, String timeLabelIn, String questionAmountIn, String createdIn) {
        GridPane gridPane = this;
        currTestName.setText(testNameIn);
        currSubjectLabel.setText(subjectLabelIn);
        currTimeLabel.setText(timeLabelIn);
        currQuestionAmount.setText(questionAmountIn);
        currCreated.setText(createdIn);

        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.getColumnConstraints().add(column1);
        gridPane.setAlignment(Pos.TOP_CENTER);

        gridPane.add(testName,0,0);
        gridPane.add(subjectLabel,0,1);
        gridPane.add(timeLabel,0,2);
        gridPane.add(questionAmount,0,3);
        gridPane.add(created,0,4);

        gridPane.add(currTestName,1,0);
        gridPane.add(currSubjectLabel,1,1);
        gridPane.add(currTimeLabel,1,2);
        gridPane.add(currQuestionAmount,1,3);
        gridPane.add(currCreated,1,4);

    }


}
