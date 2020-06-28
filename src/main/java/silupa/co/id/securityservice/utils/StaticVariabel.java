package silupa.co.id.securityservice.utils;

public class StaticVariabel {
	
	/*#####----- ROLE NAME -----#####*/
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_GUESS = "ROLE_GUESS";
	public static final String ROLE_SUPERVISOR = "ROLE_SUPERVISOR";
	
	/*#####----- TIME -----#####*/
	public static final long MAX_AGE_SECS = 3600;
	
	/*#####----- SECURITY -----#####*/
	public static final String TOKEN_PREFIX = "Bearer";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_PATH = "/users/sign-up";
	public static final String SIGN_IN_PATH = "/users/sign-in";

}
