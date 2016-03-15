package adminClient.beans;

/**
 * Entity class that handles the tests thats been corrected for further reference.
 *
 * Created by Johan Lindström (jolindse@hotmail.com) on 2016-03-14.
 *
 * id	 					= Auto generated id value.
 * testId					= Referencing what test with id.
 * persNumber				= Referencing the student id.
 * totalNumberOfVgQuestions	= The number of questions marked as VG-questions.
 * totalNumberOfGQuestions	= The number of questions marked as G-questions.
 * maxPoints				= How many points a maximal result on the test would amount to.
 * vgPoints					= How many points the student earned in VG-questions.
 * gPoints					= How many points the student earned in G-questions.
 * completedCorrection		= Boolean that notes if the test was completely corrected.
 *
 */

public class CorrectedTests {

	private int id;

	// Identification
	private int testId;
	private long persNumber;

	// Questions info
	private int totalNumberOfVgQuestion;
	private int totalNumberOfGQuestions;
	private int maxPoints;

	// Correction info
	private int vgPoints;
	private int gPoints;

	private boolean completedCorrection;


	public CorrectedTests(){
	}

	public CorrectedTests(int testId, long persNumber, int totalNumberOfVgQuestion, int totalNumberOfGQuestions, int maxPoints, int vgPoints, int gPoints, boolean completedCorrection) {
		this.testId = testId;
		this.persNumber = persNumber;
		this.totalNumberOfVgQuestion = totalNumberOfVgQuestion;
		this.totalNumberOfGQuestions = totalNumberOfGQuestions;
		this.maxPoints = maxPoints;
		this.vgPoints = vgPoints;
		this.gPoints = gPoints;
		this.completedCorrection = completedCorrection;
	}

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public long getPersNumber() {
		return persNumber;
	}

	public void setPersNumber(long persNumber) {
		this.persNumber = persNumber;
	}

	public int getTotalNumberOfVgQuestion() {
		return totalNumberOfVgQuestion;
	}

	public void setTotalNumberOfVgQuestion(int totalNumberOfVgQuestion) {
		this.totalNumberOfVgQuestion = totalNumberOfVgQuestion;
	}

	public int getTotalNumberOfGQuestions() {
		return totalNumberOfGQuestions;
	}

	public void setTotalNumberOfGQuestions(int totalNumberOfGQuestions) {
		this.totalNumberOfGQuestions = totalNumberOfGQuestions;
	}

	public int getMaxPoints() {
		return maxPoints;
	}

	public void setMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}

	public int getVgPoints() {
		return vgPoints;
	}

	public void setVgPoints(int vgPoints) {
		this.vgPoints = vgPoints;
	}

	public int getgPoints() {
		return gPoints;
	}

	public void setgPoints(int gPoints) {
		this.gPoints = gPoints;
	}

	public boolean isCompletedCorrection() {
		return completedCorrection;
	}

	public void setCompletedCorrection(boolean completedCorrection) {
		this.completedCorrection = completedCorrection;
	}
}