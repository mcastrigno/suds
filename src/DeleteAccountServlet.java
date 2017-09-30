

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for deleting the customer account associated with the current login session
 * <p>
 * Note to students:  You should not need to modify this class for your CS271 project.
 */
@WebServlet("/DeleteAccountServlet")
public class DeleteAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	/**
	 * Delete the customer account associated with the currently logged-in session
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 *<p>
	 * Tomcat calls this method after the end-user clicks on the Delete Account link 
	 * in index.html.
	 * <p>
	 * The end-user must be logged-in.  If they are not, ServletHelper.getAccount will
	 * fail to get the end-user's CustomerAccount object.
	 * <p>
	 * Deleting an account is different than logout.  Deletion refers to the destruction
	 * of the persisted customer data (that would be in a database in a real-world
	 * production server).  Logout refers to the destruction of the current session
	 * which maintains a reference to a CustomerAccount object.  The persisted data is
	 * what the server remembers for each customer.  The session is how the server
	 * remembers that an end-user has already supplied valid credentials for accessing a
	 * CustomerAccount object.
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Console.println("DeleteAccountServlet request on session id="+ServletHelper.getSessionId(request));
		try {
			CustomerAccount account = ServletHelper.getAccount(request);			//Fails if end-user is not logged-in 
			account.delete(); 													//Delete the end-user's account data
			Console.println("Account destroyed");
			ServletHelper.logout(request);										//Log-out the end-user fm this session
			ServletHelper.sendResponse(request, response, "Delete account succeeded"," Ok", "index.html");
		} catch(Exception e) {
			Console.println("Delete CustomerAccount failed on session="+ServletHelper.getSessionId(request));
			Console.println(e.getMessage());
		    //response.sendRedirect("DeleteAccountError.html");  				//Report failure to user
			ServletHelper.sendResponse(request, response, "Delete account failed: "+e.getMessage(),"Ok", "index.html");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
