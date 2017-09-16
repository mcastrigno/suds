

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Logout servlet invalidates end-user's session with browser
 * <p>
 * Tomcat invokes this servlet after the end-user clicks on the Logout link in index.html
 * <p>
 * Note to students:  You should not need to modify this class for CS271
 * <p>
 * When the end-user clicks logout, the server invalidates the HttpSession object associating
 * this browser session with a CustomerAccount
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Console.println("LogoutServlet request on session="+ServletHelper.getSessionId(request));
		try {
			ServletHelper.logout(request);		//End the session, removing association between session and a CustomerAccount
		} catch (Exception e) {
			//Ignore errors
		}
		//response.sendRedirect("Success.html");
		  ServletHelper.sendResponse(request, response, "Logged-out from "+ServletHelper.getAccountName(request),"Continue", "index.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
