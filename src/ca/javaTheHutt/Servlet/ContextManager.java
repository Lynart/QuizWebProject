package ca.javaTheHutt.Servlet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
	public Quiz GenerateQuiz(User u) {
		Quiz q = new Quiz();
		q.setUser(u);
		Query query = em.createNativeQuery("select * from Question where difficulty = 1 order by rand() limit 3",
				Question.class);
		Collection<Question> easyQuestions;
		easyQuestions = query.getResultList();

		query = em.createNativeQuery("select * from Question where difficulty = 2 order by rand() limit 2",
				Question.class);
		Collection<Question> medQuestions;
		medQuestions = query.getResultList();

		query = em.createNativeQuery("select * from Question where difficulty = 3 order by rand() limit 1",
				Question.class);
		Collection<Question> hardQuestions;
		hardQuestions = query.getResultList();

		Iterator<Question> it = easyQuestions.iterator();
		while (it.hasNext()) {
			q.addQuestion(it.next());
		}

		it = medQuestions.iterator();
		while (it.hasNext()) {
			q.addQuestion(it.next());
		}

		it = hardQuestions.iterator();
		while (it.hasNext()) {
			q.addQuestion(it.next());
		}

		// Save the quiz
		em.getTransaction().begin();
		em.persist(q);
		em.getTransaction().commit();
		return q;
	}

	public User Login(String username, String password) {
		Object user;
		String query = "FROM " + User.class.getSimpleName() + " e WHERE e.password='" + password + "' AND e.login='"
				+ username + "'";
		// There has go to to be a less stupid way to do this
		// Check if user exists
		try {
			user = em.createQuery(query).getSingleResult();
		}
		// If user does not exist, exception will be thrown
		catch (NoResultException e) {
			user = null;
		}
		// If user is not null, login was successful
		if (user != null) {
			return (User) user;
		}
		// Login failed
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

	public void EditQuestion(Question question) {
		em.getTransaction().begin();
		em.merge(question);
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

	public Collection<Question> getAllQuestions() {
		String query = "FROM " + Question.class.getSimpleName() + " e";
		Collection<Question> returnThis;
		// This is totally unsafe in a production environment
		returnThis = (Collection<Question>) em.createQuery(query).getResultList();

		return returnThis;
	}

	public Question getQuestion(int questionId) {
		String query = "FROM " + Question.class.getSimpleName() + " e WHERE e.id='" + questionId + "'";
		return (Question) em.createQuery(query).getSingleResult();
	}

	public Boolean deleteQuestion(int questionId) {
		String query = "FROM " + Question.class.getSimpleName() + " e WHERE e.id='" + questionId + "'";
		Question question = (Question) em.createQuery(query).getSingleResult();

		// Cannot delete if quizzes have been created using the question
		if (question.getQuiz().isEmpty()) {
			em.getTransaction().begin();
			em.remove(question);
			em.getTransaction().commit();
			return true;
		} else {
			return false;
		}
	}

	public Question UpdateQuiz(int quizId, int questionIndex, String[] userAnswers) {
		String getQuiz = "FROM " + Quiz.class.getSimpleName() + " e WHERE e.id='" + quizId + "'";
		Quiz quiz = (Quiz) em.createQuery(getQuiz).getSingleResult();

		// I really hate EclipseLinks' lazy loading compared to .NET
		ArrayList<Question> questions = new ArrayList<Question>(quiz.getQuestions());

		Question checkQuestion = questions.get(questionIndex);
		Question returnQuestion = null;
		if (questionIndex < 5) {
			returnQuestion = questions.get(++questionIndex);
		}
		// Really stupid how EclipseLink can't lazy fetch this
		Collection<Answer> a = checkQuestion.getAnswers();
		ArrayList<Answer> answers = new ArrayList<Answer>(checkQuestion.getAnswers());

		// This one is annoying; you have to create a user response for each
		// userAnswer that isn't null
		// Skipping this for now
		if (checkQuestion.getType() == QuestionType.Checkbox) {
			Boolean right = true;
			for (int i = 0; i < answers.size(); i++) {
				// If answer is correct, and user did not select this answer,
				// it's wrong
				if (answers.get(i).getCorrect()) {
					if (userAnswers[i] == null) {
						right = false;
						break;
					}
				}
				// If answer is not correct, and user selected the answer, it's
				// wrong
				else {
					if (userAnswers[i] != null) {
						right = false;
						break;
					}
				}
			}
			if (right) {
				quiz.setUserScore((quiz.getUserScore() + 1));
			}
		} else {
			if (checkQuestion.getCorrectAnswer().getDescription().equalsIgnoreCase(userAnswers[0])) {
				quiz.setUserScore((quiz.getUserScore() + 1));
			}
		}
		em.getTransaction().begin();
		em.merge(quiz);
		em.getTransaction().commit();
		// If it returns null, quiz is complete!
		return returnQuestion;

	}

	public Collection<Quiz> getQuizzes(User u) {
		Query query = em.createNativeQuery("select * from Quiz where User_ID = " + u.getId(), Quiz.class);
		Collection<Quiz> returnThis;
		// This is totally unsafe in a production environment
		returnThis = (Collection<Quiz>) query.getResultList();
		return returnThis;
	}

	// Returns mean, medium, range low, range high (IN THAT ORDER!)
	public ScoreStatistics getScoreRates() {
		String query = "FROM " + Quiz.class.getSimpleName() + " e";
		Collection<Quiz> quizes = (Collection<Quiz>) em.createQuery(query).getResultList();
		Iterator<Quiz> qIt = quizes.iterator();
		ScoreStatistics rc = new ScoreStatistics();
		int i = 0;
		ArrayList<Integer> medianCalc = new ArrayList<Integer>();
		while (qIt.hasNext()) {
			i++;
			Quiz q = qIt.next();
			medianCalc.add(q.getUserScore());
			rc.mean += q.getUserScore();
			if (q.getUserScore() < rc.low) {
				rc.low = q.getUserScore();
			}
			if (q.getUserScore() > rc.high) {
				rc.high = q.getUserScore();
			}
		}
		rc.mean /= i;
		Collections.sort(medianCalc);
		if (medianCalc.size() % 2 == 0){
			rc.median = ((double)medianCalc.get(medianCalc.size()/2)+(double)medianCalc.get(medianCalc.size()/2-1))/2;
		}
		else{
			rc.median = medianCalc.get(medianCalc.size()/2);
		}
		return rc;
	}

}
