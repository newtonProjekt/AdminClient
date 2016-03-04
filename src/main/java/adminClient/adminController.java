package adminClient;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Jonas on 2016-03-02.
 */

public class AdminController extends Application{
    AdminView view;

    @Override
    public void start(Stage primaryStage) throws Exception {
        view = new AdminView(primaryStage);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
