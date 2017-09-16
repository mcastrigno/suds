

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet bridging the HTML login user interface and the business logic
 * <p>
 * Note to students:  You should not need to modify this class for your CS271 project.
 * <p>
 * A logged-in user has been authenticated (i.e. supplied valid credentials for
 * a CustomerAccount).  The server constructs an HttpSession object for a logged-in
 * user and that object includes a reference to the associated CustomerAccount 
 * object.  A session begins when an end-user supplies valid credentials (and
 * also begins when an end-user creates a new CustomerAccount).  A session ends when
 * an end-user either logs-out or the session times-out from inactivity.
 * <p>
 * While logged-in, an end-user is authorized to perform certain activities
 * defined in the Product Backlog that are denied to unauthenticated users.
 * <p>
 * Authentication is implemented in CustomerAccount business logic while sessions
 * are managed in the servlets.
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Invoked by tomcat processing index.html
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   doPost(request, response);
		}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//A login session begins with a successful authentication and ends with logout or time-out.  
		//While logged-in, an end-user is authorized to perform those activities defined by business policies.
		HttpSession loginSession;
		
		//Retrieve the user's credentials as entered at the keyboard
		String accountName = request.getParameter("account_name");
		String password = request.getParameter("password");
		
		Console.println("LoginServlet request for "+accountName+" on latant session="+ServletHelper.getSessionId(request));
		
		//Logout any lingering session
		if (ServletHelper.isLoggedIn(request)) {
			ServletHelper.logout(request);			//Kill existing session
		}
		
		//Process login request in business logic 
		try 	{ 
			  Console.println("LoginServlet enters the try block.\n");	
			  Console.println(accountName.toString());
			  CustomerAccount account = CustomerAccount.authenticateCredentials(accountName,password);	//Authenticate customer's credentials
			  Console.println("LoginServlet returned from authentication call to customer account.\n");			  
			  loginSession = ServletHelper.createSession(request, account); 								//Login the customer
			  ServletHelper.sendResponse(request, response, "Logged-in as "+accountName, "Ok", "index.html");
		} catch(Exception e) {
			  Console.println("Login request has failed, "+e.getMessage());
			  ServletHelper.sendResponse(request, response, "Login as "+accountName+" failed:  "+e.getMessage(), "Continue", "index.html");
		}	
	}

}
