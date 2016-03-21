package ca.javaTheHutt.Servlet;

import java.io.IOException;

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

		} else {
			doGet(request, response);
		}
	}

	private void addQuestion(HttpServletRequest request) {
		String questionType = request.getParameter("questionType");
		//int difficulty = Integer.parseInt(request.getParameter("difficulty"));
		Question question = new Question();
		switch (questionType) {
		case "Checkbox":
			question.setType(QuestionType.Checkbox);
			//question.setDifficulty(difficulty);
			question.setDescription(request.getParameter("checkQuestion"));
			
			//I admit, this looks stupid but I am not sure if you have to set both ends
			//I miss .NET entity framework
			Answer a = new Answer();
			a.setQuestion(question);
			if(request.getParameter("boolA")!=null)
				a.setCorrect(true);
			else
				a.setCorrect(false);
			a.setDescription(request.getParameter("checkA"));
			
			Answer b = new Answer();
			b.setQuestion(question);
			if(request.getParameter("boolB")!=null)
				b.setCorrect(true);
			else
				b.setCorrect(false);
			b.setDescription(request.getParameter("checkB"));
			
			Answer c = new Answer();
			c.setQuestion(question);
			if(request.getParameter("boolC")!=null)
				c.setCorrect(true);
			else
				c.setCorrect(false);
			c.setDescription(request.getParameter("checkC"));
		
			Answer d = new Answer();
			d.setQuestion(question);
			if(request.getParameter("boolD")!=null)
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
		case "Multichoice":
			break;
		case "Text":
			break;
				
		default:
			break;
		}
	}

}
