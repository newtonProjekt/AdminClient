package adminClient;

/**
 * The adminController.
 */

import adminClient.beans.*;
import adminClient.gui.*;
import adminClient.network.CommandHandler;
import adminClient.network.NetworkConnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

/**
 * Created by Jonas on 2016-03-02.
 */

public class AdminController extends Application{
    private AdminView view;
    private CommandHandler commandHandler;
    private LoginBox loginBox;
    private SchoolTest schoolTest;
    private Student student;

    //Components for tableviews:
    private TableView<TableStudent> userTableView = new UserTable();
    private TableView<SchoolTest> testTableView = new TestTable();

    private ObservableList<SchoolTest> testObservableList = FXCollections.observableArrayList();
    private ObservableList<TableStudent> studentObservableList = FXCollections.observableArrayList();
    private ObservableList<NewtonClass> studentClassObservableList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {

        //TODO kunna redigera prov.
        //TODO bekräftelse innan man skapar ett prov.
        //TODO dela prov(fixa listan till klassen).
        //TODO Börja om ett prov.
        //TODO restrict textfields till bara nummer / bokstäver.
        //TODO ifall servern inte svarar vid start av program, skicka meddelande
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


        /**
         * ADD A NEWTONCLASS:
         */
        view.addClassBtnListener(event1 -> {
            NewtonClass newtonClass = new NewtonClass(view.getStudentClass());
            commandHandler.send("putnewtonclass",newtonClass);
            commandHandler.send("getallstudentclasses","");
            view.clearAddClassTextField();
        });

        /**
         * ADD A STUDENT:
         */
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

        /**
         * DELETE A SCHOOLTEST:
         */
        view.deleteTestBtnListener(event -> {
            int selectedTestId = view.getSelectedTest().getId();

            commandHandler.send("deletetest",view.getSelectedTest());
            commandHandler.send("getalltests","");
        });

        /**
         * DELETE A USER:
         */
        view.deleteUserBtnListener(event -> {
            long selectedUserId = view.getSelectedUser().getPersNumber();

            commandHandler.send("deletestudent",selectedUserId);
            commandHandler.send("getallstudents","");
        });

        /**
         * DELETE A CLASS:
         */
        view.deleteNewtonClassBtnListener(event -> {
            if (view.getDeleteStudentsCb()){
                commandHandler.send("deletestudentsfromclass",view.newtonClasstoRemove());
            }
            commandHandler.send("deleteclass",view.newtonClasstoRemove());
            commandHandler.send("getallstudentclasses","");
            commandHandler.send("getallstudents","");
            view.clearDeleteClassCmbBox();
        });

        /**
         * If the NewtonClass-list changes, update comboboxes:
         */
        studentClassObservableList.addListener((ListChangeListener<NewtonClass>) c -> {
            view.addUserComboBox(studentClassObservableList);
            view.editUserComboBox(studentClassObservableList);
            view.deleteClassCmbBox(studentClassObservableList);

        });

        /**
         * CREATE TEST:
         */
        view.createTestTabListener(event -> {
            addTest();
        });

        /**
         * Edit a students information (creates a form):
         */
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

        /**
         * When updating a students information and clicking DONE:
         */
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

        /**
         * When updating a students information and clicking BACK:
         */
        view.editUserBackButton(event -> {
            view.handleUserTable();
        });

        /**
         * Share a SchoolTest:
         */
        view.shareTestBtnListener(event -> {
           view.showShareTest();
        });

        /**
         * Edit a Schooltest:
         */
        view.editTestBtnListener(event -> {
            //Local method:
            editTest();
        });

        /**
         * Shutdown-hook.
         */
        primaryStage.setOnCloseRequest(event -> {
            commandHandler.disconnectServer();
            Platform.exit();
            System.exit(0);
        });
    }

