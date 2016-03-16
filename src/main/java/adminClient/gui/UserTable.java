package adminClient.gui;

/**
 * The model for a Table based on the class User.
 */

import adminClient.beans.TableStudent;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Jonas on 2016-03-03.
 */

public class UserTable extends TableView<TableStudent> {

    private TableColumn<TableStudent, Long> userPersNumb = new TableColumn<TableStudent, Long>("Personnummer");
    private TableColumn<TableStudent, String> userFirstName = new TableColumn<TableStudent, String>("Förnamn");
    private TableColumn<TableStudent, String> userLastName = new TableColumn<TableStudent, String>("Efteramn");
    private TableColumn<TableStudent, String> userStudentClass = new TableColumn<TableStudent, String>("Klass");

    public UserTable() {
        this.setEditable(true);

        userPersNumb.setCellValueFactory(new PropertyValueFactory<>("persNumber"));
        userPersNumb.setPrefWidth(132);
        userFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        userFirstName.setPrefWidth(280);
        userLastName.setCellValueFactory(new PropertyValueFactory<>("surName"));
        userLastName.setPrefWidth(280);
        userStudentClass.setCellValueFactory(new PropertyValueFactory<>("newtonClass"));
        userStudentClass.setPrefWidth(106);

        this.getColumns().setAll(userPersNumb, userFirstName, userLastName, userStudentClass);

    }

    public void setTableList(ObservableList<TableStudent> userObservableList){
        this.setItems(userObservableList);
    }
}
