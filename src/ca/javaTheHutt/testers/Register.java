package ca.javaTheHutt.testers;

import javax.persistence.*;

import ca.javaTheHutt.model.Answer;
import ca.javaTheHutt.model.Question;
import ca.javaTheHutt.model.Quiz;
import ca.javaTheHutt.model.User;
import ca.javaTheHutt.model.UserResponse;

public class Register {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public void getEntityManager() {
		// Create the EntityManager
		emf = Persistence
				.createEntityManagerFactory("QuizWebProject");
		em = emf.createEntityManager();

	}
	
	public void resetTables(){
		getEntityManager();
		em.getTransaction().begin();
		em.createQuery("DELETE FROM " + UserResponse.class.getSimpleName() + " o").executeUpdate();
		em.createQuery("DELETE FROM " + Answer.class.getSimpleName() + " o").executeUpdate();
		em.createQuery("DELETE FROM " + Question.class.getSimpleName() + " o").executeUpdate();
		em.createQuery("DELETE FROM " + Quiz.class.getSimpleName() + " o").executeUpdate();
		em.createQuery("DELETE FROM " + User.class.getSimpleName() + " o").executeUpdate();
		em.getTransaction().commit();
		em.close();
		emf.close();
	}
	
	public void TestTables(){
		User u = new User();
		u.setLogin("admin@admin.ca");
		u.setPassword("1234");
		/*
		Quiz q = new Quiz();
		q.setUser(u);
		u.addQuiz(q);
		
		Question question = new Question();
		question.setQuiz(q);
		q.addQuestion(question);
	
		Answer a = new Answer();
		a.setQuestion(question);
		question.addAnswer(a);
		*/
		
		try{
			getEntityManager();
			em.getTransaction().begin();
			em.persist(u);
			/*
			em.persist(q);
			em.persist(question);
			em.persist(a);
			*/
			em.getTransaction().commit();
			em.close();
			emf.close();
		}
		catch(Exception e){
			//do something
		}
	}
	
	public String TestRetrieval(){
		getEntityManager();
		String q = "FROM "+User.class.getSimpleName()+" e WHERE e.password='1234'";
		User v = (User)em.createQuery(q).getSingleResult();
		System.out.println(v.getLogin());
		System.out.println(v.getAttemptedQuizes().size());
		Quiz quiz = v.getAttemptedQuizes().iterator().next();
		System.out.println(quiz.getUserScore());
		em.close();
		emf.close();
		return v.getLogin();
		
	}
	
	public static void main ( String[] args){
		Register register = new Register();
		register.resetTables();
		register.TestTables();
	}
}
