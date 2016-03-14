package adminClient.gui;

/**
 * Class for creating a "Add test-form".
 */

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

/**
 * Created by Jonas on 2016-03-07.
 */

public class AddTest extends BorderPane {

    //Layouts:
    private GridPane testGrid = new GridPane();
    private GridPane questionGrid = new GridPane();
    private HBox radioButtonBox = new HBox(10);
    private VBox multiAnswerBox;
    private HBox answerHBox = new HBox(10);
    private BorderPane bottomBtnBorderPane = new BorderPane();

    //Textobjects:
    private Text testHeader = new Text("Skapa nytt prov:");
    private Text questionHeader = new Text("Skapa ny fråga:");

    //Labels:
    private Label testName = new Label("Provnamn:");
    private Label subjectLabel = new Label("Ämne:");
    private Label timeLabel = new Label("Tid i minuter:");
    private Label scoreLabel = new Label("Poäng:");
    private Label selfCorrectingLabel = new Label("Självrättande prov?");
    private Label answerFormLbl = new Label("Svarsalternativ");
    private Label questionLabel = new Label("Fråga:");
    private Label answerLabel = new Label("Svar:");
    private Label answersAmountLabel = new Label("Antal svar:");
    private Label correctAnswerLabel = new Label("Rätt svar:");
    private Label questionCounter = new Label();

    //Textfields:
    private TextField testNameTextField = new TextField();
    private TextField subjectTextField = new TextField();
    private TextField timeTextField = new TextField();
    private TextField scoreTextField = new TextField();
    private TextField questionTextField;

    //TextAreas:
    private TextArea questionTextArea = new TextArea();

    //CheckBoxes:
    private CheckBox selfCorrectingBox = new CheckBox();

    //Radiobuttons:
    private ToggleGroup answerRbGroup = new ToggleGroup();
    private ToggleGroup rbGroup = new ToggleGroup();
    private RadioButton textAnswerRb = new RadioButton("Textsvar");
    private RadioButton multiAnswerRb = new RadioButton("Flervalsalternativ");
    private RadioButton gAnswerRb = new RadioButton("G");
    private RadioButton vgAnswerRb = new RadioButton("VG");

    //Buttons:
    private Button proceedBtn = new Button("Vidare");
    private Button startOverBtn = new Button("Börja om");
    private Button saveQuestionBtn = new Button("Spara fråga");
    private Button createTestBtn = new Button("Skapa prov");

    //ComboBoxes:
    private ComboBox<Integer> answersAmountCmbBox = new ComboBox();
    private ComboBox<String> correctAnswerCmbBox = new ComboBox();

    //Others:
    private ColumnConstraints column1 = new ColumnConstraints();
    private ArrayList<TextField> answersList = new ArrayList<>();
    private int answerAmount;
    private int questionCounterInt = 1;

