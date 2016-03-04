package adminClient;

/**
 * The GUI for the administrator.
 */

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Jonas on 2016-03-02.
 */

public class AdminView {

    //Layouts:
    private BorderPane mainBorderPane = new BorderPane();
    private BorderPane testBorderPane = new BorderPane();
    private BorderPane userBorderPane = new BorderPane();
    private HBox topMenuPanel = new HBox();
    private HBox bottomMenuPanel = new HBox();
    private VBox handleTestPanel = new VBox(5);
    private HBox handleTestButtons = new HBox(5);
    //Buttons:
    private Button editTestBtn = new Button("Redigera prov");
    private Button deleteTestBtn = new Button("Radera prov");
    private Button statTestBtn = new Button("Statistik");
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
    private TableView<Test> testTableView;
    private TableView<User> userTableView;
    //Logo-image:
    private Image image1 = new Image("file:logo-footer.png");
    private ImageView imageView = new ImageView(image1);
    //Stage and scene:
    private Stage window;
    private Scene scene;

    public AdminView(Stage window) {
        this.window = window;
        scene = new Scene(mainBorderPane,800,600);

        //Can't close tabs:
        mainMenuTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        testsTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        usersTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        //Add tabs to TabPanes:
        mainMenuTabPane.getTabs().addAll(homeTab, testsTab, usersTab);
        testsTabPane.getTabs().addAll(handleTestsTab, createTest);
        usersTabPane.getTabs().addAll(handleUsersTab, createUser);
        //Set content to tabs:
        testsTab.setContent(testBorderPane);
        usersTab.setContent(userBorderPane);
        handleTestsTab.setContent(handleTestPanel);
        homeTab.setContent(new Label("Antal upplagda prov, allmän information osv"));
        createUser.setContent(new Label("Forumulär för att skapa ny användare"));
        createTest.setContent(new Label("Forumulär för att skapa nytt prov"));
        handleUsersTab.setContent(new Label("Tabell med användare. Kolumner för personnummer, klass osv.\nMan ska kunna markera en användare och välja att redigera information eller radera."));


        //Add the logo-image to the H-box 'topMenuPanel'.
        topMenuPanel.getChildren().add(imageView);

        //Add the TabPane 'testsTabPane' to the BorderPane 'testBorderPane'.
        testBorderPane.setCenter(testsTabPane);

        //Add the TabPane 'usersTabPane' to the BorderPane 'userBorderPane'.
        userBorderPane.setCenter(usersTabPane);

        //Add the test-buttons to the H-box 'handleTestButtons'.
        handleTestButtons.getChildren().addAll(editTestBtn, deleteTestBtn);



        //Add the TableView 'testTableView' and the H-box 'handleTestButtons' to the V-box 'handleTestPanel'.
        handleTestPanel.getChildren().addAll(testTableView,handleTestButtons);



        //Add the H-box 'topMenuPanel' to the top of the main borderpane:
        mainBorderPane.setTop(topMenuPanel);

        //Add the TabPane 'mainMenuTabPane' in the center of the main borderpane:
        mainBorderPane.setCenter(mainMenuTabPane);

        //Add the H-box 'bottomMenuPanel' to the bottom of the main borderpane:
        mainBorderPane.setBottom(bottomMenuPanel);



        //Bind main-panel to the size of the scene:
        mainBorderPane.prefHeightProperty().bind(scene.heightProperty());
        mainBorderPane.prefWidthProperty().bind(scene.widthProperty());

        //Init components:
        topMenuPanel.setStyle("-fx-alignment: center-left; -fx-background-color: #ee7202;");
        bottomMenuPanel.setStyle("-fx-background-color: #ee7202");
        bottomMenuPanel.setPrefHeight(40);




        window.setTitle("Admin");
        window.setScene(scene);
        window.show();
    }

    void builComponents(){

    }

    void initComponents(){

    }

    public void setUserTableView (UserTable userTable){
        userTableView = userTable;
    }

    public void setTestTableView (TestTable testTable){
        testTableView = testTable;
    }
}
