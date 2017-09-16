/**
 * Very simple class for server-side console messages
 * @author kq7b
 * <p>
 * This class can be use to conditionally write logging messages to the server "Console" 
 *
 */
public class Console { 
	
	//variable defines if we are logging debugging messages to the console
	private final static boolean logging=true;	//Set to false to disable console logging messages
	
	//Methods for logging debugging messages to the console
	public static void println(String s) {
		if (logging) System.out.println(s);
	}
	

}
