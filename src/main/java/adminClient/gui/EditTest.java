package adminClient.gui;

/**
 * Class for creating a "Add test-form".
 */

import adminClient.beans.Answer;
import adminClient.beans.Question;
import adminClient.beans.SchoolTest;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonas on 2016-03-07.
 */

public class EditTest extends BorderPane {

    //Layouts:
    private GridPane testGrid = new GridPane();
    private GridPane questionGrid = new GridPane();
    private HBox radioButtonBox = new HBox(10);
    private VBox multiAnswerBox;
    private HBox answerHBox = new HBox(10);
    private HBox saveDeleteQuestionBox = new HBox(10);
    private HBox leftBorderBox = new HBox(10);
    private HBox rightBorderBox = new HBox(10);
    private BorderPane bottomBtnBorderPane = new BorderPane();

    //Textobjects:
    private Text testHeader = new Text("Redigera prov:");
    private Text questionHeader = new Text("Redigera fråga: ");

    //Labels:
    private Label testName = new Label("Provnamn:");
    private Label subjectLabel = new Label("Ämne:");
    private Label timeLabel = new Label("Tid i minuter:");
    private Label scoreLabel = new Label("Poäng:");
    private Label answerFormLbl = new Label("Svarsalternativ");
    private Label questionLabel = new Label("Fråga:");
    private Label answerLabel = new Label("Svar:");
    private Label answersAmountLabel = new Label("Antal svar:");
    private Label correctAnswerLabel = new Label("Rätt svar:");
    private Label questionCounter = new Label();
    private Label confirmationName = new Label();
    private Label confirmationSubject = new Label();
    private Label confirmationTime = new Label();
    private Label confirmationQuestions = new Label("Antal frågor:");
    private Label confirmationCounter = new Label();

    //Textfields:
    private TextField testNameTextField = new TextField();
    private TextField subjectTextField = new TextField();
    private TextField timeTextField = new TextField();
    private TextField scoreTextField = new TextField();
    private TextField questionTextField;

    //TextAreas:
    private TextArea questionTextArea = new TextArea();

    //Radiobuttons:
    private ToggleGroup answerRbGroup = new ToggleGroup();
    private ToggleGroup rbGroup = new ToggleGroup();
    private RadioButton textAnswerRb = new RadioButton("Textsvar");
    private RadioButton multiAnswerRb = new RadioButton("Flervalsalternativ");
    private RadioButton gAnswerRb = new RadioButton("G");
    private RadioButton vgAnswerRb = new RadioButton("VG");

    //Buttons:
    private Button proceedBtn = new Button("Vidare");
    private Button backButton = new Button("Tillbaka");
    private Button saveQuestionBtn = new Button("Spara fråga");
    private Button createTestBtn = new Button("Spara prov");
    private Button nextQuestionBtn = new Button("Nästa fråga");
    private Button lastQuestionBtn = new Button("Föregående fråga");
    private Button deleteQuestion = new Button("Radera fråga");
    private Button newQuestion = new Button("Ny fråga");

    //ComboBoxes:
    private ComboBox<Integer> answersAmountCmbBox = new ComboBox();
    private ComboBox<String> correctAnswerCmbBox = new ComboBox();

    //Others:
    private ColumnConstraints column1 = new ColumnConstraints();
    private ArrayList<TextField> answersList = new ArrayList<>();
    private List<Question> questionList;
    private SchoolTest schoolTest;
    private int answerAmount;
    private int questionCounterInt = 0;
    private int currentQuestion = 0;
    private Question question;

