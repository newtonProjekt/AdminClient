package adminClient.beans;

/**
 * Created by Jonas on 2016-03-08.
 */

public class StudentClass {
    private String name;

    public StudentClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
