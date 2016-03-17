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
import java.util.Date;
import java.util.Optional;

/**
 * Created by Jonas on 2016-03-02.
 */

public class AdminController extends Application{
    private AdminView view;
    private CommandHandler commandHandler;
    private LoginBox loginBox;
    private SchoolTest schoolTestToCorrect;
    private SubmittedTest submittedTestToCorrect;
    private long studentToCorrect;
    private Student student;
    private HomeScreen homeScreen;
    private TestInformationBox testInformationBox;

    //Components for tableviews:
    private TableView<TableStudent> userTableView = new UserTable();
    private TestTable testTableView = new TestTable();
    private TestsToCorrectTable testsToCorrectTable = new TestsToCorrectTable();

    private ObservableList<SchoolTest> testObservableList = FXCollections.observableArrayList();
    private ObservableList<TableStudent> studentObservableList = FXCollections.observableArrayList();
    private ObservableList<NewtonClass> studentClassObservableList = FXCollections.observableArrayList();
    private ObservableList<TestsToCorrect> testsToCorrectList = FXCollections.observableArrayList();

    //Listener for studentClassObservableList:
    private ListChangeListener<NewtonClass> classListChangeListener;

    @Override
    public void start(Stage primaryStage) throws Exception {

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
        testsToCorrectTable.setItems(testsToCorrectList);

        /**
         * Doubleclick on a test in the table shows more information about it:
         */
        testTableView.doubleClickRow(click -> {
            if (click.getClickCount() == 2 && view.getSelectedTest() != null) {

                //Create an information-box that show information:
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(view.getSelectedTest().getName());
                alert.setTitle("Information om prov");
                DialogPane dialogPane = alert.getDialogPane();

                ButtonType buttonTypeDelete = new ButtonType("Ta bort");
                ButtonType buttonTypeOk = new ButtonType("Ok");
                alert.getButtonTypes().setAll(buttonTypeDelete,buttonTypeOk);

                String testName = view.getSelectedTest().getName();
                String subject = view.getSelectedTest().getSubject();
                String testTime = String.valueOf(view.getSelectedTest().getTestTime());
                String questionAmount = String.valueOf(view.getSelectedTest().getQuestions().size());
                String dateCreated = String.valueOf(view.getSelectedTest().getDateCreated());

                boolean looping = true;
                while (looping) {
                testInformationBox = new TestInformationBox(
                        testName,subject,testTime,questionAmount,dateCreated
                );

                //Get all students that have access to the test:
                commandHandler.send("getteststudents",view.getSelectedTest().getId());
                dialogPane.setContent(testInformationBox);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeDelete) {

                        Message message = new Message("removetestfromstudent");
                        message.addCommandData(testInformationBox.getSelectedStudentId());
                        message.addCommandData(view.getSelectedTest().getId());
                        commandHandler.sendMessage(message);
                    } else {
                        looping = false;
                        alert.close();
                    }
                }
            }
        });

        /**
         * If the NewtonClass-list changes, update comboboxes(this is just a reference to the listener):
         */
        classListChangeListener = new ListChangeListener<NewtonClass>() {
            @Override
            public void onChanged(Change<? extends NewtonClass> c) {
                view.addUserComboBox(studentClassObservableList);
                view.editUserComboBox(studentClassObservableList);
                view.deleteClassCmbBox(studentClassObservableList);
            }
        };

        //Update the lists with current data:
        commandHandler.send("getalltests","");
        commandHandler.send("getallstudentclasses","");
        commandHandler.send("getallstudents","");
        commandHandler.send("getteststocorrect","");

        //Add information to homescreen:
        Platform.runLater(() -> {
            homeScreen = new HomeScreen(
                    testObservableList.size(),
                    studentObservableList.size(), testsToCorrectTable
            );

            homeScreen.clickTestBox(event -> {
                view.handleTestTable();
            });

            homeScreen.clickUserBox(event -> {
                view.handleUserTable();
            });

            homeScreen.clickCorrectButton(event -> {
                studentToCorrect = homeScreen.getSelectedTestToCorrect().getTestUserNumber();
                Message message = new Message("gettesttocorrect");
                message.addCommandData(homeScreen.getSelectedTestToCorrect().getTestUserNumber());
                message.addCommandData(homeScreen.getSelectedTestToCorrect().getTestId());
                commandHandler.sendMessage(message);

            });


            view.homeScreenContent(homeScreen);

        });

