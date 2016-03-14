package adminClient.gui;

import adminClient.beans.NewtonClass;
import com.sun.tools.javac.comp.Check;
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
 * Created by Jonas on 2016-03-08.
 */

public class DeleteStudentClass extends GridPane {
    //Components:
    private Text textHeader = new Text("Ta bort befintlig klass:");
    private Label studentClassLabel = new Label("Klassnamn:");
    private ComboBox<NewtonClass> studentClassCmbBox = new ComboBox<>();
    private Button removeClassButton = new Button("Ta bort klass");
    private ColumnConstraints column1 = new ColumnConstraints();
    private CheckBox deleteStudentsCb = new CheckBox("Ta bort studenter som ingår i klassen?");

    public DeleteStudentClass() {
        //Init gridpane:
        this.setPadding(new Insets(10,10,10,10));
        this.setHgap(10);
        this.setVgap(10);
        column1.setHalignment(HPos.RIGHT);
        textHeader.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        this.getColumnConstraints().add(column1);
        this.setAlignment(Pos.TOP_RIGHT);
        studentClassCmbBox.setMinWidth(170);
        studentClassCmbBox.setPromptText("-Välj-");

        //Add components to gridpane:
        this.add(textHeader,1,0);

        this.add(studentClassLabel,0,1);

        this.add(studentClassCmbBox,1,1);

        this.add(deleteStudentsCb,1,2);

        this.add(removeClassButton,1,3);

        //Updates the combobox with the student class-names:
        studentClassCmbBox.setCellFactory(new Callback<ListView<NewtonClass>, ListCell<NewtonClass>>() {
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
    public void removeClassBtnListener (EventHandler<ActionEvent> buttonListener){
        removeClassButton.setOnAction(buttonListener);
    }

    public void setCmbBox(ObservableList observableList){
        studentClassCmbBox.setItems(observableList);
    }

    public boolean getDeleteStudentsCb(){
        return deleteStudentsCb.isSelected();
    }

    public int classIdToRemove(){
        return studentClassCmbBox.getSelectionModel().getSelectedItem().getId();
    }

    public void clearCmbBox(){
        studentClassCmbBox.setValue(null);
        deleteStudentsCb.setSelected(false);
    }
}
