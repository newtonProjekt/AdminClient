package adminClient.beans;

/**
 * Bean-class for User (student).
 */

/**
 * Created by Jonas on 2016-03-03.
 */

public class User {
    String persNumber;
    String firstName;
    String lastName;
    StudentClass studentClass;

    public User(String persNumber, String firstName, String lastName, StudentClass studentClass) {
        this.persNumber = persNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentClass = studentClass;
    }

    public String getPersNumber() {
        return persNumber;
    }

    public void setPersNumber(String persNumber) {
        this.persNumber = persNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public StudentClass getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(StudentClass studentClass) {
        this.studentClass = studentClass;
    }
}
