package adminClient.gui;

/**
 * The model for a Table based on the class Test.
 */

import adminClient.beans.SchoolTest;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Jonas on 2016-03-03.
 */

public class TestTable extends TableView<SchoolTest>{

    private TableColumn<SchoolTest, Integer> testName = new TableColumn<SchoolTest, Integer>("Prov");
    private TableColumn<SchoolTest, String> testSubject = new TableColumn<SchoolTest, String>("Ämne");
    private TableColumn<SchoolTest, String> testDateCreated = new TableColumn<SchoolTest, String>("Skapat");

    public TestTable() {
        this.setEditable(true);

        testName.setCellValueFactory(new PropertyValueFactory<SchoolTest, Integer>("name"));
        testName.setPrefWidth(348);
        testSubject.setCellValueFactory(new PropertyValueFactory<SchoolTest,String>("subject"));
        testSubject.setPrefWidth(350);
        testDateCreated.setCellValueFactory(new PropertyValueFactory<SchoolTest, String>("dateCreated"));
        testDateCreated.setPrefWidth(100);

        this.getColumns().setAll(testName, testSubject, testDateCreated);
    }

    public void setTableList (ObservableList<SchoolTest> testObservableList){
        this.setItems(testObservableList);
    }



}