        /**
         * ADD A NEWTONCLASS:
         */
        view.addClassBtnListener(event1 -> {
            if (!view.getStudentClass().equals("")) {
                NewtonClass newtonClass = new NewtonClass(view.getStudentClass());
                commandHandler.send("putnewtonclass", newtonClass);
                commandHandler.send("getallstudentclasses", "");
                view.clearAddClassTextField();
            }
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

            commandHandler.send("deletetest",selectedTestId);
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
         * CREATE TEST:
         */
        view.createTestTabListener(event -> {
            addTest();
        });

        /**
         * Edit a students information (creates a form):
         */
        view.editUserBtnListener(event -> {
            if (view.getSelectedUser() != null) {

                TableStudent selectedStudent = view.getSelectedUser();

                view.setOldFname(selectedStudent.getFirstName());
                view.setOldLname(selectedStudent.getSurName());
                view.setOldPnumb(selectedStudent.getPersNumber());

                for (int i = 0; i < studentClassObservableList.size(); i++) {
                    if (studentClassObservableList.get(i).getId() == selectedStudent.getNewtonClassId()) {
                        view.editUserSetComboBox(studentClassObservableList.get(i));
                    }
                }
                view.handleUserEditUser();
            }
        });

        /**
         * When updating a students information and clicking DONE:
         */
        view.editUserFormButton(event -> {
            String fName = view.getNewFname();
            String lName = view.getNewLname();
            long pNumb = Long.parseLong(view.getNewPnumb());
            String password = view.getNewPassword();

            Student student;


            if (!password.equals("")){
                student = new Student(pNumb,fName,lName,password);
            }
            else {
                student = new Student(pNumb, fName, lName);
            }

            if (view.editUserSelectedClass() != null) {
                NewtonClass newtonClass = view.editUserSelectedClass();

                int newtonClassId = 0;

                for (int i = 0; i < studentClassObservableList.size(); i++) {
                    if (studentClassObservableList.get(i).getName().equals(newtonClass.getName())) {
                        newtonClassId = studentClassObservableList.get(i).getId();
                    }
                }

                student.setNewtonClassId(newtonClassId);
            }

            commandHandler.send("updatestudent", student);
            commandHandler.send("getallstudents", "");

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
         * Share a SchoolTest to a class:
         */
        view.shareTestBtnListener(event -> {
           shareTestClasses();
        });

        /**
         * Share a SchoolTest to a Student:
         */
        view.shareTestStudentBtn(event -> {
            shareTestStudents();
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

    void correctTest(){
        CorrectTest correctTest = new CorrectTest(schoolTestToCorrect,submittedTestToCorrect);
        CorrectedTest correctedTest = new CorrectedTest(studentToCorrect);
        view.homeScreenContent(correctTest);

        correctTest.setBackButton(click -> {
            view.homeScreenContent(homeScreen);
        });

        correctTest.setCorrectQuestionBtn(event -> {

            int testId = correctTest.getCurrTest();
            int questionId = correctTest.getCurrQuestion();
            int pointsAwarded = correctTest.getPoints();
            long studentId = studentToCorrect;
            String comment = correctTest.getComment();

            AnswerCorrected answerCorrected = new AnswerCorrected(
                    testId,questionId,pointsAwarded,studentId,comment
            );

            correctedTest.addAnswer(answerCorrected);

            correctTest.setQuestionCorrected();
            correctTest.setComment();
            correctTest.setScore();
            correctTest.nextQuestion();
        });

        correctTest.setDoneButton(event -> {

            commandHandler.send("putcorrectedtest", correctedTest);
            commandHandler.send("getteststocorrect","");

            Platform.runLater(() -> view.homeScreenContent(homeScreen));
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

            Date today = new Date();

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
     * Creates a form to share a test to classes:
     */
    void shareTestClasses(){
        //If a test is selected:
        if (view.getSelectedTest() != null) {
            //create an object of the class ShareTest and send the list with classes:
            ShareTestClass shareTestClass = new ShareTestClass(studentClassObservableList);

            //Listener for ShareTest-button:
            shareTestClass.setShareTestBtnListener(event -> {
                //Get the selectedclasses:
                ObservableList<NewtonClass> selectedClasses = shareTestClass.getSelectedClasses();

                //loop trough the classes:
                for (int i = 0; i < selectedClasses.size(); i++) {
                    Message message = new Message("addtesttoclass");
                    message.addCommandData(selectedClasses.get(i).getId());
                    message.addCommandData(view.getSelectedTest().getId());
                    commandHandler.sendMessage(message);
                }
                shareTestClass.close();
            });

            //Show the GUI:
            view.showAndWait(shareTestClass);

        }
    }

    /**
     * Creates a form to share a test to students:
     */
    void shareTestStudents(){
        //If a test is selected:
        if (view.getSelectedTest() != null) {
            //create an object of the class ShareTest and send the list with classes:
            ShareTestStudent shareTestStudent = new ShareTestStudent(studentObservableList);
            for (int i = 0; i < studentObservableList.size(); i++) {
                System.out.println(studentObservableList.get(i));
            }

            //Listener for ShareTest-button:
            shareTestStudent.setShareTestBtnListener(event -> {
                //Get the selectedclasses:
                ObservableList<TableStudent> selectedStudents = shareTestStudent.getSelectedClasses();

                //loop trough the classes:
                for (int i = 0; i < selectedStudents.size(); i++) {
                    Message message = new Message("addtesttostudent");
                    message.addCommandData(selectedStudents.get(i).getPersNumber());
                    message.addCommandData(view.getSelectedTest().getId());
                    commandHandler.sendMessage(message);
                }
                shareTestStudent.close();
            });

            //Show the GUI:
            view.showAndWait(shareTestStudent);
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

    public void addTestToCorrect(TestsToCorrect testsToCorrect){
        testsToCorrectList.add(testsToCorrect);
    }

    public void clearTestsToCorrect(){
        testsToCorrectList.clear();
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

    public void setClassListener(){
        studentClassObservableList.addListener(classListChangeListener);
    }

    public void removeClassListener(){
        studentClassObservableList.removeListener(classListChangeListener);
    }

    public void addClassToInformationBox(long pNumb){
        for (int i = 0; i < studentObservableList.size(); i++) {
            if (pNumb == studentObservableList.get(i).getPersNumber()){
                testInformationBox.addStudent(studentObservableList.get(i));
                System.out.println(studentObservableList.get(i));
            }
        }
    }

    public void addSubmittedTestToCorrect(SubmittedTest submittedTest){
        submittedTestToCorrect = submittedTest;
    }

    public void addSchoolTestToCorrect(SchoolTest schoolTest){
        schoolTestToCorrect = schoolTest;

        //start correct test-gui:
        Platform.runLater(() -> correctTest());
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
