import java.util.ArrayList;

/**
 * Implements business logic/policies for the creation, authentication and deletion of customer accounts
 * 
 * @author kq7b
 * <p>
 * Note to students:  You will implement much of your CS271 project assignment in this class.
 * Before you can code anything, you'll use scrum to determine what functionality is required,
 * in which sprint you will implement that, how you will implement it, and who on your team
 * will implement it.
 * <p>
 * Business logic refers to the policies required by the business.  In the context of managing
 * customer accounts, these include what constitutes a legal or illegal accountName, the
 * password policies, and policies around repeated failed login attempts (by an attacker).
 * It is your job to capture what business needs in your Product Backlog.
 * <p>
 * This class models the customer account data as a static ArrayList<CustomerAccount>.  A real
 * production system would "persist" this in a database, perhaps in a different class
 * tasked with the so-called Data Access Object responsibilities.  However, the approach used
 * here would be viable even in a production product's earliest sprints testing the emerging
 * user interface and business logic before the project seeks the hassle of a real database.
 * A better design might separate the CustomerAccount business logic and the CustomerAccount
 * persistence, but this was not done here since the use of an ArrayList for the accounts
 * is obviously a temporary measure that would be refactored anyway in a real project.
 * <p>
 * Key design decision:  The only way another class can gain access to a CustomerAccount object
 * is with either the createNewAccount factory or by authenticating an account's credentials.
 * The idea is for the class design to protect customer data from wayward programmers in the future.
 * If you don't have the credentials, you can't access the data!
 *
 */
public class CustomerAccount {
	
	private static ArrayList<CustomerAccount> accountList = new ArrayList<CustomerAccount>();

	private String accountName;	//This user's chosen account name
	private String password;		//This user's chosen password
	private int nSequentialFailedAuthenticationAttempts;  //Counts sequence of failed login attempts
	private boolean isLocked;	//Locks account, making it unavailable until user requests support
	
	/**
	 * Initialize a new CustomerAccount object with policy-conforming account name and password
	 * @param accountName		//The acceptable user-supplied account name
	 * @param password			//The acceptable user-supplied password
	 * 
	 * <p>
	 * The constructor is invoked only by the factory contained within this class and is thus private as
	 * it should never be invoked outside of the factory.  The constructor assumes the accountName and
	 * password Strings conform to the relevant business policies defined in the Product Backlog.
	 * <p>
	 * Real world servers persist an account's data in a database and *never* save a plain-text password.
	 * We would never goto production with a plain-text design because, if a hacker steals the
	 * customer database, then they'd have the customers' passwords (that might also be used at other
	 * servers).  A real-world secure design (that could be implemented in a later sprint if this
	 * were are real-world project) would likely use password hashing.  You can learn more about
	 * password hashing at, https://www.wired.com/2016/06/hacker-lexicon-password-hashing/ 
	 */
	private CustomerAccount(String accountName, String password) {
		this.accountName = accountName;
		this.password = password;							
		this.nSequentialFailedAuthenticationAttempts = 0;
		this.isLocked = false;
	}

	/**
	 * Create new customer account factory, recording a reference to it in the accountList
	 * @param accountName		Name chosen by new user to reference their account
	 * @param password1			New user's chosen password as entered into the user interface
	 * @param password2			New user's repeated entry of their chosen password (should match password1)
	 * @return 					Reference to the newly created account
	 * @throws AccountException	If new user's account cannot be created for any reason
	 * <p>
	 * The factory approach separates the job of initializing the new CustomerAccount object in the constructor
	 * from the job of implementing the business policies for new accounts here in this code.  These policies
	 * deal with the issues of what is an acceptable accountName, the user's typing errors while entering
	 * the password, and the password's conformance to the various password policies defined in the Product
	 * Backlog.
	 * <p>
	 * This method is invoked by the CreateNewAccount servlet after the end-user has completed the form
	 * and clicked the Submit button in the CreateNewAccount.html user interface.  The accountName and the
	 * user's two attempts at typing their chosen password are passed as parameters but are unchecked (e.g.
	 * they may be invalid or even empty strings) when they arrive here.
	 */
	public static CustomerAccount createNewAccount(String accountName, String password1, String password2) throws AccountException {
	
		//TODO:  Implement business policies defined in Product Backlog for new accounts
		//first attempt just add the name and password
		String newAccountName = accountName; //mc
		String newPassword = password1;      //mc
		//If the user-supplied parameters are acceptable to the policies, create and record the new account
		try {
		
			//Create the account object and record a reference to it in the accountList
			CustomerAccount newAccount = new CustomerAccount(newAccountName, newPassword); //mc - these strings match mine but don't know if that was dumb luck
																						   // or I added them.
			accountList.add(newAccount);
		
			//Return reference to the new account object
			Console.println("Created CustomerAccount for "+accountName);
			return newAccount;
		
		} catch(Exception e) {

			//Report failure to create new account
			throw(new AccountException("Create New Account failed"));
	
		}
		
	} //createNewAccount
	
