package adminClient;

/**
 * The model for a Table based on the class User.
 */

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
    private TableColumn<User, String> userLastName = new TableColumn<User, String>("EfterNamn");
    private TableColumn<User, String> userStudentClass = new TableColumn<User, String>("Klass");

    public UserTable() {
        this.setEditable(true);

        userPersNumb.setCellValueFactory(new PropertyValueFactory<User, Integer>("persNumber"));
        userFirstName.setCellValueFactory(new PropertyValueFactory<User,String>("firstName"));
        userFirstName.setPrefWidth(310);
        userLastName.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        userLastName.setPrefWidth(310);
        userStudentClass.setCellValueFactory(new PropertyValueFactory<User, String>("studentClass"));
        userStudentClass.setPrefWidth(80);

        this.getColumns().setAll(userPersNumb, userFirstName, userLastName, userStudentClass);

    }

    public void setTableList(ObservableList<User> userObservableList){
        this.setItems(userObservableList);
    }
}
