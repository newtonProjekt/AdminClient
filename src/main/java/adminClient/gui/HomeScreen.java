package adminClient.gui;

import adminClient.beans.TestsToCorrect;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Created by Jonas on 2016-03-14.
 */
public class HomeScreen extends HBox {
    private VBox testBox = new VBox(10);
    private VBox userBox = new VBox(10);
    private VBox unCorrectTestBox = new VBox(10);
    private Text testsAmountText = new Text();
    private Text usersAmountText = new Text();
    private Text unCorrectedTestText = new Text();
    private Image testImage = new Image("file:tests-icon.png");
    private ImageView testImageView = new ImageView(testImage);
    private Image userImage = new Image("file:users-icon.png");
    private ImageView userImageView = new ImageView(userImage);
    private Image unCorrectedImage = new Image("file:correctTest-icon.png");
    private ImageView unCorrectedImageView = new ImageView(unCorrectedImage);
    private TableView<TestsToCorrect> unCorrectedTestTableView;
    private Button correctButton = new Button("Rätta prov");

    public HomeScreen(int testsAmount, int usersAmount, TableView<TestsToCorrect> testsToCorrectTableView) {
        this.unCorrectedTestTableView = testsToCorrectTableView;

        GridPane gridPane = new GridPane();

        this.setSpacing(45);
        this.setPadding(new Insets(10,10,10,10));

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        unCorrectTestBox.setPadding(new Insets(10,10,10,10));
        unCorrectTestBox.setSpacing(10);

        testsAmountText.setText("Antal prov: " + testsAmount);
        usersAmountText.setText("Antal studenter: " + usersAmount);
        unCorrectedTestText.setText("Antal orättade prov: " + testsToCorrectTableView.getItems().size());

        testBox.setAlignment(Pos.CENTER);
        testBox.setPadding(new Insets(20,20,20,20));
        testBox.getChildren().addAll(testImageView, testsAmountText);
        testBox.setStyle("-fx-background-color: #e6e6e6; -fx-background-radius: 30;");

        userBox.setAlignment(Pos.CENTER);
        userBox.setPadding(new Insets(20,20,20,20));
        userBox.getChildren().addAll(userImageView, usersAmountText);
        userBox.setStyle("-fx-background-color: #e6e6e6; -fx-background-radius: 30;");

        unCorrectTestBox.setAlignment(Pos.CENTER);
        unCorrectTestBox.setPadding(new Insets(20,20,20,20));
        unCorrectTestBox.getChildren().addAll(unCorrectedImageView, unCorrectedTestText,
                unCorrectedTestTableView, correctButton);
        unCorrectTestBox.setStyle("-fx-background-color: #e6e6e6; -fx-background-radius: 30;");

        gridPane.add(testBox,0,0);
        gridPane.add(userBox,1,0);

        this.getChildren().addAll(gridPane, unCorrectTestBox);
    }

    public void setUnCorrectTests(int amount){
        unCorrectedTestText.setText("Antal orättade prov: " + amount);
    }

    public void clickTestBox(EventHandler<MouseEvent> click){
        testBox.setOnMouseClicked(click);
    }

    public void clickUserBox(EventHandler<MouseEvent> click){
        userBox.setOnMouseClicked(click);
    }

    public void clickCorrectButton(EventHandler<ActionEvent> click){
        correctButton.setOnAction(click);
    }

    public TestsToCorrect getSelectedTestToCorrect(){
        return unCorrectedTestTableView.getSelectionModel().getSelectedItem();

    }
}
