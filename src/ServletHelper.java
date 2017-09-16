import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Helper definitions and methods for implementing servlets with tomcat
 * <p>
 * Note to students:  You should not need to modify this class for your CS271 project.
 * <p>
 * A session is how the server remembers that an end-user's browser has previously supplied valid
 * credentials (e.g. account name and password).  A session begins when a user either logs-in
 * to an existing account or creates a new account.  A session ends when the end-user either logs-out
 * or their session times-out from inactivity. 
 * 
 * @author kq7b
 *
 */
public class ServletHelper {
	
	//Constants
	private final static int SessionTimeoutSeconds = 60*30;		//Session times-out after 30 minutes of inactivity	
	
	
	/**
	 * Builds an HTML response message and a button to redirect the end-user to the next page (URL)
	 * @param request		Servlet's record of the browser's request
	 * @param response		Servlet's response to that request
	 * @param msg			The message text to include in the response
	 * @param buttonText		The button text to include in the response
	 * @param nextPage		The action to be taken when end-user clicks on the button
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public static void sendResponse(HttpServletRequest request, HttpServletResponse response,
			                        String msg, String buttonText, String nextPage) throws IOException, ServletException {
		
		//Configure servlet to transmit an HTML response to the browser
	    response.setContentType("text/html");

	    //Build the response header
	    PrintWriter out = response.getWriter();
	    String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
		
	    //Output the response without or with a button
	    if (buttonText==null) {
	    		out.println(docType + "<html>\n" +
	   	         "<head><title>" + msg + "</title></head>\n" +				//The page title
	   	         "<body bgcolor=\"#f0f0f0\">\n" +
	   	         "<h1 align=\"center\">" + msg + "</h1>\n" +					//The displayed message
	   	         "</body></html>");
	    	    	request.getRequestDispatcher(nextPage).forward(request, response);		//Avoid sendRedirect as some browsers lose session id
	    } else {
	    		out.println(docType + "<html>\n" +
	         "<head><title>" + msg + "</title></head>\n" +				//The page title
	         "<body bgcolor=\"#f0f0f0\">\n" +
	         "<h1 align=\"center\">" + msg + "</h1>\n" +					//The displayed message
	         "<form action=\""+nextPage+"\" method=\"POST\">" +			//Form to implement the button for next page
	         "<input type=\"submit\" value=\""+buttonText+"\" />" +		//Button text
	         "</form></body></html>");
	    }
	    
	   //Push response content to browser
	   out.close();
	}
	
	/**
	 * Creates a new session
	 * @param request		The new session is created for this request from the browser
	 * @param accountName	The customer's account name
	 * @param account		The CustomerAccount record for this session
	 * <p>
	 * We associate an HttpSession with an authenticated (logged-in) customer.  The servlet container implements the
	 * session machinery, and we are responsible for binding a CustomerAccount to that session.
	 */
	public static HttpSession createSession(HttpServletRequest request, CustomerAccount account) {
		HttpSession newSession;
		newSession = request.getSession(true);						//Create a new session
		newSession.setAttribute("name", account.getAccountName());	//Bind session to account name
		newSession.setAttribute("account",account);					//And the CustomerAccount object
		newSession.setMaxInactiveInterval(SessionTimeoutSeconds);		//Time-out after SessionTimeOutSeconds
		Console.println("Created session "+newSession.getId()+" for user="+newSession.getAttribute("name"));
		return newSession;
	}

	/**
	 * Retrieves a reference to the browser's session CustomerAccount object for this customer
	 * @param request				References the HttpRequest object
	 * @return						Reference to the CustomerAccount object for this session
	 * @throws IllegalStateException	This session has not been authenticated to a CustomerAccount
	 * <p>
	 * The CustomerAccount reference is only available for a logged-in session
	 */
	public static CustomerAccount getAccount(HttpServletRequest request) throws IllegalStateException {
		CustomerAccount theAccount = null;
		HttpSession theSession = request.getSession(false);
		if (theSession!=null) {
			theAccount = (CustomerAccount) theSession.getAttribute("account");
		}
		if (theSession==null || theAccount==null) {
			throw(new IllegalStateException("Customer does not have a login session"));
		}
		return theAccount;
	}
	
	public static String getAccountName(HttpServletRequest request) {
		CustomerAccount account;
		try {
			account = getAccount(request);
			return account.getAccountName();
		} catch(Exception e) {
			return "N/A";
		}
	}

	/**
	 * Logout the current session
	 * @param request
	 */
	public static void logout(HttpServletRequest request) {
		Console.println("Logging-out session="+getSessionId(request));
		HttpSession loginSession = request.getSession(false);
		if (loginSession!=null) {
			loginSession.invalidate();
		}
	}
	
	/**
	 * Returns session id string or N/A if no existing session
	 * 
	 * @param request
	 * @return
	 */
	public static String getSessionId(HttpServletRequest request) {
		HttpSession loginSession = request.getSession(false);
		if (loginSession!=null) {
			return loginSession.getId();
		} else {
			return "N/A";
		}
	}
	
	public static boolean isLoggedIn(HttpServletRequest request) {
		HttpSession theSession = request.getSession(false);				//The Session object or null if none
		return theSession!=null;											//If there's a Session then we might be logged in
	}

}
