package adminClient.gui;

/**
 * The model for a Table based on the class User.
 */

import adminClient.beans.Student;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Jonas on 2016-03-03.
 */

public class UserTable extends TableView<Student> {

    private TableColumn<Student, Long> userPersNumb = new TableColumn<Student, Long>("Personnummer");
    private TableColumn<Student, String> userFirstName = new TableColumn<Student, String>("Förnamn");
    private TableColumn<Student, String> userLastName = new TableColumn<Student, String>("Efteramn");
    private TableColumn<Student, String> userStudentClass = new TableColumn<Student, String>("Klass");

    public UserTable() {
        this.setEditable(true);

        userPersNumb.setCellValueFactory(new PropertyValueFactory<>("persNumber"));
        userPersNumb.setPrefWidth(138);
        userFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        userFirstName.setPrefWidth(280);
        userLastName.setCellValueFactory(new PropertyValueFactory<>("surName"));
        userLastName.setPrefWidth(280);
        userStudentClass.setCellValueFactory(new PropertyValueFactory<>("newtonClass"));
        userStudentClass.setPrefWidth(100);

        this.getColumns().setAll(userPersNumb, userFirstName, userLastName, userStudentClass);

    }

    public void setTableList(ObservableList<Student> userObservableList){
        this.setItems(userObservableList);
    }
}
