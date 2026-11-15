package silupa.co.id.securityservice.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;

class UserRequestValidationTests {

	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	void signupRejectsPasswordsBeyondBcryptUtf8Limit() {
		SignUpRequest request = validSignUpRequest();
		request.setPassword("😀".repeat(19));

		Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(request);

		assertThat(violations).anyMatch(violation ->
			violation.getMessage().contains("72 UTF-8 bytes"));
	}

	@Test
	void signupTrimsUsernameAndEmailBeforeValidation() {
		SignUpRequest request = validSignUpRequest();
		request.setUsername("  test-user  ");
		request.setEmail("  test@example.com  ");

		assertThat(validator.validate(request)).isEmpty();
		assertThat(request.getUsername()).isEqualTo("test-user");
		assertThat(request.getEmail()).isEqualTo("test@example.com");
	}

	@Test
	void passwordChangeRequestRequiresCurrentAndNewPasswords() {
		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setCurrentPassword("current-password");
		request.setNewPassword("new-password");

		assertThat(validator.validate(request)).isEmpty();
	}

	private SignUpRequest validSignUpRequest() {
		SignUpRequest request = new SignUpRequest();
		request.setFullname("Test User");
		request.setUsername("test-user");
		request.setPassword("password123");
		request.setEmail("test@example.com");
		request.setNoTelepon("08123456789");
		request.setSmartphone("Android");
		request.setAlamat("Test address");
		return request;
	}
}
