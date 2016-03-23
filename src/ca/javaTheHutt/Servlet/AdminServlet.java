package ca.javaTheHutt.Servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.javaTheHutt.model.Answer;
import ca.javaTheHutt.model.Question;
import ca.javaTheHutt.model.QuestionType;
import ca.javaTheHutt.model.User;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	User user;
	ContextManager cm;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		cm = new ContextManager();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// This is the hackiest security I have ever written
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/adminMenu.html");
		rd.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("questionType") != null) {
			addQuestion(request);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/question.html");
			rd.include(request, response);

		} else if (request.getParameter("viewQuestions") != null) {
			getQuestions(request);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/ViewQuizes.jsp");
			rd.include(request, response);
		} else {
			doGet(request, response);
		}
	}

	private void getQuestions(HttpServletRequest request) {
		Collection<Question> questions = cm.getAllQuestions();
		request.setAttribute("questions", questions);
	}

	private void addQuestion(HttpServletRequest request) {
		String questionType = request.getParameter("questionType");
		int difficulty = Integer.parseInt(request.getParameter("questionDifficulty"));
		Question question = new Question();
		question.setDifficulty(difficulty);
		Answer a = new Answer();
		Answer b = new Answer();
		Answer c = new Answer();
		Answer d = new Answer();

		switch (questionType) {
		case "Checkbox":
			question.setType(QuestionType.Checkbox);
			question.setDescription(request.getParameter("checkQuestion"));

			// I admit, this looks stupid but I am not sure if you have to set
			// both ends
			// I miss .NET entity framework
			a.setQuestion(question);
			if (request.getParameter("boolA") != null)
				a.setCorrect(true);
			else
				a.setCorrect(false);
			a.setDescription(request.getParameter("checkA"));

			b.setQuestion(question);
			if (request.getParameter("boolB") != null)
				b.setCorrect(true);
			else
				b.setCorrect(false);
			b.setDescription(request.getParameter("checkB"));

			c.setQuestion(question);
			if (request.getParameter("boolC") != null)
				c.setCorrect(true);
			else
				c.setCorrect(false);
			c.setDescription(request.getParameter("checkC"));

			d.setQuestion(question);
			if (request.getParameter("boolD") != null)
				d.setCorrect(true);
			else
				d.setCorrect(false);
			d.setDescription(request.getParameter("checkD"));

			question.addAnswer(a);
			question.addAnswer(b);
			question.addAnswer(c);
			question.addAnswer(d);

			cm.AddQuestion(question);
			break;

		// Repeated code sucks but at least this isn't production code
		case "Multichoice":
			question.setType(QuestionType.Multichoice);
			question.setDescription(request.getParameter("multiQ"));

			// Didn't know you can't have same var names in switches; I clearly
			// don't use switches enough
			// ....or .NET's compiler
			a.setDescription(request.getParameter("multiA"));
			a.setQuestion(question);
			a.setCorrect(false);

			b.setDescription(request.getParameter("multiB"));
			b.setQuestion(question);
			b.setCorrect(false);

			c.setDescription(request.getParameter("multiC"));
			c.setQuestion(question);
			c.setCorrect(false);

			d.setDescription(request.getParameter("multiD"));
			d.setQuestion(question);
			d.setCorrect(false);

			String correctA = request.getParameter("multiAnswer");
			switch (correctA) {
			case "A":
				a.setCorrect(true);
				question.setCorrectAnswer(a);
				break;
			case "B":
				b.setCorrect(true);
				question.setCorrectAnswer(b);
				break;
			case "C":
				c.setCorrect(true);
				question.setCorrectAnswer(c);
				break;
			case "D":
				d.setCorrect(true);
				question.setCorrectAnswer(d);
				break;
			// THIS SHOULD NEVER HIT
			default:
				break;
			}
			question.addAnswer(a);
			question.addAnswer(b);
			question.addAnswer(c);
			question.addAnswer(d);
			cm.AddQuestion(question);
			break;
		case "Text":
			question.setType(QuestionType.Text);
			question.setDescription(request.getParameter("shortQuestion"));

			a.setDescription(request.getParameter("shortAnswer"));
			a.setCorrect(true);
			a.setQuestion(question);
			question.addAnswer(a);
			question.setCorrectAnswer(a);

			cm.AddQuestion(question);
			break;

		default:
			break;
		}
	}

}
