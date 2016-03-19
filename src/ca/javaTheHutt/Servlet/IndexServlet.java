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
		cm = new ContextManager();

	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		/*
		 * ds = DataSourceFactory.getMySQLDataSource(); try { conn =
		 * ds.getConnection(); } catch (SQLException e) { e.printStackTrace(); }
		 */
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		if (user == null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
			response.getWriter().println("<p>WOO FUCKING LOO!</p>");
			rd.include(request, response);
			//rd.forward(request, response);
			
			
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");

			PrintWriter out = response.getWriter();
			Register r = new Register();
			out.println(r.main(new String[1]));
			response.getWriter().append("Served at: Murloc").append(request.getContextPath());
		}
	}
	
	/*
        	RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
            PrintWriter out= response.getWriter();
            out.println("<p style=\"color:red;\">Either email or password is wrong. "
            		+ "Please try again.</p>");
            rd.include(request, response);
	 * */

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
        String email = request.getParameter("email");
        String pwd = request.getParameter("password");
        
        User u = cm.Login(email, pwd);
        if(u==null){
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
			response.getWriter().println("<p>WOO FUCKING LOO!</p>");
			rd.include(request, response);
        }
        else{
			PrintWriter out = response.getWriter();
			Register r = new Register();
			response.getWriter().append("WOOOOOOOOLOOOOOOOOOOOLOOOOOOOOOOO!").append(request.getContextPath());
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