    void addTest(){
        //create a new test:
        SchoolTest schoolTest = new SchoolTest();

        //Create a form for the test:
        AddTest addTest = new AddTest(schoolTest);

        //Send the form to the GUI:
        view.createTestContent(addTest);

        //Proceed with creating questions:
        addTest.proceedBtnListener(event -> {

            schoolTest.setName(addTest.getTestName());
            schoolTest.setSubject(addTest.getSubject());
            schoolTest.setTestTime(addTest.getTestTime());

            addTest.initProceedBtn();
        });

        //Save a question:
        addTest.saveQuestionBtnListener(event -> {
            boolean multiQuestion = addTest.getMultiAnswerSelected();
            boolean vgQuestion = addTest.getVgQuestion();
            int points = addTest.getQuestionPoint();
            String questionText = addTest.getQuestion();

            Question question = new Question(multiQuestion,points,vgQuestion,questionText);

            //If it is a multianswer-question:
            if (multiQuestion){
                ArrayList<TextField> tempArray = addTest.getMultiAnswerList();

                tempArray.forEach(answerEvent -> {
                    //Make objects of all answers:
                    Answer answer = new Answer(answerEvent.getText());

                    //If the answer equals to the right answer, set boolean to true:
                    if (addTest.getCorrectAnswer().equals(answerEvent.getText())){
                        answer.setCorrectAnswer(true);
                    }

                    //Add answer to the list in the Question-class:
                    question.addAnswer(answer);
                });
            }

            //Add the question to the SchoolTest-Object:
            schoolTest.addQuestion(question);
            //Init the GUI:
            addTest.initSaveQuestionBtn();
        });

        //Create the test:
        addTest.createTestBtnListener(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle(null);
            alert.setHeaderText("Bekräftelse på att skapa prov");
            alert.getDialogPane().setContent(addTest.confirmationBox());

            Date today = new Date(Calendar.getInstance().getTimeInMillis());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                schoolTest.setDateCreated(today);
                commandHandler.send("puttest",schoolTest);
                commandHandler.send("getalltests","");
                //Reset the testform:
                addTest();
            }
        });

        //Start over, new form:
        addTest.startOverBtnListener(event -> {
            addTest();
        });

        //Delete a question:
        addTest.deleteQuestionBtnListener(event -> {

            if (schoolTest.getQuestions().size() > 1) {
                schoolTest.removeQuestion(addTest.getCurrQuestion());
                addTest.removeQuestion();
            }
        });
    }

    void editTest(){
        //Reference to the selected SchoolTest:
        SchoolTest schoolTest = view.getSelectedTest();
        //Create an object of the class EditTest and send the selected test to the constructor:
        EditTest editTest = new EditTest(schoolTest);
        //Send the gridpane(editTest) to the view:
        view.handleTestEditTest(editTest);

        //Changing the test-information:
        editTest.editTestProceedBtn(event -> {
            String testName = editTest.getTestName();
            String subjectName = editTest.getSubject();
            int testTime = editTest.getTestTime();

            schoolTest.setName(testName);
            schoolTest.setSubject(subjectName);
            schoolTest.setTestTime(testTime);

            editTest.editTestInitProceed();
        });

        editTest.editTestBackBtn(event -> {
            view.handleTestTable();
        });

        //Save a question:
        editTest.editTestSaveQuestionBtn(event -> {
            boolean multiQuestion = editTest.getMultiAnswerSelected();
            boolean vgQuestion = editTest.getVgQuestion();
            int points = editTest.getQuestionPoint();
            String questionText = editTest.getQuestion();

            Question question = new Question(multiQuestion,points,vgQuestion,questionText);

            //If it is a multianswer-question:
            if (multiQuestion){
                ArrayList<TextField> tempArray = editTest.getMultiAnswerList();

                tempArray.forEach(answerEvent -> {
                    //Make objects of all answers:
                    Answer answer = new Answer(answerEvent.getText());

                    //If the answer equals to the right answer, set boolean to true:
                    if (editTest.getCorrectAnswer().equals(answerEvent.getText())){
                        answer.setCorrectAnswer(true);
                    }

                    //Add answer to the list in the Question-class:
                    question.addAnswer(answer);
                });
            }

            //If it already exists and its an update:
            if (schoolTest.getQuestions().contains(editTest.getCurrQuestion())){
                schoolTest.updateQuestion(editTest.getCurrQuestion(),question);
                System.out.println("finns");
            }

            //If its a new question:
            else{
                schoolTest.addQuestion(question);
            }

            editTest.editTestInitSaveQuestion();

        });

        //Delete a question:
        editTest.editTestDeleteQuestionBtn(event -> {

            if (schoolTest.getQuestions().size() > 1) {
                schoolTest.removeQuestion(editTest.getCurrQuestion());
                editTest.removeQuestion();
            }
        });

        //Update the test:
        editTest.editTestMergeTestBtn(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle(null);
            alert.setHeaderText("Bekräftelse på att skapa prov");
            alert.getDialogPane().setContent(editTest.confirmationBox());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                commandHandler.send("updatetest",schoolTest);
                commandHandler.send("getalltests","");

                view.handleTestTable();
            }

        });

    }

    /**
     * Method for login:
     */
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

    /**
     * Method for adding schooltest to the observablelist:
     * @param schoolTest
     */
    public void addTest(SchoolTest schoolTest){
        testObservableList.add(schoolTest);
    }

    /**
     * Method for adding a Student to the observablelist:
     * @param student
     */
    public void addStudent(TableStudent student){
        int newtonClassId = student.getNewtonClassId();

        for (int i = 0; i < studentClassObservableList.size(); i++) {
            if (newtonClassId == studentClassObservableList.get(i).getId()){
                student.setNewtonClass(studentClassObservableList.get(i).getName());
            }
        }
        studentObservableList.add(student);
    }

    /**
     * Method for adding a NewtonClass to the observablelist:
     * @param newtonClass
     */
    public void addNewtonClass (NewtonClass newtonClass){
        studentClassObservableList.add(newtonClass);
    }

    /**
     * Clears the student-observablelist:
     */
    public void clearStudentList(){
        studentObservableList.clear();
    }

    /**
     * Clears the newtonclass-observablelist:
     */
    public void clearClassList(){
        studentClassObservableList.clear();
    }

    /**
     * clears the schooltest-observablelist:
     */
    public void clearTestList(){
        testObservableList.clear();
    }

    /**
     * MAIN.
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
