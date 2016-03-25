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

import ca.javaTheHutt.model.Question;
import ca.javaTheHutt.model.Quiz;
import ca.javaTheHutt.model.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ContextManager cm;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("takeNewQuiz") != null) {
			User u = (User) request.getSession().getAttribute("user");
			Quiz quiz = cm.GenerateQuiz(u);
			Question question = quiz.getQuestions().iterator().next();
			// In a real world scenario, you'd want the answers to be checked on
			// the server but this is faster
			request.setAttribute("question", question);
			request.setAttribute("quizId", quiz.getId());
			request.setAttribute("answerText", question.getAnswerText());
			request.setAttribute("correctIndex", question.getCorrectIndexes());
			request.setAttribute("questionIndex", 0);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/quiz.jsp");
			rd.include(request, response);
		} else if (request.getParameter("questionIndex") != null) {
			String[] userAnswers = new String[4];
			if (request.getParameter("questionType").equalsIgnoreCase("Checkbox")) {
				userAnswers[0] = request.getParameter("userAnswerA");
				userAnswers[1] = request.getParameter("userAnswerB");
				userAnswers[2] = request.getParameter("userAnswerC");
				userAnswers[3] = request.getParameter("userAnswerD");
			} else {
				userAnswers[0] = request.getParameter("userAnswer");
			}
			int quizId = Integer.valueOf(request.getParameter("quizId"));
			int questionIndex = Integer.valueOf(request.getParameter("questionIndex"));
			Question question = cm.UpdateQuiz(quizId, questionIndex, userAnswers);
			if (question != null) {
				request.setAttribute("question", question);
				request.setAttribute("quizId", quizId);
				request.setAttribute("answerText", question.getAnswerText());
				request.setAttribute("correctIndex", question.getCorrectIndexes());
				request.setAttribute("questionIndex", ++questionIndex);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/quiz.jsp");
				rd.include(request, response);
			}
			else{
				User u = (User) request.getSession().getAttribute("user");
				Collection<Quiz> quizes = cm.getQuizzes(u);
				request.setAttribute("quizes", quizes);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/viewResults.jsp");
				rd.include(request, response);
			}
		} else if (request.getParameter("viewResults") != null){
			User u = (User) request.getSession().getAttribute("user");
			Collection<Quiz> quizes = cm.getQuizzes(u);
			request.setAttribute("quizes", quizes);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/viewResults.jsp");
			rd.include(request, response);
		} else {

			RequestDispatcher rd = getServletContext().getRequestDispatcher("/userMenu.html");
			rd.include(request, response);
		}
	}

}