    public AddTest() {

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
        bottomBtnBorderPane.setLeft(startOverBtn);
        bottomBtnBorderPane.setCenter(questionCounter);
        bottomBtnBorderPane.setRight(createTestBtn);
        answerHBox.getChildren().addAll(scoreTextField, gAnswerRb, vgAnswerRb);
        questionTextArea.setPrefHeight(400);
        questionTextArea.setMinHeight(100);
        answersAmountCmbBox.getItems().addAll(2, 3, 4, 5, 6, 7, 8, 9, 10);
        answersAmountCmbBox.setValue(3);
        correctAnswerCmbBox.setPromptText("-Välj-");
        bottomBtnBorderPane.setPadding(new Insets(5, 5, 5, 5));
        startOverBtn.setPrefWidth(90);
        createTestBtn.setPrefWidth(90);
        testNameTextField.setMinWidth(400);
        subjectTextField.setMinWidth(400);
        timeTextField.setMaxWidth(50);
        scoreTextField.setMaxWidth(40);

        //Add components to test-gridpane:
        testGrid.add(testHeader, 1, 0);
        testGrid.add(testName, 0, 1);
        testGrid.add(subjectLabel, 0, 2);
        testGrid.add(timeLabel, 0, 3);
        testGrid.add(selfCorrectingLabel, 0, 4);
        testGrid.add(testNameTextField, 1, 1);
        testGrid.add(subjectTextField, 1, 2);
        testGrid.add(timeTextField, 1, 3);
        testGrid.add(selfCorrectingBox, 1, 4);
        testGrid.add(proceedBtn, 1, 5);

        //Add components to question-gridpane:
        questionGrid.add(questionHeader, 1, 0);
        questionGrid.add(questionLabel, 0, 1);
        questionGrid.add(answerFormLbl, 0, 2);
        questionGrid.add(questionTextArea, 1, 1);
        questionGrid.add(radioButtonBox, 1, 2);
        questionGrid.add(scoreLabel, 0, 4);
        questionGrid.add(answerHBox, 1, 4);
        questionGrid.add(saveQuestionBtn, 1, 6);

        /**
         * GUI LISTENERS:
         */

        //Listener for "Selfcorrecting test-checkbox":
        selfCorrectingBox.selectedProperty().addListener((observable, oldValue, newValue) -> {

            //If it is a selfcorrecting test, it can only be a multi answered-question:
            if (selfCorrectingBox.isSelected()) {
                multiAnswerRb.fire();
                textAnswerRb.setDisable(true);
            } else {
                textAnswerRb.setDisable(false);
            }
        });

        //Listener for "Multianswer question-radiobutton":
        multiAnswerRb.setOnAction(event -> {

            //Remove all the old components and add the right ones:
            questionGrid.getChildren().removeAll(answerHBox, scoreLabel, answerLabel, saveQuestionBtn, answersAmountLabel, answersAmountCmbBox);

            questionGrid.add(answersAmountLabel, 0, 4);
            questionGrid.add(answersAmountCmbBox, 1, 4);
            answersAmountCmbBox.setValue(3);
            Event.fireEvent(answersAmountCmbBox, new ActionEvent());
        });

        //Listener for the "Textstring answer-radiobutton":
        textAnswerRb.setOnAction(event -> {

            //Remove all the old components and add the right ones:
            questionGrid.getChildren().removeAll(answerHBox, scoreLabel, answerLabel, saveQuestionBtn, multiAnswerBox, correctAnswerCmbBox, answersAmountLabel, correctAnswerLabel, answersAmountCmbBox);
            questionGrid.add(scoreLabel, 0, 4);
            questionGrid.add(answerHBox, 1, 4);
            questionGrid.add(saveQuestionBtn, 1, 5);
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

        //Listener for when the right answer is chosen, show the "Save question"-button:
        correctAnswerCmbBox.setOnAction(event -> {

            //If the value is not null, show save question-button:
            if (correctAnswerCmbBox.getValue() != null) {
                questionGrid.getChildren().remove(saveQuestionBtn);
                questionGrid.add(scoreLabel, 0, 7);
                questionGrid.add(answerHBox, 1, 7);
                questionGrid.add(saveQuestionBtn, 1, 8);
            }
        });
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

    /**
     * Clears the form.
     */
    void clearForm() {
        questionTextArea.clear();
        correctAnswerCmbBox.setValue(null);
    }

    /**
     * Listener for the "Proceed"-button.
     * @param buttonListener
     */
    public void proceedBtnListener(EventHandler<ActionEvent> buttonListener) {
        proceedBtn.setOnAction(buttonListener);
    }

    /**
     * Init after clicking the proceedbutton:
     */
    public void initProceedBtn() {
        testNameTextField.setDisable(true);
        subjectTextField.setDisable(true);
        timeTextField.setDisable(true);
        selfCorrectingBox.setDisable(true);
        proceedBtn.setDisable(true);
        this.setCenter(questionGrid);
    }

    /**
     * Listener for the "Save Question"-button.
     * @param buttonlistener
     */
    public void saveQuestionBtnListener(EventHandler<ActionEvent> buttonlistener) {
        saveQuestionBtn.setOnAction(buttonlistener);
    }

    /**
     * Init after clicking the "Save Question"-button.
     */
    public void initSaveQuestionBtn() {

        //If it is a multi answer-question:
        if (multiAnswerRb.isSelected()) {
            //Remove all the old components and add the right ones:
            questionGrid.getChildren().remove(saveQuestionBtn);
            Event.fireEvent(multiAnswerRb, new ActionEvent());
        }
        clearForm();
        questionCounter.setText("Antal frågor: " + (questionCounterInt++));
    }

    /**
     * Listener for the "Create Test"-Button.
     * @param buttonListener
     */
    public void createTestBtnListener(EventHandler<ActionEvent> buttonListener) {
        createTestBtn.setOnAction(buttonListener);
    }

    /**
     * Listener for the "Start Over"-button.
     * @param buttonlistener
     */
    public void startOverBtnListener(EventHandler<ActionEvent> buttonlistener) {
        startOverBtn.setOnAction(buttonlistener);
    }
}
