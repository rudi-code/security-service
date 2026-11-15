package silupa.co.id.securityservice.constants;

public final class AppConstants {
	private AppConstants() {
	}

	public static final String ROLE_USER = "ROLE_USER";

	public static final long MAX_AGE_SECONDS = 3600;

	public static final String TOKEN_PREFIX = "Bearer";
	public static final String SIGN_UP_PATH = "/users/sign-up";
	public static final String SIGN_IN_PATH = "/users/sign-in";
	public static final String CHANGE_PASSWORD_PATH = "/users/change-password";
}
