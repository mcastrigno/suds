import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

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
@WebServlet("/EditAccountServlet") 
public class EditAccountServlet extends HttpServlet {
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
		String username1 = request.getParameter("username1");
		String username2 = request.getParameter("username2");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		
		// Much better way to do this probably..
		boolean updateUsername = true;
		boolean updatePassword = true;
		
		if(username1.equals("")) {
			// Don't want to update
			updateUsername = false;
		}
		else if(username1.equals(username2)) {
			// Check if usernames entered match each other
			// A nice email regex I found online //tp
	        String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)"+
	                              "*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]"+
	                              "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]"+
	                              "*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4]"+
	                              "[0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|"+
	                              "[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]"+
	                              "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	        
	        // Let's see if the email entered is an actual email //tp
	        boolean validEmail = Pattern.matches(emailPattern, username1);
	        if(!validEmail)
	            ServletHelper.sendResponse(request, response, "Username must be a valid email. Please try again.", "Ok", "EditAccount.html");
		}
		else
			ServletHelper.sendResponse(request, response, "Usernames entered did not match. Try again.", "Ok", "EditAccount.html");
        
		if(password1.equals("")) {
			// Don't want to update
			updatePassword = false;
		}
		else if(password1.equals(password2)) {
			// Check if passwords entered match each other
			// A nice password regex I found online: //tp
	        //     1 lowercase alpha, 
	        //     1 uppercase alpha,
	        //     1 numeric,
	        //     1 special char,
	        //     at least 8 characters long
	        String passwordPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\\$%\\^&\\*]).{8,}$";
	        
	        // Let's see if the password that was entered is a valid one //tp
	        boolean validPassword = Pattern.matches(passwordPattern, password1);
	        if(!validPassword) {
	        		ServletHelper.sendResponse(request, response, "Please enter a valid password at least 8 characters long with 1 uppercase letter, "+
							  								 "1 lowercase letter, 1 number, and 1 special character.", "Ok", "EditAccount.html");
	        }
		}
		else
			ServletHelper.sendResponse(request, response, "Passwords entered did not match. Try again.", "Ok", "EditAccount.html");
		
		//Process edit new account
		try 	{ 
			CustomerAccount account = ServletHelper.getAccount(request);
			System.out.println("Account found: "+account.getAccountName());
			if(updateUsername == true) {
				account.updateAccountName(username1);
				System.out.println("Successfully updated acount name.");
			}
			
			if(updatePassword == true) {
				account.updatePassword(password1);
				System.out.println("Successfully updated password.");
			}
		} catch(Exception e) {
			  //Report failure
			  ServletHelper.sendResponse(request, response, "Edit account failed: "+e.getMessage(), "Ok", "EditAccount.html");
		}	  
		
		//Send response to browser
		ServletHelper.sendResponse(request, response, "Successfully updated your account information.", "Ok", "index.html");

	}
}
