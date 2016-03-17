package adminClient.gui;

/**
 * Class for creating a "Add Class-form".
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by Jonas on 2016-03-08.
 */

public class AddStudentClass extends GridPane {
    //Components:
    private Text textHeader = new Text("Skapa ny klass:");
    private Label studentClassLabel = new Label("Klassnamn:");
    private TextField studentClassTextField = new TextField();
    private Button addClassButton = new Button("Lägg till klass");
    private ColumnConstraints column1 = new ColumnConstraints();

    public AddStudentClass() {
        //Init gridpane:
        this.setPadding(new Insets(10,100,10,10));
        this.setHgap(10);
        this.setVgap(10);
        column1.setHalignment(HPos.RIGHT);
        textHeader.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        this.getColumnConstraints().add(column1);
        this.setAlignment(Pos.TOP_RIGHT);

        //Add components to gridpane:
        this.add(textHeader,1,0);

        this.add(studentClassLabel,0,1);

        this.add(studentClassTextField,1,1);

        this.add(addClassButton,1,2);
    }

    /**
     * Listener for button.
     * @param buttonListener = Eventhandler, incomming from the controller-class via the view-class.
     */
    public void addClassButtonListener (EventHandler<ActionEvent> buttonListener){
        addClassButton.setOnAction(buttonListener);
    }

    /**
     * Gets classname:
     * @return class as a String.
     */
    public String getStudentClassTextField() {
        return studentClassTextField.getText();
    }

    /**
     * Clears textfields:
     */
    public void clearTextFields(){
        studentClassTextField.clear();
    }
}
