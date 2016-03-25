package ca.javaTheHutt.model;

import javax.persistence.*;

import com.sun.istack.Nullable;

@Entity
@Table(name = "UserResponse")
public class UserResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String response;
	@ManyToOne
	private Question question;
	@ManyToOne
	private Quiz quiz;
	@OneToOne @Nullable
	//Could be null, need to test what happens if it is
	private Answer answer;
	private Boolean correct;

	// getters
	public int getId() {
		return id;
	}

	public String getResponse() {
		return response;
	}

	public Question getQuestion() {
		return question;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public Answer getAnswer() {
		return answer;
	}
	
	private Boolean getCorrect(){
		return correct;
	}

	// setters
	public void setResponse(String response) {
		this.response = response;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
	private void setCorrect(Boolean c){
		correct = c;
	}
}
