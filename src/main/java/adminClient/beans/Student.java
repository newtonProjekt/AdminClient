package adminClient.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for the students.
 *
 * Contains login (personal number), name, password and the answers student has submitted to questions.
 */

public class Student {

	private long persNumber;
	private String firstName;
	private String surName;
	private String password;
    private NewtonClass newtonClass;
	private List<AnswerSubmited> answersSubmited;
	private List<SchoolTest> testsToTake;

	public Student(){
		answersSubmited = new ArrayList<AnswerSubmited>();
	}

	/**
	 * Constructor when no password has been submitted. Sets password to "password".
	 *
	 * @param persNumber long
	 * @param firstName String
	 * @param surName String
     */
	public Student(long persNumber, String firstName, String surName){
		answersSubmited = new ArrayList<AnswerSubmited>();
		testsToTake = new ArrayList<>();
		this.persNumber = persNumber;
		this.firstName = firstName;
		this.surName = surName;
		password = "password";
	}

	/**
	 * Constructor when all arguments is supplied.
	 *
	 * @param persNumber long
	 * @param firstName String
	 * @param surName String
	 * @param password String
     */
	public Student(long persNumber, String firstName, String surName, String password) {
		answersSubmited = new ArrayList<AnswerSubmited>();
		testsToTake = new ArrayList<>();
		this.persNumber = persNumber;
		this.firstName = firstName;
		this.surName = surName;
		this.password = password;
	}

	// Getters and setters

    public NewtonClass getNewtonClass() {
        return newtonClass;
    }

    public void setNewtonClass(NewtonClass newtonClass) {
        this.newtonClass = newtonClass;
    }

    public long getPersNumber() {
		return persNumber;
	}

	public void setPersNumber(long persNumber) {
		this.persNumber = persNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<AnswerSubmited> getAnswersSubmited() {
		return answersSubmited;
	}

	public void setAnswersSubmited(List<AnswerSubmited> answersSubmited) {
		this.answersSubmited = answersSubmited;
	}

	public List<SchoolTest> getTestsToTake() {
		return testsToTake;
	}

	public void setTestsToTake(List<SchoolTest> testsToTake) {
		this.testsToTake = testsToTake;
	}

	// Methods to add and remove tests to take.

	public void addTest(SchoolTest currTest){
		testsToTake.add(currTest);
	}

	public void removeTest(SchoolTest currTest) {
		for (SchoolTest curr: testsToTake){
			System.out.println("BEFORE" + curr);
		}
		testsToTake.remove(currTest);
		for (SchoolTest curr: testsToTake){
			System.out.println("AFTER" + curr);
		}
	}

	// Methods to add and remove answers.

	public void addAnswer(AnswerSubmited currAnswer){
		answersSubmited.add(currAnswer);
	}

	public void removeAnswer(AnswerSubmited currAnswer){
		answersSubmited.remove(currAnswer);
	}

	/**
	 * Checks submitted login credentials against the ones stored in the entity. Returns boolean with result.
	 *
	 * @param submittedPassword String
	 * @return boolean
	 */

	public boolean checkLogin(String submittedPassword){
		boolean loginOk = false;
		if (password.equals(submittedPassword)){
			loginOk = true;
		}
		return loginOk;
	}
}
