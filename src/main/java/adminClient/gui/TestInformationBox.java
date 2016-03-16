package adminClient.gui;

import adminClient.beans.TableStudent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Jonas on 2016-03-16.
 */
public class TestInformationBox extends HBox {
    private ColumnConstraints column1 = new ColumnConstraints();
    private VBox listViewBox = new VBox(5);

    private Label testName = new Label("Provnamn:");
    private Label subjectLabel = new Label("Ämne:");
    private Label timeLabel = new Label("Tid i minuter:");
    private Label questionAmount = new Label("Antal frågor:");
    private Label created = new Label("Skapad:");
    private Label students = new Label("Elever som har tillgång till provet:");

    private Label currTestName = new Label();
    private Label currSubjectLabel = new Label();
    private Label currTimeLabel = new Label();
    private Label currQuestionAmount = new Label();
    private Label currCreated = new Label();

    private ObservableList<TableStudent> studentList = FXCollections.observableArrayList();
    private ListView<TableStudent> studentListView = new ListView<>(studentList);

    public TestInformationBox(String testNameIn, String subjectLabelIn, String timeLabelIn, String questionAmountIn, String createdIn) {
        GridPane gridPane = new GridPane();

        currTestName.setText(testNameIn);
        currSubjectLabel.setText(subjectLabelIn);
        currTimeLabel.setText(timeLabelIn);
        currQuestionAmount.setText(questionAmountIn);
        currCreated.setText(createdIn);

        column1.setHalignment(HPos.RIGHT);

        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.getColumnConstraints().add(column1);
        gridPane.setAlignment(Pos.TOP_CENTER);

        studentListView.setPrefWidth(120);
        studentListView.setPrefHeight(200);

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

        listViewBox.getChildren().addAll(students, studentListView);
        this.getChildren().addAll(gridPane,listViewBox);

    }

    public void addStudent(TableStudent student){
        studentList.add(student);
    }

    public long getSelectedStudentId(){
        return studentListView.getSelectionModel().getSelectedItem().getPersNumber();
    }
}
