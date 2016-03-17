package adminClient.gui;

/**
 * Class for creating a "Correct test-form".
 */

import adminClient.beans.AnswerSubmited;
import adminClient.beans.Question;
import adminClient.beans.SchoolTest;
import adminClient.beans.SubmittedTest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonas on 2016-03-07.
 */

public class CorrectTest extends BorderPane {

    //Layouts:
    private GridPane testGrid = new GridPane();
    private GridPane questionGrid = new GridPane();
    private HBox scoreHbox = new HBox();
    private HBox leftBorderBox = new HBox(10);
    private HBox rightBorderBox = new HBox(10);
    private BorderPane bottomBtnBorderPane = new BorderPane();

    //Textobjects:
    private Text questionHeader = new Text("Rätta fråga: ");

    //Labels:
    private Label testName = new Label("Provnamn:");
    private Label subjectLabel = new Label("Ämne:");
    private Label timeLabel = new Label("Tid i minuter:");
    private Label scoreLabel = new Label("Poäng:");
    private Label schoolTestScore = new Label();

    private Label questionLabel = new Label("Fråga:");
    private Label studentAnswerLabel = new Label("Studentens svar:");
    private Label commentLabel = new Label("Din kommentar:");
    private Label questionCounter = new Label();

    private Label confirmationName = new Label();
    private Label confirmationSubject = new Label();
    private Label confirmationTime = new Label();

    //TextAreas:
    private TextArea questionTextArea = new TextArea();
    private TextArea studentAnswerTextArea = new TextArea();
    private TextArea commentTextArea = new TextArea();

    private TextField scoreTextField = new TextField();

    //Buttons:
    private Button backButton = new Button("Tillbaka");
    private Button correctQuestionBtn = new Button("Rätta fråga");
    private Button doneButton = new Button("Klar");
    private Button nextQuestionBtn = new Button("Nästa fråga");
    private Button lastQuestionBtn = new Button("Föregående fråga");

    //Others:
    private ColumnConstraints column1 = new ColumnConstraints();

    private List<Question> questionList;
    private List<AnswerSubmited> answerSubmitedList;
    private ArrayList<String> correctLabelList = new ArrayList<>();
    private ArrayList<String> commentList = new ArrayList<>();
    private ArrayList<String> scoreList = new ArrayList<>();

    private SchoolTest schoolTest;
    private SubmittedTest submittedTest;
    private Question question;

    private int questionCounterInt = 0;
    private int currentQuestion = 0;