	/**
	 * Authenticate (verify) a customer's account credentials
	 * @param accountName		Uniquely identifies the customer
	 * @param password			Customer's secret password
	 * @return 					Reference to the CustomerAccount 
	 * @throw AccountException	Credential authentication failed
	 * <p>
	 * The LoginServlet invokes this method.
	 * <p>
	 * Assumes accountName is unique amongst all instances of CustomerAccount.
	 * <p>
	 * Implements the Product Backlog's business policy for locking an account after repeated failures
	 * <p>
	 * Here in this server, authentication refers to the verification of the end-user supplied
	 * credentials for the specified account.  Login refers to the establishment of an HttpSession
	 * for the server to remember that this browser's session has been authenticated.  You implement
	 * authentication here; login is already implemented in CreateNewAccountServlet and LoginServlet.
	 * <p>
	 * Design question for students:  Why is this method static???  Are there alternatives?  
	 */
	public static CustomerAccount authenticateCredentials(String accountName, String password) throws AccountException {
		// need to write code that looks up the accountName in the array list? 
		// not sure if this simply needs a return of an account name? 
		CustomerAccount accountNameToGet = null;
		Console.println("I am about to enter the for loop in the authenticateCredentials method of CustomerAccount.\n" +
						"The size of the accountList arrayList is: " +  accountList.size()+"\n");
		for ( int i = 0; i < accountList.size(); i++ ){
		CustomerAccount x = accountList.get(i);
		Console.println("I am in the for loop, here is the toString of account x" + x.toString());
			if(accountName == x.accountName) {
				accountNameToGet = x;
				
			}
		
		}
		//TODO:  Implement credential authentication
		
		//TODO:  Implement business policies defined in Product Backlog for locking an account after
		//repeated unsuccessful login attempts.
		return accountNameToGet;
		
		//throw(new AccountException("authenticateCredentials not even implemented"));
		
		
	} //authenticateAccount
	
	
	/**
	 * Administrative method to reset a customer's locked account
	 * @param accountName		Name of account to reset
	 * @throws AccountException	Account not found or other error arose
	 * <p>
	 * This method is included for a future administrative user who has authenticated a customer over the
	 * phone or in-person to reset a locked account and force a password change.  It should never be 
	 * accessible to a customer.
	 */
	public static void reset(String accountName) throws AccountException {
		//TODO:  Implement this someday when we're ready to unlock accounts.  This might become an extra credit
		//assignment.  Until then, it's just skeletal place-holder code, and not used in CS271.
		throw(new AccountException("reset not implemented"));				//Functionality not implemented
	} //reset
	
	/**
	 * Deletes this authenticated account
	 * @throws AccountException		Account cannot be deleted for some reason
	 * <p>
	 * The design of this method as a member function (rather than static) assures an account cannot 
	 * be deleted without first being authenticated (even by programming accident), a bit of business
	 * policy implemented via design. The design provides no mechanism for a newbie programmer to
	 * someday delete an account without first authenticating it.
	 */
	public void delete() throws AccountException {
		Console.println("CustomerAccount.delete()");
		//TODO:  Remove this account from the list of all accounts
		throw(new AccountException("delete not implemented"));
	} //delete
	
	
	/**
	 * Retrieves this account's name
	 * @return	This account's name
	 */
	public String getAccountName() {
		return this.accountName;
	}
	
	
	/**
	 * Deletes all existing customer accounts
	 */
	public static void deleteAllAccounts() {
		accountList = new ArrayList<CustomerAccount>();
	}
	
}
