package adminClient;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by Jonas on 2016-03-04.
 */
public class LoginBox {
    Text scenetitle = new Text("Välkommen");
    Label userName = new Label("Användarnamn:");
    TextField userTextField = new TextField();
    PasswordField pwBox = new PasswordField();
    Button btn = new Button("Logga in");
    HBox hbBtn = new HBox(10);
    Scene scene;

    GridPane grid = new GridPane();

    public LoginBox() {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(userName, 0, 1);

        grid.add(userTextField, 1, 1);

        Label pw = new Label("Lösenord:");
        grid.add(pw, 0, 2);

        grid.add(pwBox, 1, 2);

        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        scene = new Scene(grid, 300, 275);

    }

    public Scene createLoginBox(){
        return scene;
    }
}
