

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ExampleHelloWorldServlet
 * 
 * @author jimconrad
 * 
 * The server loads this class when the client's browser does the first
 * "GET" on its URL, e.g. http://localhost:8080/Suds4You/ExampleHelloWorldServlet 
 */
@WebServlet("/ExampleHelloWorldServlet")
public class ExampleHelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Setup the servlet to output an html response to the browser
		response.setContentType("text/html");
		
		//Get a PrintWriter to the client's browser
		PrintWriter out = response.getWriter();
		
		//Have the browser display the html hello message
		out.println("Hello, "+System.getProperty("user.name")+" on "+System.getProperty("os.name"));
	}

}
