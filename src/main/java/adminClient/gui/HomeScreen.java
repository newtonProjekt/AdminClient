package adminClient.gui;

import adminClient.beans.SchoolTest;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
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
    private ObservableList<SchoolTest> unCorrectedTestList;
    private ListView<SchoolTest> unCorrectedTestListView = new ListView<>(unCorrectedTestList);

    public HomeScreen(int testsAmount, int usersAmount) {
        GridPane gridPane = new GridPane();

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10,10,10,10));

        unCorrectTestBox.setPadding(new Insets(10,10,10,10));
        unCorrectTestBox.setSpacing(10);

        testsAmountText.setText("Antal prov: " + testsAmount);
        usersAmountText.setText("Antal studenter: " + usersAmount);
        unCorrectedTestText.setText("Antal orättade prov: ");

        unCorrectedTestListView.setStyle("-fx-background-radius: 10;");

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
        unCorrectTestBox.getChildren().addAll(unCorrectedImageView, unCorrectedTestText, unCorrectedTestListView);
        unCorrectTestBox.setStyle("-fx-background-color: #e6e6e6; -fx-background-radius: 30;");

        gridPane.add(testBox,0,0);
        gridPane.add(userBox,1,0);

        this.getChildren().addAll(gridPane, unCorrectTestBox);
    }

    public void clickTestBox(EventHandler<MouseEvent> click){
        testBox.setOnMouseClicked(click);
    }

    public void clickUserBox(EventHandler<MouseEvent> click){
        userBox.setOnMouseClicked(click);
    }
}
