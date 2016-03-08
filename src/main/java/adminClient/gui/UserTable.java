package adminClient.gui;

/**
 * The model for a Table based on the class User.
 */

import adminClient.beans.User;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Jonas on 2016-03-03.
 */

public class UserTable extends TableView<User> {

    private TableColumn<User, Integer> userPersNumb = new TableColumn<User, Integer>("Personnummer");
    private TableColumn<User, String> userFirstName = new TableColumn<User, String>("Förnamn");
    private TableColumn<User, String> userLastName = new TableColumn<User, String>("Efteramn");
    private TableColumn<User, String> userStudentClass = new TableColumn<User, String>("Klass");

    public UserTable() {
        this.setEditable(true);

        userPersNumb.setCellValueFactory(new PropertyValueFactory<>("persNumber"));
        userPersNumb.setPrefWidth(138);
        userFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        userFirstName.setPrefWidth(280);
        userLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        userLastName.setPrefWidth(280);
        userStudentClass.setCellValueFactory(new PropertyValueFactory<>("studentClass"));
        userStudentClass.setPrefWidth(100);

        this.getColumns().setAll(userPersNumb, userFirstName, userLastName, userStudentClass);

    }

    public void setTableList(ObservableList<User> userObservableList){
        this.setItems(userObservableList);
    }
}
