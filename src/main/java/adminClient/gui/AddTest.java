package adminClient.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Jonas on 2016-03-07.
 */
public class AddTest extends GridPane{
    //Components:
    private Text headerText = new Text("Skapa nytt prov:");
    private Label selfCorrectingLabel = new Label("Självrättande prov?");
    private CheckBox selfCorrectingBox = new CheckBox();
    private Label questionLabel = new Label("Fråga:");
    private TextArea questionTextArea = new TextArea();
    private TextField questionTextField;
    private Label answerFormLbl = new Label("Svarsalternativ");
    private Label answerLabel = new Label("Svar:");
    private TextArea answerTextArea = new TextArea();
    private HBox radioButtonBox = new HBox(10);
    private ToggleGroup rbGroup = new ToggleGroup();
    private RadioButton textAnswerRb = new RadioButton("Textsvar");
    private RadioButton multiAnswerRb = new RadioButton("Flervalsalternativ");
    private Label answersAmountLabel = new Label("Antal svar:");
    private Label correctAnswerLabel = new Label("Välj det rätta svaret:");
    private ComboBox<Integer> answersAmountCmbBox = new ComboBox();
    private ComboBox<String> correctAnswerCmbBox = new ComboBox();
    private VBox multiAnswerBox;
    private HBox buttonHbox = new HBox(220);
    private ArrayList<TextField> answersList = new ArrayList<>();
    private Button saveQuestionBtn = new Button("Spara fråga");
    private Button createTestBtn = new Button("Skapa Prov");
    private ColumnConstraints column1 = new ColumnConstraints();
    private int answerAmount;

    public AddTest() {
        //Init gridpane:
        this.setPadding(new Insets(30,30,30,30));
        this.setHgap(10);
        this.setVgap(10);
        this.setStyle("-fx-border-color: #e6e6e6; -fx-background-color: white");
        this.getColumnConstraints().add(column1);
        this.setAlignment(Pos.TOP_CENTER);

        //init components:
        column1.setHalignment(HPos.RIGHT);
        headerText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        textAnswerRb.setToggleGroup(rbGroup);
        multiAnswerRb.setToggleGroup(rbGroup);
        textAnswerRb.setSelected(true);
        radioButtonBox.getChildren().addAll(textAnswerRb,multiAnswerRb);
        buttonHbox.getChildren().addAll(saveQuestionBtn, createTestBtn);
        questionTextArea.setPrefWidth(400);
        questionTextArea.setMinHeight(400);
        questionTextArea.setPrefHeight(100);
        questionTextArea.setMinHeight(100);
        answerTextArea.setPrefWidth(400);
        answerTextArea.setPrefHeight(100);
        answersAmountCmbBox.getItems().addAll(2,3,4,5,6,7,8,9,10);
        answersAmountCmbBox.setPromptText("0");
        correctAnswerCmbBox.setPromptText("-Välj-");


        //Add components to gridpane:
        this.add(headerText,1,0);
        this.add(selfCorrectingLabel,0,1);
        this.add(questionLabel,0,2);
        this.add(answerFormLbl,0,3);
        this.add(selfCorrectingBox,1,1);
        this.add(questionTextArea,1,2);
        this.add(radioButtonBox,1,3);
        this.add(answerLabel,0,4);
        this.add(answerTextArea,1,4);
        this.add(buttonHbox,1,5);

        //Listener for "Selfcorrecting test-checkbox":
        selfCorrectingBox.selectedProperty().addListener((observable, oldValue, newValue) -> {

            //If it is a selfcorrecting test, it can only be a multi answered-question:
            if (selfCorrectingBox.isSelected()){
                multiAnswerRb.fire();
                textAnswerRb.setDisable(true);
            }
            else{
                textAnswerRb.setDisable(false);
            }
        });

        //Listener for "Multianswer question-radiobutton":
        multiAnswerRb.setOnAction(event -> {

            //Remove all the old components and add the right ones:
            this.getChildren().removeAll(answerLabel,answerTextArea,buttonHbox);

            this.add(answersAmountLabel,0,4);
            this.add(answersAmountCmbBox,1,4);
        });

        //Listener for the "Textstring answer-radiobutton":
        textAnswerRb.setOnAction(event -> {

            //Remove all the old components and add the right ones:
            this.getChildren().removeAll(answerLabel, answerTextArea, buttonHbox, multiAnswerBox, correctAnswerCmbBox, answersAmountLabel, correctAnswerLabel, answersAmountCmbBox);
            this.add(answerLabel,0,4);
            this.add(answerTextArea,1,4);
            this.add(buttonHbox,1,5);
        });

        //Listener for amount of answers in a multi answer-question:
        answersAmountCmbBox.setOnAction(event -> {

            if (answersAmountCmbBox.getSelectionModel().getSelectedItem() != 0) {
                //Clear the old answer-list and remove all old components:
                answersList.clear();
                this.getChildren().removeAll(answerLabel, multiAnswerBox, correctAnswerLabel, correctAnswerCmbBox);

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
                this.add(answerLabel, 0, 5);
                this.add(multiAnswerBox, 1, 5);
                this.add(correctAnswerLabel, 0, 6);
                this.add(correctAnswerCmbBox, 1, 6);
            }
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
                this.getChildren().remove(buttonHbox);
                this.add(buttonHbox, 1, 7);
            }
        });

        //Listener for the "Save question"-button:
        saveQuestionBtn.setOnAction(event -> {

            //If it is a text-string question:
            if (textAnswerRb.isSelected()){

            }

            //If it is a multi answer-question:
            if (multiAnswerRb.isSelected()){
                //Remove all the old components and add the right ones:
                this.getChildren().removeAll(answerLabel, answersAmountLabel, answersAmountCmbBox, answerTextArea, buttonHbox, multiAnswerBox, correctAnswerCmbBox, correctAnswerLabel);

                this.add(answersAmountLabel,0,4);
                this.add(answersAmountCmbBox,1,4);

            }

            clearForm();

        });
    }


    void clearForm(){
        answersList.clear();
        questionTextArea.clear();
        answerTextArea.clear();
        selfCorrectingBox.setDisable(true);
        answersAmountCmbBox.setValue(0);
        correctAnswerCmbBox.setValue(null);



    }

    public void createTestBtnListener (EventHandler<ActionEvent> buttonListener){
        createTestBtn.setOnAction(buttonListener);
    }



}
