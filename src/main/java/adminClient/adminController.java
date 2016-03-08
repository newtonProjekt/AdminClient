package adminClient;

/**
 * The adminController.
 */

import adminClient.beans.Login;
import adminClient.beans.StudentClass;
import adminClient.beans.Test;
import adminClient.beans.User;
import adminClient.gui.AdminView;
import adminClient.gui.LoginBox;
import adminClient.gui.TestTable;
import adminClient.gui.UserTable;
import adminClient.network.CommandHandler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * Created by Jonas on 2016-03-02.
 */

public class AdminController extends Application{
    private AdminView view;
    private CommandHandler commandHandler;
    private LoginBox loginBox;

    //Components for tables:
    private TableView<User> userTableView = new UserTable();
    private TableView<Test> testTableView = new TestTable();
    private ObservableList<Test> testObservableList = FXCollections.observableArrayList();
    private ObservableList<User> userObservableList = FXCollections.observableArrayList();
    private ObservableList<StudentClass> studentClassObservableList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Create objects of a CommandHandler and a LoginBox:
        commandHandler = new CommandHandler();
        loginBox = new LoginBox();

        //Start listening for actions on the loginbutton:
        loginBox.loginButtonListener(event -> {
            login();
        });

        //Show the loginbox and wait for action:
        loginBox.showAndWait();

        //If the login goes well, proceed building the rest of the application:
        view = new AdminView(primaryStage);
        //Set tables and connect observablelists to them:
        view.setUserTableView(userTableView);
        view.setTestTableView(testTableView);
        testTableView.setItems(testObservableList);
        userTableView.setItems(userObservableList);

        // TEST_AREA ---------------------------------------------------------------------------------------------------

        testObservableList.addAll(
                new Test("Delprov 1, JavaFX","Utveckling av desktopapplikationer","2016-01-28"),
                new Test("Delprov 2, JavaEE","Utveckling av desktopapplikationer","2016-02-15"),
                new Test("Delprov 1, HTML och CSS","Utveckling av webbapplikationer","2016-03-03")
        );


        //ADD CLASS
        view.addClassBtnListener(event1 -> {
            String studentClass = view.getStudentClass();
            studentClassObservableList.add(new StudentClass(studentClass));
            view.clearAddClassTextField();
        });


        //ADD USER:
        view.addUserBtnListener(event -> {
            String fName = view.getFname();
            String lName = view.getLname();
            String pNumb = view.getPnumb();
            StudentClass studentClass = view.getSelectedClass();

            userObservableList.add(new User(pNumb,fName,lName,studentClass));

            view.clearAddUserTextFields();
        });

        //DELETE CLASS
        view.deleteTestBtnListener(event -> testObservableList.remove(view.getSelectedTest()));

        //DELETE USER
        view.deleteUserBtnListener(event -> userObservableList.remove(view.getSelectedUser()));

        //IF CLASSLIST CHANGED, UPDATE COMBOBOX:
        studentClassObservableList.addListener((ListChangeListener<StudentClass>) c -> {
            view.addUserComboBox(studentClassObservableList);
        });


        //--------------------------------------------------------------------------------------------------------------
    }

    void login(){
        String username = loginBox.getUserTextField();
        String password = loginBox.getPasswordField();

        if (username.equals("admin") && password.equals("admin")){
            //Create a login-bean:
            Login userLogin = new Login(username,password);
            //Send it to the commandhandler:
            commandHandler.createLoginJson(userLogin);
            //Close the loginbox:
            loginBox.close();
        }
        else {
            loginBox.setErrorLabel("Felaktigt användarnamn eller lösenord.");
        }

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
