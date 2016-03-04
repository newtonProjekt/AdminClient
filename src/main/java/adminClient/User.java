package adminClient;

/**
 * Bean-class for User (student).
 */

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Jonas on 2016-03-03.
 */

public class User {
    SimpleStringProperty persNumber;
    SimpleStringProperty firstName;
    SimpleStringProperty lastName;
    SimpleStringProperty studentClass;

    public User(String persNumber, String firstName, String lastName, String studentClass) {
        this.persNumber = new SimpleStringProperty(persNumber);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.studentClass = new SimpleStringProperty(studentClass);
    }

    public String getPersNumber() {
        return persNumber.get();
    }

    public SimpleStringProperty persNumberProperty() {
        return persNumber;
    }

    public void setPersNumber(String persNumber) {
        this.persNumber.set(persNumber);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getStudentClass() {
        return studentClass.get();
    }

    public SimpleStringProperty studentClassProperty() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass.set(studentClass);
    }
}
