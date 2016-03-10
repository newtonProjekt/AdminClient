package adminClient;

/**
 * The adminController.
 */

import adminClient.beans.*;
import adminClient.gui.AdminView;
import adminClient.gui.LoginBox;
import adminClient.gui.TestTable;
import adminClient.gui.UserTable;
import adminClient.network.CommandHandler;
import adminClient.network.NetworkConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Jonas on 2016-03-02.
 */

public class AdminController extends Application{
    private AdminView view;
    private CommandHandler commandHandler;
    private LoginBox loginBox;
    private SchoolTest schoolTest;

    //Components for tables:
    private TableView<Student> userTableView = new UserTable();
    private TableView<SchoolTest> testTableView = new TestTable();
    private ObservableList<SchoolTest> testObservableList = FXCollections.observableArrayList();
    private ObservableList<Student> studentObservableList = FXCollections.observableArrayList();
    private ObservableList<NewtonClass> studentClassObservableList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Create objects of a CommandHandler and a LoginBox:

        commandHandler = new CommandHandler();
        NetworkConnection networkConnection = new NetworkConnection("127.0.0.1",3004,commandHandler);
        commandHandler.registerServer(networkConnection);
        Thread networkThread = new Thread(networkConnection);
        networkThread.start();

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
        userTableView.setItems(studentObservableList);

        // TEST_AREA ---------------------------------------------------------------------------------------------------

        testObservableList.addAll(
                new SchoolTest("Delprov 1, JavaFX","Utveckling av desktopapplikationer",120),
                new SchoolTest("Delprov 2, JavaEE","Utveckling av desktopapplikationer",120),
                new SchoolTest("Delprov 1, HTML och CSS","Utveckling av webbapplikationer",120)
        );


        //ADD CLASS
        view.addClassBtnListener(event1 -> {
            String newtonClass = view.getStudentClass();
            studentClassObservableList.add(new NewtonClass(newtonClass));
            view.clearAddClassTextField();
        });


        //ADD USER:
        view.addUserBtnListener(event -> {
            String fName = view.getFname();
            String lName = view.getLname();
            long pNumb = Long.parseLong(view.getPnumb());
            NewtonClass newtonClass = view.getSelectedClass();

            studentObservableList.add(new Student(pNumb,fName,lName,newtonClass));

            view.clearAddUserTextFields();
        });

        //DELETE CLASS
        view.deleteTestBtnListener(event -> testObservableList.remove(view.getSelectedTest()));

        //DELETE USER
        view.deleteUserBtnListener(event -> studentObservableList.remove(view.getSelectedUser()));

        //IF CLASSLIST CHANGED, UPDATE COMBOBOX:
        studentClassObservableList.addListener((ListChangeListener<NewtonClass>) c -> {
            view.addUserComboBox(studentClassObservableList);
        });

        //--------------------------------------------------------------------------------------------------------------

        //Listener for proceeding when creating a test:
        view.proceedBtnListener(event -> {

            String testName = view.getTestName();
            String subjectName = view.getSubjectName();
            int testTime = view.getTestTime();

            //Create an object of SchoolTest:
            schoolTest = new SchoolTest(testName,subjectName,testTime);

            //commandHandler.createSchoolTest(schoolTest);
            view.initProceedBtn();

        });

        view.saveQuestionBtnListener(event -> {
            boolean multiQuestion = view.getMultiAnswerSelected();
            boolean vgQuestion = view.getVgQuestion();
            int points = view.getQuestionPoint();
            String questionText = view.getQuestion();

            Question question = new Question(multiQuestion,points,vgQuestion,questionText);

            //If it is a multianswer-question:
            if (view.getMultiAnswerSelected()){
                ArrayList<TextField> tempArray = view.getMultiAnswerList();

                tempArray.forEach(answerEvent -> {
                    //Make objects of all answers:
                    Answer answer = new Answer(answerEvent.getText());

                    //If the answer equals to the right answer, set boolean to true:
                    if (view.getCorrectAnswer().equals(answerEvent.getText())){
                        answer.setCorrectAnswer(true);
                    }

                    //Add answer to the list in the Question-class:
                    question.addAnswer(answer);

                });
            }

            schoolTest.addQuestion(question);

            view.initSaveQuestionBtn();

           // System.out.println(schoolTest);
            //commandHandler.send("puttest",schoolTest);
        });

    }

    void login(){
        String username = loginBox.getUserTextField();
        String password = loginBox.getPasswordField();

        if (username.equals("admin") && password.equals("admin")){
            //Create a login-bean:
            Login userLogin = new Login(username,password);
            //Send it to the commandhandler:
            commandHandler.send("login",userLogin);
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
