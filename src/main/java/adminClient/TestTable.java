package adminClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Jonas on 2016-03-03.
 */

public class TestTable extends TableView<Test>{

    final ObservableList<Test> testObservableList = FXCollections.observableArrayList(
            new Test("Utveckling av desktopapplikationer", 80, 60),
            new Test("Utveckling av webbapplikationer", 80, 60)
    );

    private TableColumn<Test, String> testSubject = new TableColumn<Test, String>("Ämne");
    private TableColumn<Test, Integer> testMaxTime = new TableColumn<Test, Integer>("Tid");
    private TableColumn<Test, Integer> testMaxScore = new TableColumn<Test, Integer>("Maxpoäng");

    public TestTable() {

        this.setEditable(true);

        testSubject.setCellValueFactory(new PropertyValueFactory<Test,String>("subject"));
        testMaxTime.setCellValueFactory(new PropertyValueFactory<Test,Integer>("maxTime"));
        testMaxScore.setCellValueFactory(new PropertyValueFactory<Test,Integer>("maxScore"));

        this.setItems(testObservableList);

        this.getColumns().setAll(testSubject, testMaxTime, testMaxScore);
    }
}
