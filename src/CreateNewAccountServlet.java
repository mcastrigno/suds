

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet for creating a new customer account and logging-in the new user
 * <p>
 * Note to students:  You should not need to modify this code for the CS271 project although, if you are using
 * the debugger, you may wish to place breakpoints or log console messages here.
 * <p>
 * This servlet connects tomcat with the CustomerAccount business logic.  The end-user browses the
 * CreateNewAccount.html web page (probably reached from a link in index.html).  When they complete
 * the form and click Submit, their browser "posts" the form data to tomcat that invokes the doPost
 * method below.  The doPost method invokes CustomerAccount's createNewAccount factory method and then
 * creates an HttpSession to effectively login the user of the new account.  If all goes well, doPost
 * redirects the end-user's browser to the Success.html page.
 * <p>
 * An HttpSession is the foundation of how the server keeps track of an authenticated (logged-in) user.
 * An authenticated session begins when the server receives valid credentials for an account, and ends when
 * they logout or time-out from inactivity.
 */
@WebServlet("/CreateNewAccountServlet") 
public class CreateNewAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession loginSession;
		// 
		// We make to here
		//
		Console.println("CreateNewAccountServlet request on latant session="+ServletHelper.getSessionId(request));
	      
		//Retrieve the user's input from the request form POSTed by the browser to tomcat
		String accountName = request.getParameter("account_name");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		
		//Process create new account request thru business logic and leave this new account logged-in if successful
		try 	{ 
			  Console.println("We entered the try block.\n");
			  ServletHelper.logout(request); 																//Kill existing session if any
			  Console.println("We succesfully killed any old sessions.\n");
			  CustomerAccount account = CustomerAccount.createNewAccount(accountName,password1,password2);  	//Create the CustomerAccount
			  Console.println("We came back from creating the account\n");
			  loginSession = ServletHelper.createSession(request, account);									//Login new customer
		} catch(Exception e) {
			  //Report failure
			  ServletHelper.sendResponse(request, response,  "Create account, "+accountName+", failed:  "+e.getMessage(), "Continue", "CreateNewAccount.html");
		}	  
		
		//Send response to browser
		ServletHelper.sendResponse(request, response, "Created and logged into new account "+ServletHelper.getAccountName(request),"Ok", "index.html");

	}
}
