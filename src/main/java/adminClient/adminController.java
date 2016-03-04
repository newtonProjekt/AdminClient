package adminClient;

/**
 * The adminController.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * Created by Jonas on 2016-03-02.
 */

public class AdminController extends Application{
    private AdminView view;

    private TableView<User> userTableView = new UserTable();
    private TableView<Test> testTableView = new TestTable();
    private ObservableList<Test> testObservableList = FXCollections.observableArrayList();
    private ObservableList<User> userObservableList = FXCollections.observableArrayList();
    private Stage loginStage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        view = new AdminView(primaryStage);

        view.setUserTableView(userTableView);
        view.setTestTableView(testTableView);
        

        // TEST --------------------------------------------------------------------------------------------------------

        testObservableList.addAll(
                new Test("Delprov 1, JavaFX","Utveckling av desktopapplikationer","2016-01-28"),
                new Test("Delprov 2, JavaEE","Utveckling av desktopapplikationer","2016-02-15"),
                new Test("Delprov 1, HTML och CSS","Utveckling av webbapplikationer","2016-03-03")
        );

        userObservableList.addAll(
                new User("770642-8913","Ted","Borg","Java1"),
                new User("870705-8914","Godzilla","Hårdisksson",".Net1"),
                new User("8905047712", "Romeo", "Olsson", "Poesi2")
        );

        view.deleteTestBtnListener(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                testObservableList.remove(view.getSelectedTest());
            }
        });

        //--------------------------------------------------------------------------------------------------------------

        testTableView.setItems(testObservableList);
        userTableView.setItems(userObservableList);

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
