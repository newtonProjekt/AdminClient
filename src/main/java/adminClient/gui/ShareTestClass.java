package adminClient.gui;

import adminClient.beans.NewtonClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Jonas on 2016-03-14.
 */

public class ShareTestClass extends Stage {
    private BorderPane root = new BorderPane();
    private GridPane gridpane = new GridPane();
    private HBox buttonBox = new HBox(10);
    private VBox arrowsVbox = new VBox(5);
    private ColumnConstraints column1 = new ColumnConstraints(150, 150, Double.MAX_VALUE);
    private ColumnConstraints column2 = new ColumnConstraints(50);
    private ColumnConstraints column3 = new ColumnConstraints(150, 150, Double.MAX_VALUE);
    private Label candidatesLbl = new Label("Inte tillgång till prov:");
    private Label selectedLbl = new Label("Tillgång till prov:");
    private ObservableList<NewtonClass> candidateClasses;
    private final ListView<NewtonClass> candidatesListView;
    private ObservableList<NewtonClass> selectedClasses = FXCollections.observableArrayList();
    private ListView<NewtonClass> selectedClassesListView = new ListView<>(selectedClasses);
    private Button sendRightButton = new Button(" > ");
    private Button sendLeftButton = new Button(" < ");
    private Button shareTestBtn = new Button("Dela prov");

    public ShareTestClass(ObservableList<NewtonClass> observableList) {
        this.candidateClasses = FXCollections.observableArrayList(observableList);
        candidatesListView = new ListView<>(candidateClasses);

        //the sharetextbox is in focus, the other stages is disabled:
        this.initModality(Modality.APPLICATION_MODAL);

        //Init GridPane:
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);

        column1.setHgrow(Priority.ALWAYS);
        column3.setHgrow(Priority.ALWAYS);

        gridpane.getColumnConstraints().addAll(column1, column2, column3);

        GridPane.setHalignment(candidatesLbl, HPos.CENTER);
        GridPane.setHalignment(selectedLbl, HPos.CENTER);
        GridPane.setVgrow(root, Priority.ALWAYS);

        gridpane.add(candidatesLbl, 0, 0);

        gridpane.add(selectedLbl, 2, 0);

        gridpane.add(candidatesListView, 0, 1);

        gridpane.add(selectedClassesListView, 2, 1);

        gridpane.add(arrowsVbox, 1, 1);

        //Init HBox:
        buttonBox.getChildren().add(shareTestBtn);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(5,5,5,5));

        //Init ArrowsVBox:
        arrowsVbox.getChildren().addAll(sendRightButton, sendLeftButton);
        arrowsVbox.setAlignment(Pos.CENTER);

        //Set Borderpane:
        root.setCenter(gridpane);
        root.setBottom(buttonBox);

        //Listener for "SendRightButton":
        sendRightButton.setOnAction((ActionEvent event) -> {
            NewtonClass potential = candidatesListView.getSelectionModel()
                    .getSelectedItem();
            if (potential != null) {
                candidatesListView.getSelectionModel().clearSelection();
                candidateClasses.remove(potential);
                selectedClasses.add(potential);
            }
        });

        //Listener for "SendLeftButton":
        sendLeftButton.setOnAction((ActionEvent event) -> {
            NewtonClass s = selectedClassesListView.getSelectionModel().getSelectedItem();
            if (s != null) {
                selectedClassesListView.getSelectionModel().clearSelection();
                selectedClasses.remove(s);
                candidateClasses.add(s);
            }
        });

        Scene scene = new Scene(root, 500, 300, Color.WHITE);
        this.setScene(scene);

    }

    public void setShareTestBtnListener(EventHandler<ActionEvent> listener){
        shareTestBtn.setOnAction(listener);
    }

    public ObservableList<NewtonClass> getSelectedClasses(){
        return selectedClasses;
    }

}
