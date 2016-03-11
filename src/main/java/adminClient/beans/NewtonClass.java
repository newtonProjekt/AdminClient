package adminClient.beans;

/**
 * Entity class that consists of name of class, students in class and the tests the class has access to.
 */

public class NewtonClass {

	private Integer id;
	private String name;


	public NewtonClass(String name){
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Override
    public String toString() {
        return name;
    }
}
