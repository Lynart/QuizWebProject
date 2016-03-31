package ca.javaTheHutt.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.persistence.*;

import com.sun.istack.Nullable;

@Entity
@Table(name = "Question")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int difficulty;
	private String description;
	private String explaination;
	private String hint;
	// Will hardcode type of questions and construct the UI accordingly
	@Enumerated(EnumType.STRING)
	private QuestionType type;
	@ManyToMany
	private Collection<Quiz> quizes;
	@OneToMany(cascade=CascadeType.REMOVE)
	private Collection<Answer> answers;
	@OneToOne
	private Answer correctAnswer;
	@OneToMany(mappedBy = "parentQuestion")
	private Collection<Question> subQuestions;
	@ManyToOne
	@Nullable
	private Question parentQuestion;
	@OneToMany
	private Collection<UserResponse> userResponses;

	// getters
	public int getId() {
		return id;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public String getDescription() {
		return description;
	}

	public String getExplaination() {
		return explaination;
	}

	public String getHint() {
		return hint;
	}

	public QuestionType getType() {
		return type;
	}

	public Collection<Quiz> getQuiz() {
		return quizes;
	}

	public Collection<Answer> getAnswers() {
		return answers;
	}

	public Answer getCorrectAnswer() {
		return correctAnswer;
	}

	public Collection<Question> getsubQuestions() {
		return subQuestions;
	}

	public Question getParentQuestion() {
		return parentQuestion;
	}

	public Collection<UserResponse> getUserResponses() {
		return userResponses;
	}

	// setters
	public void setDifficulty(int d) {
		difficulty = d;
	}

	public void setDescription(String d) {
		description = d;
	}

	public void setExplaination(String e) {
		explaination = e;
	}

	public void setHint(String h) {
		hint = h;
	}

	public void setType(QuestionType t) {
		type = t;
	}

	public void addQuiz(Quiz q) {
		quizes.add(q);
	}

	public void addAnswer(Answer a) {
		answers.add(a);
	}

	public void setCorrectAnswer(Answer a) {
		correctAnswer = a;
	}

	public void addSubQuestions(Question q) {
		subQuestions.add(q);
	}

	public void setParentQuestion(Question q) {
		parentQuestion = q;
	}

	public void addUserResponse(UserResponse u) {
		userResponses.add(u);
	}

	// Constructor
	public Question() {
		answers = new ArrayList<Answer>();
		subQuestions = new ArrayList<Question>();
		userResponses = new ArrayList<UserResponse>();
		quizes = new ArrayList<Quiz>();
	}

	// Useful methods
	public String toString() {
		return this.description;
	}
	
	// Returns all answer descriptions/text in a string array
	public String[] getAnswerText(){
		String[] rc = new String[answers.size()];
		Iterator<Answer> it = answers.iterator();
		for(int i=0; it.hasNext(); i++){
			rc[i]=it.next().getDescription();
		}
		return rc;
	}
	
	// Returns the index of the correct answer
	public int[] getCorrectIndexes(){
		int[] rc = new int[answers.size()];
		if(answers.size()==1){
			rc[0]=1;
			return rc;
		}
		Iterator<Answer> it = answers.iterator();
		for(int i=0; it.hasNext(); i++){
			if(it.next().getCorrect()){
				rc[i]=1;
			}
			else{
				rc[i]=0;
			}
		}
		//This should never hit unless somebody botched up question setup
		return rc;
	}

	// May be useful when randomizing question selection
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!Question.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final Question other = (Question) obj;
		if (this.id == other.id) {
			return false;
		}
		return true;
	}
}
