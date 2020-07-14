package silupa.co.id.securityservice.utils;

public class StaticVariabel {
	
	/*#####----- SEPARATOR -----#####*/
	public static final String SEPARATOR_SPACE = " ";
	public static final String SEPARATOR_COMA = ",";
	public static final String SEPARATOR_DOT = ".";
	
	/*#####----- MESSAGE -----#####*/
	public static final String MESSAGE_FAILED = "Failed";
	public static final String MESSAGE_SUCCESS = "Success";
	public static final String MESSAGE_ALREADY_EXIST = "already exist";
	public static final String MESSAGE_DID_NOT_EXIST = "did not exist";
	public static final String MESSAGE_USERNAME = "Username";
	public static final String MESSAGE_EMAIL = "Email";
	public static final String MESSAGE_PASSWORD = "Password";
	public static final String MESSAGE_REGISTER = "Register";
	
	
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
	public static final String CHANGE_PASSWORD = "/users/change-password";
	public static final String DELETE_USER = "/users/delete-user";

}
