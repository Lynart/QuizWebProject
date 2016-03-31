package ca.javaTheHutt.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import ca.javaTheHutt.model.User;
import ca.javaTheHutt.testers.Register;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ContextManager cm;
	User user;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexServlet() {
		super();
	}

	// http://stackoverflow.com/questions/9381356/why-we-use-init-rather-constructor
	// Useful stuff
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		cm = new ContextManager();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// http://www.javapractices.com/topic/TopicAction.do?Id=181
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/*
	 * RequestDispatcher rd =
	 * getServletContext().getRequestDispatcher("/index.html"); PrintWriter out=
	 * response.getWriter(); out.println(
	 * "<p style=\"color:red;\">Either email or password is wrong. " +
	 * "Please try again.</p>"); rd.include(request, response);
	 */

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String email = request.getParameter("user");
		String pwd = request.getParameter("pass");

		user = cm.Login(email, pwd);
		if(request.getParameter("registerUser") != null){
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.html");
			rd.include(request, response);
		}else if(request.getParameter("addUser") != null){
			User u = new User(request.getParameter("addUser"),request.getParameter("pass1"));
			
			
			cm.AddUser(u);

			getServletContext().getRequestDispatcher("/admin").forward(request, response);
			//contentmanager.java
		}else if (user == null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
			rd.include(request, response);
		} else if (user.getLogin().equalsIgnoreCase("admin@admin.ca")){
			//Originally using the below for security, security will be assumed instead
			//request.getSession().setAttribute("user", user);
			getServletContext().getRequestDispatcher("/admin").forward(request, response);
		}else{
			HttpSession session = request.getSession(true);
			session.setAttribute("user", user);
			getServletContext().getRequestDispatcher("/user").forward(request, response);
		}
	}

	public void destroy() {
		if (cm != null) {
			try {
				cm.Destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
			cm = null;
		}
	}
}
