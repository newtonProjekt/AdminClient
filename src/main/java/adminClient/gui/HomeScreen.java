package adminClient.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Created by Jonas on 2016-03-14.
 */
public class HomeScreen extends GridPane{
    private VBox testBox = new VBox(10);
    private VBox userBox = new VBox(10);
    private Text testsAmountText = new Text();
    private Text usersAmountText = new Text();
    private Image testImage = new Image("file:tests-icon.png");
    private ImageView testImageView = new ImageView(testImage);
    private Image userImage = new Image("file:users-icon.png");
    private ImageView userImageView = new ImageView(userImage);

    public HomeScreen(int testsAmount, int usersAmount) {
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(10,10,10,10));

        testsAmountText.setText("Antal prov: " + testsAmount);
        usersAmountText.setText("Antal studenter: " + usersAmount);

        testBox.setAlignment(Pos.CENTER);
        testBox.setPadding(new Insets(20,20,20,20));
        testBox.getChildren().addAll(testImageView, testsAmountText);
        testBox.setStyle("-fx-background-color: #e6e6e6; -fx-background-radius: 30;");

        userBox.setAlignment(Pos.CENTER);
        userBox.setPadding(new Insets(20,20,20,20));
        userBox.getChildren().addAll(userImageView, usersAmountText);
        userBox.setStyle("-fx-background-color: #e6e6e6; -fx-background-radius: 30;");

        this.add(testBox,0,0);
        this.add(userBox,1,0);

    }
}
