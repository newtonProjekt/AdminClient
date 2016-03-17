package adminClient.gui;

/**
 * The model for a Table based on the TestsToCorrect class.
 */

import adminClient.beans.TestsToCorrect;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


/**
 * Created by Jonas on 2016-03-03.
 */

public class TestsToCorrectTable extends TableView<TestsToCorrect>{

    private TableColumn<TestsToCorrect, String> testName = new TableColumn<TestsToCorrect, String>("Prov");
    private TableColumn<TestsToCorrect, String> testUser = new TableColumn<TestsToCorrect, String>("Elev");

    public TestsToCorrectTable() {
        this.setEditable(true);

        testName.setCellValueFactory(new PropertyValueFactory<TestsToCorrect, String>("testName"));
        testName.setPrefWidth(100);
        testUser.setCellValueFactory(new PropertyValueFactory<TestsToCorrect,String>("testUser"));
        testUser.setPrefWidth(200);

        this.getColumns().setAll(testName, testUser);

    }

    public void setTableList (ObservableList<TestsToCorrect> testObservableList){
        this.setItems(testObservableList);
    }

    public void doubleClickRow(EventHandler<MouseEvent> listener){
        this.setOnMouseClicked(listener);
    }


}
