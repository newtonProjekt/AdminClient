package adminClient.gui;

/**
 * Class for creating a "add user-form".
 */

import adminClient.beans.NewtonClass;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * Created by Jonas on 2016-03-07.
 */

public class AddUser extends GridPane{

    //Components:
    private Text headerText = new Text("Skapa ny användare:");
    private Label fNameLabel = new Label("Förnamn:");
    private Label lNameLabel = new Label("Efternamn:");
    private Label pNumberLabel = new Label("Personnummer:");
    private Label studentClassLabel = new Label("Klass:");
    private Label studentAddedLabel = new Label("");
    private TextField fNameTextField = new TextField();
    private TextField lNameTextField = new TextField();
    private TextField pNumberTextField = new TextField();
    private Button addUserButton = new Button("Lägg till användare");
    private ColumnConstraints column1 = new ColumnConstraints();
    private ComboBox<NewtonClass> comboBox = new ComboBox<>();

    public AddUser() {

        //Init gridpane:
        this.setPadding(new Insets(10,10,10,10));
        this.setHgap(10);
        this.setVgap(10);
        column1.setHalignment(HPos.RIGHT);
        comboBox.setPromptText("-Välj-");
        headerText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        this.getColumnConstraints().add(column1);
        this.setAlignment(Pos.TOP_CENTER);
        comboBox.setMinWidth(170);

        //Add components to gridpane:
        this.add(headerText,1,0);
        this.add(fNameLabel,0,1);
        this.add(lNameLabel,0,2);
        this.add(pNumberLabel,0,3);
        this.add(studentClassLabel,0,4);
        this.add(fNameTextField,1,1);
        this.add(lNameTextField,1,2);
        this.add(pNumberTextField,1,3);
        this.add(comboBox,1,4);
        this.add(addUserButton,1,5);
        this.add(studentAddedLabel,1,6);

        //Updates the combobox with the student class-names:
        comboBox.setCellFactory(new Callback<ListView<NewtonClass>, ListCell<NewtonClass>>() {
            @Override
            public ListCell call(ListView param) {
                return new ListCell<NewtonClass>(){
                    @Override
                    public void updateItem(NewtonClass item, boolean empty){
                        super.updateItem(item, empty);
                        if(!empty) {
                            setText(item.getName());
                            setGraphic(null);
                        }
                    }
                };
            }
        });
    }

    /**
     * Listener for button.
     * @param buttonListener = Eventhandler, incomming from the controller-class via the view-class.
     */
    public void addUserButtonListener (EventHandler<ActionEvent> buttonListener){
        addUserButton.setOnAction(buttonListener);
    }

    //Getters & setters:
    public String getfNameTextField() {
        return fNameTextField.getText();
    }

    public String getlNameTextField() {
        return lNameTextField.getText();
    }

    public String getpNumberTextField() {
        return pNumberTextField.getText();
    }

    public void setCmbBox(ObservableList observableList){
        comboBox.setItems(observableList);
    }

    public NewtonClass getSelectedClass(){
        return comboBox.getSelectionModel().getSelectedItem();
    }

    public void setStudentAdded(String student){
        studentAddedLabel.setText(student);
    }

    public void clearTextFields(){
        fNameTextField.clear();
        lNameTextField.clear();
        pNumberTextField.clear();
    }
}
