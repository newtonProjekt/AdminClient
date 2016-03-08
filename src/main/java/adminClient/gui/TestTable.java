package adminClient.gui;

/**
 * The model for a Table based on the class Test.
 */

import adminClient.beans.Test;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Jonas on 2016-03-03.
 */

public class TestTable extends TableView<Test>{

    private TableColumn<Test, Integer> testName = new TableColumn<Test, Integer>("Prov");
    private TableColumn<Test, String> testSubject = new TableColumn<Test, String>("Ämne");
    private TableColumn<Test, String> testDateCreated = new TableColumn<Test, String>("Skapat");

    public TestTable() {
        this.setEditable(true);

        testName.setCellValueFactory(new PropertyValueFactory<Test, Integer>("name"));
        testName.setPrefWidth(348);
        testSubject.setCellValueFactory(new PropertyValueFactory<Test,String>("subject"));
        testSubject.setPrefWidth(350);
        testDateCreated.setCellValueFactory(new PropertyValueFactory<Test, String>("dateCreated"));
        testDateCreated.setPrefWidth(100);

        this.getColumns().setAll(testName, testSubject, testDateCreated);
    }

    public void setTableList (ObservableList<Test> testObservableList){
        this.setItems(testObservableList);
    }



}
