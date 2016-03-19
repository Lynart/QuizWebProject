package ca.javaTheHutt.Servlet;

import java.sql.SQLException;
import javax.persistence.*;
import javax.sql.DataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import ca.javaTheHutt.model.*;

public class ContextManager {
	
	private EntityManagerFactory emf;
	private EntityManager em;

	//Create the EntityManager
	public ContextManager(){
		emf = Persistence.createEntityManagerFactory("QuizWebProject");
		em = emf.createEntityManager();
	}

	//Stub, implement later
	//Need a user object or user name (make it hacky who cares)
	public Quiz GenerateQuiz(){
		Quiz q = new Quiz();
		return q;
	}
	
	//Method is not tested; need to test!
	public void AddQuestion(Question q){
		em.getTransaction().begin();
		em.persist(q);
		//Add all answers
		while(q.getAnswers().iterator().hasNext()){
			em.persist(q.getAnswers().iterator().next());
		}
		em.getTransaction().commit();
	}
	
	//This method is probably not needed?
	public static DataSource getMySQLDataSource(){
		MysqlDataSource mysqlDS = null;
		mysqlDS = new MysqlDataSource();
		mysqlDS.setURL("jdbc:mysql://zenit.senecac.on.ca/dps904_161a11");
		mysqlDS.setUser("dps904_161a11");
		mysqlDS.setPassword("jmQL4399");
		return mysqlDS;
	}
	
	//Destructor; manually call it when you stop using this class!
	public void Destroy(){
		em.close();
		emf.close();
	}
	
}
