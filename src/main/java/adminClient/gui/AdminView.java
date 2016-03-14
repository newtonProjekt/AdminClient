package adminClient.gui;

/**
 * The GUI for the administrator.
 */

import adminClient.beans.NewtonClass;
import adminClient.beans.SchoolTest;
import adminClient.beans.TableStudent;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonas on 2016-03-02.
 */

public class AdminView {
    //Layouts:
    private ScrollPane createTestScrollPane = new ScrollPane();
    private BorderPane mainBorderPane = new BorderPane();
    private BorderPane testBorderPane = new BorderPane();
    private BorderPane userBorderPane = new BorderPane();
    private HBox topMenuPanel = new HBox();
    private HBox bottomMenuPanel = new HBox();
    private HBox createUserPanel = new HBox(140);
    private VBox handleTestPanel = new VBox(5);
    private HBox handleTestButtons = new HBox(10);
    private VBox handleUsersPanel = new VBox(5);
    private HBox handleUserButtons = new HBox(10);
    private VBox newtonClassPanel = new VBox(100);
    //Buttons:
    private Button editTestBtn = new Button("Redigera prov");
    private Button deleteTestBtn = new Button("Radera prov");
    private Button statTestBtn = new Button("Statistik");
    private Button shareTestBtn = new Button("Dela prov");
    private Button editUserBtn = new Button("Redigera användare");
    private Button deleteUserBtn = new Button("Radera användare");
    //Tabs:
    private TabPane mainMenuTabPane = new TabPane();
    private TabPane testsTabPane = new TabPane();
    private TabPane usersTabPane = new TabPane();
    private Tab homeTab = new Tab("Hem");
    private Tab testsTab = new Tab("Prov");
    private Tab usersTab = new Tab("Användare");
    private Tab createTest = new Tab("Skapa nytt prov");
    private Tab createUser = new Tab("Skapa ny användare");
    private Tab handleTestsTab = new Tab("Hantera prov");
    private Tab handleUsersTab = new Tab("Hantera användare");
    //Tables:
    private TableView testTableView;
    private TableView userTableView;
    //Logo-image:
    private Image image1 = new Image("file:logo-footer.png");
    private ImageView imageView = new ImageView(image1);
    //Stage and scene:
    private Stage window;
    private Scene scene;
    //GUI-classes:
    private AddUser addUser = new AddUser();
    private AddStudentClass addStudentClass = new AddStudentClass();
    private AddTest addTest = new AddTest();
    private EditUser editUser = new EditUser();
    private DeleteStudentClass deleteStudentClass = new DeleteStudentClass();
    private ShareTest shareTest = new ShareTest();

    /**
     * Constructor of the view.
     * Sets the scene, builds and initializes the GUI-window.
     * First shows the loginbox, if the user has the right username and password, the application will be shown.
     *
     * @param window = incomming stage from the class adminController.
     */
    public AdminView(Stage window) {
        this.window = window;
        scene = new Scene(mainBorderPane, 800, 600);

        buildGUI();
        initComponents();

        window.setTitle("Admin");
        window.setScene(scene);

        scene.getStylesheets().add("file:Stylesheet.css"); //check the file Stylesheet.css
        window.show();

    }

    /**
     * Builds the GUI (sets layouts)
     */
    void buildGUI() {

        //Add the logo-image to the H-box 'topMenuPanel'.
        topMenuPanel.getChildren().add(imageView);

        //Add Tabs to TabPanes:
        mainMenuTabPane.getTabs().addAll(homeTab, testsTab, usersTab);
        testsTabPane.getTabs().addAll(handleTestsTab, createTest);
        usersTabPane.getTabs().addAll(handleUsersTab, createUser);

        //Add the TabPane 'testsTabPane' to the BorderPane 'testBorderPane'.
        testBorderPane.setCenter(testsTabPane);

        //Add the TabPane 'usersTabPane' to the BorderPane 'userBorderPane'.
        userBorderPane.setCenter(usersTabPane);

        //Add the add class-form and the delete class-form to the VBox 'newtonClassPanel'.
        newtonClassPanel.getChildren().addAll(addStudentClass,deleteStudentClass);

        //Add the add user-form and add VBox newtonclasspanel to the Hbox 'createUserPanel'.
        createUserPanel.getChildren().addAll(addUser, newtonClassPanel);

        //Add the add test-form to the ScrollPane 'createTestScrollPane'.
        createTestScrollPane.setContent(addTest);

        //Add the test-buttons to the H-box 'handleTestButtons'.
        handleTestButtons.getChildren().addAll(editTestBtn, deleteTestBtn, statTestBtn, shareTestBtn);

        //Add the user-buttons to the H-box 'handleUserButtons'.
        handleUserButtons.getChildren().addAll(editUserBtn, deleteUserBtn);

        //Add the H-box 'topMenuPanel' to the top of the main borderpane:
        mainBorderPane.setTop(topMenuPanel);

        //Add the TabPane 'mainMenuTabPane' in the center of the main borderpane:
        mainBorderPane.setCenter(mainMenuTabPane);

        //Add the H-box 'bottomMenuPanel' to the bottom of the main borderpane:
        mainBorderPane.setBottom(bottomMenuPanel);
    }

