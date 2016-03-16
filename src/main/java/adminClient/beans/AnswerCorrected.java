package adminClient.beans;

/**
 * Bean class for storing manually corrected answers.
 *
 * Created by Johan Lindström (jolindse@hotmail.com) on 2016-03-16.
 */

public class AnswerCorrected {


	private Integer id;

	private int testId;
	private int questionId;
	private int pointsAwarded;
	private long studentId;

	private String comment;

	public AnswerCorrected() {
	}

	public AnswerCorrected(int testId, int questionId, int pointsAwarded, long studentId, String comment) {
		this.testId = testId;
		this.questionId = questionId;
		this.pointsAwarded = pointsAwarded;
		this.studentId = studentId;
		this.comment = comment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getPointsAwarded() {
		return pointsAwarded;
	}

	public void setPointsAwarded(int pointsAwarded) {
		this.pointsAwarded = pointsAwarded;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
