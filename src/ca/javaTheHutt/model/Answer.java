package ca.javaTheHutt.model;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;


@Entity
@Table(name="Answer")
public class Answer {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	//0 for incorrect, 1 for correct. Might use other integers 
	private Boolean correct;
	@ManyToOne
	private Question question;
	private String description;
	@OneToMany
	private Collection<UserResponse> userResponses;
	
	//getters
	public int getId(){
		return id;
	}
	public Boolean getCorrect(){
		return correct;
	}
	public Question getQuestion(){
		return question;
	}
	public String getDescription(){
		return description;
	}
	public Collection<UserResponse> getUserResponses(){
		return userResponses;
	}
	
	//setters
	public void setCorrect(Boolean c){
		correct = c;
	}
	public void setQuestion(Question q){
		question = q;
	}
	public void setDescription(String d){
		description = d;
	}
	
	public void addUserResponse(UserResponse u){
		userResponses.add(u);
	}
	
	public Answer(){
		userResponses = new ArrayList<UserResponse>();
	}
	
	//Useful methods
	public String toString(){
		return this.description;
	}
}
