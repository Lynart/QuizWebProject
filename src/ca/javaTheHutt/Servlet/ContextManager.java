package ca.javaTheHutt.Servlet;

import java.sql.SQLException;
import java.util.Iterator;

import javax.persistence.*;
import javax.sql.DataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import ca.javaTheHutt.model.*;

public class ContextManager {

	private EntityManagerFactory emf;
	private EntityManager em;

	// Create the EntityManager
	public ContextManager() {
		emf = Persistence.createEntityManagerFactory("QuizWebProject");
		em = emf.createEntityManager();
	}

	// Stub, implement later
	// Need a user object or user name (make it hacky who cares)
	public Quiz GenerateQuiz() {
		Quiz q = new Quiz();
		return q;
	}

	public User Login(String username, String password) {
		Object user;
		String query = "FROM "
						+User.class.getSimpleName()
						+" e WHERE e.password='"+password+
						"' AND e.login='"+username+"'";
		//There has go to to be a less stupid way to do this
		//Check if user exists
		try{
		user = em.createQuery(query).getSingleResult();
		}
		//If user does not exist, exception will be thrown
		catch(NoResultException e){
			user = null;
		}
		//If user is not null, login was successful
		if(user!=null){
			return (User) user;
		}
		//Login failed
		return null;
	}

	// Method is not tested; need to test!
	// Do we need to manually add answers? Test this
	public void AddQuestion(Question q) {
		em.getTransaction().begin();
		em.persist(q);
		// Add all answers

		Iterator<Answer> a = q.getAnswers().iterator();
		while (a.hasNext()) {
			em.persist(a.next());
		}
		em.getTransaction().commit();
	}

	// This method is probably not needed?
	public static DataSource getMySQLDataSource() {
		MysqlDataSource mysqlDS = null;
		mysqlDS = new MysqlDataSource();
		mysqlDS.setURL("jdbc:mysql://zenit.senecac.on.ca/dps904_161a11");
		mysqlDS.setUser("dps904_161a11");
		mysqlDS.setPassword("jmQL4399");
		return mysqlDS;
	}

	// Destructor; manually call it when you stop using this class!
	public void Destroy() {
		em.close();
		emf.close();
	}

}
