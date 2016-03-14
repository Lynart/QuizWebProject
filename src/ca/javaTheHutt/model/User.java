package ca.javaTheHutt.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String login;
	private String password;
	@OneToMany
	private Collection<Quiz> quizes;

	// getters
	public int getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public Collection<Quiz> getAttemptedQuizes() {
		return quizes;
	}

	// setters
	// No id setter; should be auto increment
	public void setLogin(String l) {
		login = l;
	}

	public void setPassword(String p) {
		password = p;
	}

	public void addQuiz(Quiz q) {
		quizes.add(q);
	}

	// Constructor
	public User() {
		quizes = new ArrayList<Quiz>();
	}

	// Useful methods
	public String toString() {
		return login;
	}
}