    public CorrectTest(SchoolTest schoolTestIn, SubmittedTest submittedTestIn) {

        this.schoolTest = schoolTestIn;
        this.submittedTest = submittedTestIn;

        questionList = schoolTest.getQuestions();
        answerSubmitedList = submittedTest.getAnswersSubmited();

        for (int i = 0; i < questionList.size(); i++) {
            correctLabelList.add("(Inte rättad)");
            commentList.add("");
            scoreList.add("");
        }

        initTest();
        initQuestion(currentQuestion);

        //Init testGridPane:
        testGrid.setPadding(new Insets(30, 30, 30, 30));
        testGrid.setHgap(10);
        testGrid.setVgap(10);
        testGrid.setStyle(" -fx-background-color: white");
        testGrid.getColumnConstraints().add(column1);
        testGrid.setAlignment(Pos.TOP_CENTER);

        //Init QuestionGridpane:
        questionGrid.setPadding(new Insets(30, 30, 30, 30));
        questionGrid.setHgap(10);
        questionGrid.setVgap(10);
        questionGrid.setStyle(" -fx-background-color: white");
        questionGrid.getColumnConstraints().add(column1);
        questionGrid.setAlignment(Pos.TOP_CENTER);

        //Init BorderPane (this):
        this.setStyle("-fx-background-color: white;");
        this.setTop(testGrid);
        this.setCenter(questionGrid);
        this.setBottom(bottomBtnBorderPane);

        //init components:
        column1.setHalignment(HPos.RIGHT);
        questionHeader.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));

        leftBorderBox.getChildren().addAll(backButton,lastQuestionBtn);
        rightBorderBox.getChildren().addAll(nextQuestionBtn,doneButton);

        bottomBtnBorderPane.setLeft(leftBorderBox);
        bottomBtnBorderPane.setCenter(questionCounter);
        bottomBtnBorderPane.setRight(rightBorderBox);
        scoreHbox.getChildren().addAll(scoreTextField, schoolTestScore);

        questionTextArea.setPrefHeight(400);
        studentAnswerTextArea.setPrefHeight(400);
        commentTextArea.setPrefHeight(400);

        bottomBtnBorderPane.setPadding(new Insets(5, 5, 5, 5));
        backButton.setPrefWidth(110);
        doneButton.setPrefWidth(110);
        nextQuestionBtn.setPrefWidth(110);

        //count the textanswer-questions:
        for (Question aQuestionList : questionList) {
            if (!aQuestionList.isMultiQuestion()) {
                questionCounterInt++;
            }
        }
        questionCounter.setText("Fråga: " + (currentQuestion+1) + " / " + (questionCounterInt));

        scoreTextField.setPrefWidth(40);
        schoolTestScore.setFont(Font.font(14));
        scoreHbox.setAlignment(Pos.CENTER_LEFT);

        //Add components to test-gridpane:
        testGrid.add(testName, 0, 1);
        testGrid.add(subjectLabel, 0, 2);
        testGrid.add(timeLabel, 0, 3);

        testGrid.add(confirmationName, 1, 1);
        testGrid.add(confirmationSubject, 1, 2);
        testGrid.add(confirmationTime, 1, 3);

        //Add components to question-gridpane:
        questionGrid.add(questionHeader, 1, 0);

        questionGrid.add(questionLabel, 0, 1);
        questionGrid.add(questionTextArea, 1, 1);

        questionGrid.add(studentAnswerLabel, 0, 2);
        questionGrid.add(studentAnswerTextArea, 1, 2);

        questionGrid.add(commentLabel, 0, 3);
        questionGrid.add(commentTextArea, 1, 3);

        questionGrid.add(scoreLabel, 0, 4);
        questionGrid.add(scoreHbox, 1, 4);

        questionGrid.add(correctQuestionBtn, 1, 5);

        /**
         * GUI LISTENERS:
         */
        lastQuestionBtn.setOnAction(event -> {
            lastQuestion();
        });

        nextQuestionBtn.setOnAction(event -> {
            nextQuestion();
        });
    }

    void lastQuestion(){
        if (currentQuestion > 0){
            currentQuestion--;
            initQuestion(currentQuestion);
        }
    }
    public void nextQuestion(){
        if (currentQuestion < questionList.size()-1){
            currentQuestion++;
            initQuestion(currentQuestion);
        }
    }

    public void initTest(){
        confirmationName.setText(schoolTest.getName());
        confirmationSubject.setText(schoolTest.getSubject());
        confirmationTime.setText(String.valueOf(schoolTest.getTestTime()));
    }

    public void initQuestion(int questionId) {
        if (!questionList.get(questionId).isMultiQuestion()) {
            question = questionList.get(questionId);
            questionHeader.setText("Rätta fråga: " + (questionId + 1) + " " + correctLabelList.get(questionId));
            schoolTestScore.setText(" / " + question.getPoints() + " poäng");

            questionTextArea.setText(questionList.get(questionId).getQuestionText());
            questionTextArea.setEditable(false);

            studentAnswerTextArea.setText(answerSubmitedList.get(questionId).getAnswerString());
            studentAnswerTextArea.setEditable(false);

            commentTextArea.setText(commentList.get(questionId));

            scoreTextField.setText(scoreList.get(questionId));
        }
    }

    /**
     * Clears the form.
     */
    public int getCurrTest(){
        return schoolTest.getId();
    }

    public int getCurrQuestion(){
        return questionList.get(currentQuestion).getId();
    }

    public String getComment(){
        return commentTextArea.getText();
    }

    public int getPoints(){
        return Integer.parseInt(scoreTextField.getText());
    }

    public void setBackButton (EventHandler<ActionEvent> click){
        backButton.setOnAction(click);
    }

    public void setCorrectQuestionBtn (EventHandler<ActionEvent> click){
        correctQuestionBtn.setOnAction(click);
    }

    public void setDoneButton (EventHandler<ActionEvent> click){
        doneButton.setOnAction(click);
    }

    public void setQuestionCorrected(){
        correctLabelList.set(currentQuestion,"(Rättad)");
        questionHeader.setText("Rätta fråga: " + (currentQuestion + 1) + " " + correctLabelList.get(currentQuestion));
    }

    public void setComment(){
        commentList.set(currentQuestion,commentTextArea.getText());
    }

    public void setScore(){
        scoreList.set(currentQuestion,scoreTextField.getText());
    }
}
