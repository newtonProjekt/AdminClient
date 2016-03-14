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
import javafx.application.Platform;
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
    private Student student;

    //Components for tables:
    private TableView<TableStudent> userTableView = new UserTable();
    private TableView<SchoolTest> testTableView = new TestTable();
    private ObservableList<SchoolTest> testObservableList = FXCollections.observableArrayList();
    private ObservableList<TableStudent> studentObservableList = FXCollections.observableArrayList();
    private ObservableList<NewtonClass> studentClassObservableList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {

        //TODO kunna redigera prov.
        //TODO bekräftelse innan man skapar ett prov.
        //TODO dela prov till elever ELLER klasser, listviews.
        //TODO restrict textfields till bara nummer / bokstäver.
        //TODO hemfönstret.

        //Create objects of a CommandHandler and a LoginBox:
        commandHandler = new CommandHandler(this);
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

        //Update the lists with current data:
        commandHandler.send("getalltests","");
        commandHandler.send("getallstudentclasses","");
        commandHandler.send("getallstudents","");


        //ADD CLASS
        view.addClassBtnListener(event1 -> {
            NewtonClass newtonClass = new NewtonClass(view.getStudentClass());
            commandHandler.send("putnewtonclass",newtonClass);
            commandHandler.send("getallstudentclasses","");
            view.clearAddClassTextField();
        });

        //ADD USER:
        view.addUserBtnListener(event -> {
            String fName = view.getFname();
            String lName = view.getLname();
            long pNumb = Long.parseLong(view.getPnumb());

            student = new Student(pNumb,fName,lName);

            if (view.addUserGetSelectedClass() != null) {
                NewtonClass newtonClass = view.addUserGetSelectedClass();

                int newtonClassId = 0;

                for (int i = 0; i < studentClassObservableList.size(); i++) {
                    if (studentClassObservableList.get(i).getName().equals(newtonClass.getName())){
                        newtonClassId = studentClassObservableList.get(i).getId();
                        System.out.println(newtonClassId);
                    }
                }

                student.setNewtonClassId(newtonClassId);
            }

            commandHandler.send("putstudent",student);
            commandHandler.send("getallstudents","");

            view.clearAddUserTextFields();

        });

        //DELETE TEST
        view.deleteTestBtnListener(event -> {

            commandHandler.send("deletetest",view.getSelectedTest());
            commandHandler.send("getalltests","");
        });

        //DELETE USER
        view.deleteUserBtnListener(event -> {
            long selectedUserId = view.getSelectedUser().getPersNumber();

            commandHandler.send("deletestudent",selectedUserId);
            commandHandler.send("getallstudents","");
        });

        //IF CLASSLIST CHANGED, UPDATE COMBOBOX:
        studentClassObservableList.addListener((ListChangeListener<NewtonClass>) c -> {
            view.addUserComboBox(studentClassObservableList);
            view.editUserComboBox(studentClassObservableList);
            view.deleteClassCmbBox(studentClassObservableList);

        });


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

        //Save a question:
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

        });

        view.createTestBtnListener(event -> {
            commandHandler.send("puttest",schoolTest);
            commandHandler.send("getalltests","");

            //Reset the testform:
            view.startOverTest();
        });

        view.startOverBtnListener(event -> {
            view.startOverTest();
        });

        view.editUserBtnListener(event -> {
            TableStudent selectedStudent = view.getSelectedUser();

            view.setOldFname(selectedStudent.getFirstName());
            view.setOldLname(selectedStudent.getSurName());
            view.setOldPnumb(selectedStudent.getPersNumber());

            for (int i = 0; i < studentClassObservableList.size(); i++) {
                if (studentClassObservableList.get(i).getId() == selectedStudent.getNewtonClassId()){
                    view.editUserSetComboBox(studentClassObservableList.get(i));
                }
            }
            view.handleUserEditUser();
        });

        view.editUserFormButton(event -> {
            String fName = view.getNewFname();
            String lName = view.getNewLname();
            long pNumb = Long.parseLong(view.getNewPnumb());
            Student student = new Student(pNumb,fName,lName);

            if (view.editUserSelectedClass() != null) {
                NewtonClass newtonClass = view.editUserSelectedClass();

                int newtonClassId = 0;

                for (int i = 0; i < studentClassObservableList.size(); i++) {
                    if (studentClassObservableList.get(i).getName().equals(newtonClass.getName())){
                        newtonClassId = studentClassObservableList.get(i).getId();
                    }
                }

                student.setNewtonClassId(newtonClassId);
            }

            commandHandler.send("updatestudent",student);
            commandHandler.send("getallstudents","");

            view.clearEditUserTextFields();
            view.handleUserTable();
        });

        view.editUserBackButton(event -> {
            view.handleUserTable();
        });

        view.deleteNewtonClassBtnListener(event -> {
            if (view.getDeleteStudentsCb()){
                commandHandler.send("deletestudentsfromclass",view.newtonClasstoRemove());
            }
            commandHandler.send("deleteclass",view.newtonClasstoRemove());
            commandHandler.send("getallstudentclasses","");
            commandHandler.send("getallstudents","");
            view.clearDeleteClassCmbBox();
        });

        view.shareTestBtnListener(event1 -> {
           view.showShareTest();
        });

        primaryStage.setOnCloseRequest(event -> {
            commandHandler.disconnectServer();
            Platform.exit();
            System.exit(0);
        });

    }

    void login(){
        String username = loginBox.getUserTextField();
        String password = loginBox.getPasswordField();

        if (username.equals("admin") && password.equals("admin")){
            //Create a login-bean:
            Login userLogin = new Login("0",password);
            //Send it to the commandhandler:
            commandHandler.send("login",userLogin);
            //Close the loginbox:
            loginBox.close();
        }
        else {
            loginBox.setErrorLabel("Felaktigt användarnamn eller lösenord.");
        }
    }

    public void addTest(SchoolTest schoolTest){
        testObservableList.add(schoolTest);
    }

    public void addStudent(TableStudent student){
        int newtonClassId = student.getNewtonClassId();

        for (int i = 0; i < studentClassObservableList.size(); i++) {
            if (newtonClassId == studentClassObservableList.get(i).getId()){
                student.setNewtonClass(studentClassObservableList.get(i).getName());
            }
        }
        studentObservableList.add(student);
    }

    public void addNewtonClass (NewtonClass newtonClass){
        studentClassObservableList.add(newtonClass);
    }

    public void clearStudentList(){
        studentObservableList.clear();
    }

    public void clearClassList(){
        studentClassObservableList.clear();
    }

    public void clearTestList(){
        testObservableList.clear();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