    /**
     * Initializes the components.
     */
    void initComponents() {
        //Can't close tabs:
        mainMenuTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        testsTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        usersTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Set content to tabs:
        testsTab.setContent(testBorderPane);
        usersTab.setContent(userBorderPane);
        handleTestsTab.setContent(handleTestPanel);
        handleUsersTab.setContent(handleUsersPanel);
        homeTab.setContent(new Label("Antal upplagda prov, allmän information osv"));
        createUser.setContent(createUserPanel);
        createTest.setContent(createTestScrollPane);

        //Bind main-panel to the size of the scene:
        mainBorderPane.prefHeightProperty().bind(scene.heightProperty());
        mainBorderPane.prefWidthProperty().bind(scene.widthProperty());

        //Init components:
        topMenuPanel.setStyle("-fx-alignment: center-left; -fx-background-color: #ee7202;");
        bottomMenuPanel.setStyle("-fx-background-color: #ee7202");
        createUserPanel.setStyle("-fx-border-color: #e6e6e6");
        createTestScrollPane.setStyle("-fx-background-color: white;");
        createTestScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        bottomMenuPanel.setPrefHeight(40);
        handleTestButtons.setPadding(new Insets(5, 5, 5, 5));
        handleUserButtons.setPadding(new Insets(5, 5, 5, 5));
        deleteUserBtn.setPrefWidth(150);
        editUserBtn.setPrefWidth(150);
        editTestBtn.setPrefWidth(110);
        deleteTestBtn.setPrefWidth(110);
        statTestBtn.setPrefWidth(110);
        shareTestBtn.setPrefWidth(110);
        createTestScrollPane.setFitToWidth(true);
        createTestScrollPane.setFitToHeight(true);

        //Autoscroll:
        addTest.heightProperty().addListener((observable, oldvalue, newValue) -> createTestScrollPane.setVvalue((Double) newValue));
    }

    /**
     * Inits the TableView 'userTableView', also adds the table and buttons to a borderpane.
     *
     * @param userTable = TableView made from the class User.
     */
    public void setUserTableView(TableView userTable) {
        userTableView = userTable;
        handleUsersPanel.getChildren().addAll(userTableView, handleUserButtons);
    }

    /**
     * Inits the TableView 'testTableView', also adds the table and buttons to a borderpane.
     *
     * @param testTable = TableView made from the class Test.
     */
    public void setTestTableView(TableView testTable) {
        testTableView = testTable;
        //Add the TableView 'testTableView' and the H-box 'handleTestButtons' to the V-box 'handleTestPanel'.
        handleTestPanel.getChildren().addAll(testTableView, handleTestButtons);
    }

    /**
     * Get the selected test from the tableview.
     *
     * @return = the selected test as a "Test"-object.
     */
    public int getSelectedTest() {
        SchoolTest selectedTest = (SchoolTest) testTableView.getSelectionModel().getSelectedItem();
        int selectedTestId = selectedTest.getId();
        return selectedTestId;
    }

    /**
     * Get the selected user from the tableview.
     *
     * @return = the selected user as a "User"-object.
     */
    public TableStudent getSelectedUser() {
        TableStudent selectedUser = (TableStudent) userTableView.getSelectionModel().getSelectedItem();
        return selectedUser;
    }
    public void startOverTest() {
        addTest = new AddTest();
        createTestScrollPane.setContent(addTest);
    }
    public void handleUserTable() {
        handleUsersTab.setContent(handleUsersPanel);
    }
    public void handleUserEditUser() {
        handleUsersTab.setContent(editUser);
    }
    public void showShareTest(){
        shareTest.showAndWait();
    }

    /**
     * Getters & Setters from the "AddUser"-class, called from the AdminController-class.
     */
    public String getFname() {
        return addUser.getfNameTextField();
    }
    public String getLname() {
        return addUser.getlNameTextField();
    }
    public String getPnumb() {
        return addUser.getpNumberTextField();
    }
    public void clearAddUserTextFields() {
        addUser.clearTextFields();
    }
    public void addUserComboBox(ObservableList observableList) {
        addUser.setCmbBox(observableList);
    }
    public NewtonClass addUserGetSelectedClass() {
        return addUser.getSelectedClass();
    }
    public void studentAddedLabel(String student) {
        addUser.setStudentAdded(student);
    }


