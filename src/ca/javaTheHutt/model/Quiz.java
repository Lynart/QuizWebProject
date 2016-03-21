package ca.javaTheHutt.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;

@Entity
@Table(name = "Quiz")
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int attempt;
	@Temporal(TemporalType.DATE)
	private Date dateCreated;
	private int userScore;
	@ManyToOne
	private User user;
	@ManyToMany
	private Collection<Question> questions;
	@OneToMany
	private Collection<UserResponse> userResponse;

	// getters
	public int getId() {
		return id;
	}

	public int getAttempt() {
		return attempt;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public User getUser() {
		return user;
	}

	public int getUserScore() {
		return userScore;
	}

	public Collection<Question> getQuestions() {
		return questions;
	}

	public Collection<UserResponse> getuserResponse() {
		return userResponse;
	}

	// setters
	public void setUser(User i) {
		user = i;
	}

	public void setAttempt(int a) {
		this.attempt = a;
	}

	public void setUserScore(int s) {
		userScore = s;
	}

	public void addQuestion(Question q) {
		questions.add(q);
	}

	public Quiz() {
		questions = new ArrayList<Question>();
		userResponse = new ArrayList<UserResponse>();
		dateCreated = new Date(); // Default is today
		userScore = 0;

		// Need some kind of random generation here for questions to pop 'em
		// into the list
	}
}
