package silupa.co.id.securityservice.service;

public class UserAlreadyExistsException extends RuntimeException {

	public UserAlreadyExistsException() {
		super("Username or email is already registered");
	}
}