    //NewtonClass
    public void clearAddClassTextField() {
        addStudentClass.clearTextFields();
    }
    public String getStudentClass() {
        return addStudentClass.getStudentClassTextField();
    }

    //add test
    public void initProceedBtn() {
        addTest.initProceedBtn();
    }
    public String getTestName() {
        return addTest.getTestName();
    }
    public String getSubjectName() {
        return addTest.getSubject();
    }
    public int getTestTime() {
        return addTest.getTestTime();
    }
    public boolean getMultiAnswerSelected() {
        return addTest.getMultiAnswerSelected();
    }
    public String getQuestion() {
        return addTest.getQuestion();
    }
    public ArrayList<TextField> getMultiAnswerList() {
        return addTest.getMultiAnswerList();
    }
    public String getCorrectAnswer() {
        return addTest.getCorrectAnswer();
    }
    public int getQuestionPoint() {
        return addTest.getQuestionPoint();
    }
    public boolean getVgQuestion() {
        return addTest.getVgQuestion();
    }
    public void initSaveQuestionBtn() {
        addTest.initSaveQuestionBtn();
    }

    //delete class
    public int newtonClasstoRemove(){
       return deleteStudentClass.classIdToRemove();
    }
    public void clearDeleteClassCmbBox(){
        deleteStudentClass.clearCmbBox();
    }
    public void deleteClassCmbBox(ObservableList observableList){
        deleteStudentClass.setCmbBox(observableList);
    }
    public boolean getDeleteStudentsCb(){
        return deleteStudentClass.getDeleteStudentsCb();
    }

    //edit user
    public void setOldFname(String fName) {
        editUser.setfNameTextField(fName);
    }
    public void setOldLname(String lName) {
        editUser.setlNameTextField(lName);
    }
    public void setOldPnumb(long pnumb) {
        editUser.setpNumberTextField(pnumb);
    }
    public String getNewFname() {
        return editUser.getfNameTextField();
    }
    public String getNewLname() {
        return editUser.getlNameTextField();
    }
    public String getNewPnumb() {
        return editUser.getpNumberTextField();
    }
    public void clearEditUserTextFields() {
        editUser.clearTextFields();
    }
    public void editUserComboBox(ObservableList observableList) {
        editUser.setCmbBox(observableList);
    }
    public NewtonClass editUserSelectedClass() {
        return editUser.getSelectedClass();
    }
    public void editUserSetComboBox(NewtonClass newtonClass) {
        editUser.setComboBox(newtonClass);
    }

    /**
     * Listeners for the GUI.
     */
    public void editTestBtnListener(EventHandler<ActionEvent> listener) {
        editTestBtn.setOnAction(listener);
    }
    public void deleteTestBtnListener(EventHandler<ActionEvent> listener) {
        deleteTestBtn.setOnAction(listener);
    }
    public void statTestBtnListener(EventHandler<ActionEvent> listener) {
        statTestBtn.setOnAction(listener);
    }
    public void editUserBtnListener(EventHandler<ActionEvent> listener) {
        editUserBtn.setOnAction(listener);
    }
    public void deleteUserBtnListener(EventHandler<ActionEvent> listener) {
        deleteUserBtn.setOnAction(listener);
    }
    public void addUserBtnListener(EventHandler<ActionEvent> listener) {
        addUser.addUserButtonListener(listener);
    }
    public void addClassBtnListener(EventHandler<ActionEvent> listener) {
        addStudentClass.addClassButtonListener(listener);
    }
    public void proceedBtnListener(EventHandler<ActionEvent> listener) {
        addTest.proceedBtnListener(listener);
    }
    public void saveQuestionBtnListener(EventHandler<ActionEvent> listener) {
        addTest.saveQuestionBtnListener(listener);
    }
    public void createTestBtnListener(EventHandler<ActionEvent> listener) {
        addTest.createTestBtnListener(listener);
    }
    public void startOverBtnListener(EventHandler<ActionEvent> listener) {
        addTest.startOverBtnListener(listener);
    }
    public void editUserFormButton(EventHandler<ActionEvent> listener) {
        editUser.editUserButtonListener(listener);
    }
    public void editUserBackButton(EventHandler<ActionEvent> listener) {
        editUser.backButtonListener(listener);
    }
    public void deleteNewtonClassBtnListener(EventHandler<ActionEvent> listener) {
        deleteStudentClass.removeClassBtnListener(listener);
    }
    public void shareTestBtnListener(EventHandler<ActionEvent> listener){
        shareTestBtn.setOnAction(listener);
    }
}