    public EditTest(SchoolTest schoolTestIn) {
        this.schoolTest = schoolTestIn;
        questionList = schoolTest.getQuestions();
        initTest();

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
        this.setBottom(bottomBtnBorderPane);

        //init components:
        column1.setHalignment(HPos.RIGHT);
        questionHeader.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        testHeader.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        textAnswerRb.setToggleGroup(rbGroup);
        multiAnswerRb.setToggleGroup(rbGroup);
        gAnswerRb.setToggleGroup(answerRbGroup);
        vgAnswerRb.setToggleGroup(answerRbGroup);
        gAnswerRb.setSelected(true);
        textAnswerRb.setSelected(true);
        radioButtonBox.getChildren().addAll(textAnswerRb, multiAnswerRb);
        leftBorderBox.getChildren().add(backButton);
        bottomBtnBorderPane.setLeft(leftBorderBox);
        bottomBtnBorderPane.setCenter(questionCounter);
        bottomBtnBorderPane.setRight(rightBorderBox);
        answerHBox.getChildren().addAll(scoreTextField, gAnswerRb, vgAnswerRb);
        saveDeleteQuestionBox.getChildren().addAll(saveQuestionBtn,deleteQuestion,newQuestion);
        questionTextArea.setPrefHeight(400);
        questionTextArea.setMinHeight(100);
        answersAmountCmbBox.getItems().addAll(2, 3, 4, 5, 6, 7, 8, 9, 10);
        correctAnswerCmbBox.setPromptText("-Välj-");
        bottomBtnBorderPane.setPadding(new Insets(5, 5, 5, 5));
        backButton.setPrefWidth(110);
        nextQuestionBtn.setPrefWidth(110);
        createTestBtn.setPrefWidth(110);
        testNameTextField.setMinWidth(400);
        subjectTextField.setMinWidth(400);
        timeTextField.setMaxWidth(50);
        scoreTextField.setMaxWidth(40);
        questionCounterInt = questionList.size();
        questionCounter.setText("Antal frågor: " + (questionCounterInt));

        //Add components to test-gridpane:
        testGrid.add(testHeader, 1, 0);
        testGrid.add(testName, 0, 1);
        testGrid.add(subjectLabel, 0, 2);
        testGrid.add(timeLabel, 0, 3);
        testGrid.add(testNameTextField, 1, 1);
        testGrid.add(subjectTextField, 1, 2);
        testGrid.add(timeTextField, 1, 3);
        testGrid.add(proceedBtn, 1, 5);

        //Add components to question-gridpane:
        questionGrid.add(questionHeader, 1, 0);
        questionGrid.add(questionLabel, 0, 1);
        questionGrid.add(answerFormLbl, 0, 2);
        questionGrid.add(questionTextArea, 1, 1);
        questionGrid.add(radioButtonBox, 1, 2);
        questionGrid.add(scoreLabel, 0, 4);
        questionGrid.add(answerHBox, 1, 4);
        questionGrid.add(saveDeleteQuestionBox, 1, 6);

        /**
         * GUI LISTENERS:
         */

        //Listener for "Multianswer question-radiobutton":
        multiAnswerRb.setOnAction(event -> {

            //Remove all the old components and add the right ones:
            questionGrid.getChildren().removeAll(answerHBox, scoreLabel, answerLabel, saveDeleteQuestionBox, answersAmountLabel, answersAmountCmbBox);

            questionGrid.add(answersAmountLabel, 0, 4);
            questionGrid.add(answersAmountCmbBox, 1, 4);
            questionGrid.add(scoreLabel, 0, 7);
            questionGrid.add(answerHBox, 1, 7);
            questionGrid.add(saveDeleteQuestionBox, 1, 8);
            Event.fireEvent(answersAmountCmbBox, new ActionEvent());
        });

        //Listener for the "Textstring answer-radiobutton":
        textAnswerRb.setOnAction(event -> {

            //Remove all the old components and add the right ones:
            questionGrid.getChildren().removeAll(answerHBox, scoreLabel, answerLabel, saveDeleteQuestionBox, multiAnswerBox, correctAnswerCmbBox, answersAmountLabel, correctAnswerLabel, answersAmountCmbBox);
            questionGrid.add(scoreLabel, 0, 4);
            questionGrid.add(answerHBox, 1, 4);
            questionGrid.add(saveDeleteQuestionBox, 1, 5);
        });

        //Listener for amount of answers in a multi answer-question:
        answersAmountCmbBox.setOnAction(event -> {

            //Clear the old answer-list and remove all old components:
            answersList.clear();
            questionGrid.getChildren().removeAll(answerLabel, multiAnswerBox, correctAnswerLabel, correctAnswerCmbBox);

            //Save the amount in a variable, create a VBox:
            answerAmount = answersAmountCmbBox.getSelectionModel().getSelectedItem();
            multiAnswerBox = new VBox(5);

            //Create as many textfield as the amount is, add them to a arraylist and to the VBox we just created:
            for (int i = 0; i < answerAmount; i++) {
                questionTextField = new TextField();
                answersList.add(questionTextField);
                multiAnswerBox.getChildren().add(questionTextField);
            }

            //Add relevant components to the gridpane:
            questionGrid.add(answerLabel, 0, 5);
            questionGrid.add(multiAnswerBox, 1, 5);
            questionGrid.add(correctAnswerLabel, 0, 6);
            questionGrid.add(correctAnswerCmbBox, 1, 6);

        });

        //Listener for choosing the right answer:
        correctAnswerCmbBox.setOnMousePressed(event -> {

            //Clear the old answers:
            correctAnswerCmbBox.getItems().clear();

            //Loop through the arraylist with all the answer-textfield and add the answers to the combobox:
            answersList.forEach(answer -> {
                if (!answer.getText().equals("")) {
                    correctAnswerCmbBox.getItems().add(answer.getText());
                }
            });
        });

        lastQuestionBtn.setOnAction(event -> {
            System.out.println("minus " + currentQuestion);
            if (currentQuestion > 0){
                currentQuestion--;
                initQuestion(currentQuestion);
            }
        });

        nextQuestionBtn.setOnAction(event -> {
            if (currentQuestion < questionList.size()-1){
                currentQuestion++;
                initQuestion(currentQuestion);
            }
        });

        newQuestion.setOnAction(event -> {
            newQuestion();
        });
    }

    public void initTest(){
        testNameTextField.setText(schoolTest.getName());
        subjectTextField.setText(schoolTest.getSubject());
        timeTextField.setText(String.valueOf(schoolTest.getTestTime()));

    }

    public void initQuestion(int questionId){
        question = questionList.get(questionId);
        List<Answer> answerList = question.getAnswers();

        questionHeader.setText("Redigera fråga: " + (questionId+1));

        questionTextArea.setText(question.getQuestionText());
        if (question.isMultiQuestion()){
            answersAmountCmbBox.setValue(answerList.size());
            multiAnswerRb.fire();
            for (int i = 0; i < answersList.size(); i++) {
                answersList.get(i).setText(answerList.get(i).getAnswerText());
            }
        }
        else{
            textAnswerRb.fire();
        }
        for (int i = 0; i < answerList.size(); i++) {
            if (answerList.get(i).isCorrectAnswer()){
                correctAnswerCmbBox.setValue(answerList.get(i).getAnswerText());
            }
        }
        scoreTextField.setText(String.valueOf(question.getPoints()));
    }

    public void removeQuestion(){
            currentQuestion = 0;
            initQuestion(currentQuestion);
            questionCounterInt = questionList.size();
            questionCounter.setText("Antal frågor: " + questionCounterInt);
    }

    public GridPane confirmationBox(){
        GridPane gridPane = new GridPane();

        confirmationCounter.setText(String.valueOf(questionCounterInt));

        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.getColumnConstraints().add(column1);
        gridPane.setAlignment(Pos.TOP_CENTER);

        gridPane.add(testName,0,0);
        gridPane.add(subjectLabel,0,1);
        gridPane.add(timeLabel,0,2);
        gridPane.add(confirmationQuestions,0,3);

        confirmationName.setText(testNameTextField.getText());
        confirmationSubject.setText(subjectTextField.getText());
        confirmationTime.setText(timeTextField.getText());

        gridPane.add(confirmationName,1,0);
        gridPane.add(confirmationSubject,1,1);
        gridPane.add(confirmationTime,1,2);
        gridPane.add(confirmationCounter,1,3);

        this.setPrefSize(400,400);

        return gridPane;

    }

    /**
     * Getters & Setters:
     */
    public String getTestName() {
        return testNameTextField.getText();
    }

    public String getSubject() {
        return subjectTextField.getText();
    }

    public int getTestTime() {
        return Integer.parseInt(timeTextField.getText());
    }

    public boolean getMultiAnswerSelected() {
        return multiAnswerRb.isSelected();
    }

    public String getQuestion() {
        return questionTextArea.getText();
    }

    public ArrayList<TextField> getMultiAnswerList() {
        return answersList;
    }

    public String getCorrectAnswer() {
        return correctAnswerCmbBox.getSelectionModel().getSelectedItem();
    }

    public int getQuestionPoint() {
        return Integer.parseInt(scoreTextField.getText());
    }

    public boolean getVgQuestion() {
        return vgAnswerRb.isSelected();
    }

    public Question getCurrQuestion(){
        return question;
    }

    /**
     * Clears the form.
     */
    void clearForm() {
        questionTextArea.clear();
        correctAnswerCmbBox.setValue(null);
    }

    public void newQuestion(){
        question = null;
        currentQuestion = questionList.size();
        questionHeader.setText("Skapa fråga: " + (currentQuestion+1));
        clearForm();
    }

    /**
     * Listener for the "Proceed"-button.
     * @param buttonListener
     */
    public void editTestProceedBtn(EventHandler<ActionEvent> buttonListener) {
        proceedBtn.setOnAction(buttonListener);
    }

    /**
     * Init after clicking the proceedbutton:
     */
    public void editTestInitProceed() {
        testNameTextField.setDisable(true);
        subjectTextField.setDisable(true);
        timeTextField.setDisable(true);
        proceedBtn.setDisable(true);
        this.setCenter(questionGrid);
        initQuestion(currentQuestion);

        leftBorderBox.getChildren().clear();
        rightBorderBox.getChildren().clear();
        leftBorderBox.getChildren().addAll(backButton,lastQuestionBtn);
        rightBorderBox.getChildren().addAll(nextQuestionBtn,createTestBtn);
    }

    /**
     * Listener for the "Save Question"-button.
     * @param buttonlistener
     */
    public void editTestSaveQuestionBtn(EventHandler<ActionEvent> buttonlistener) {
        saveQuestionBtn.setOnAction(buttonlistener);
    }

    /**
     * Listener for the "Delete Question"-button.
     * @param buttonlistener
     */
    public void editTestDeleteQuestionBtn(EventHandler<ActionEvent> buttonlistener){
        deleteQuestion.setOnAction(buttonlistener);
    }

    /**
     * Init after clicking the "Save Question"-button.
     */
    public void editTestInitSaveQuestion() {
        questionCounterInt = questionList.size();
        questionCounter.setText("Antal frågor: " + questionCounterInt);
    }

    /**
     * Listener for the "Create Test"-Button.
     * @param buttonListener
     */
    public void editTestMergeTestBtn(EventHandler<ActionEvent> buttonListener) {
        createTestBtn.setOnAction(buttonListener);
    }

    /**
     * Listener for the "Start Over"-button.
     * @param buttonlistener
     */
    public void editTestBackBtn(EventHandler<ActionEvent> buttonlistener) {
        backButton.setOnAction(buttonlistener);
    }
}
