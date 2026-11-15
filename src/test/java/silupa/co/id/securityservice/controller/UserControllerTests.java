package silupa.co.id.securityservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;

import silupa.co.id.securityservice.dto.request.ChangePasswordRequest;
import silupa.co.id.securityservice.dto.request.SignInRequest;
import silupa.co.id.securityservice.dto.request.SignUpRequest;
import silupa.co.id.securityservice.dto.response.ApiResponse;
import silupa.co.id.securityservice.service.UserAlreadyExistsException;
import silupa.co.id.securityservice.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTests {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@Test
	void signupReturnsCreatedForSuccessfulRegistration() {
		var response = userController.signup(new SignUpRequest());

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody().getSuccess()).isTrue();
	}

	@Test
	void signupReturnsConflictWithoutExposingWhichAccountFieldMatched() {
		when(userService.signUp(org.mockito.ArgumentMatchers.any(SignUpRequest.class)))
			.thenThrow(new UserAlreadyExistsException());

		var response = userController.signup(new SignUpRequest());

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		assertThat(response.getBody().getMessage()).isEqualTo("Username or email is already registered");
	}

	@Test
	void signinReturnsSameUnauthorizedMessageForInvalidCredentials() {
		when(userService.authenticate(org.mockito.ArgumentMatchers.any(SignInRequest.class)))
			.thenThrow(new BadCredentialsException("username not found"));

		var response = userController.signin(new SignInRequest());

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
		ApiResponse body = response.getBody();
		assertThat(body.getSuccess()).isFalse();
		assertThat(body.getMessage()).isEqualTo("Invalid username/email or password");
	}

	@Test
	void changePasswordUsesTheAuthenticatedTokenSubject() {
		var jwt = org.springframework.security.oauth2.jwt.Jwt.withTokenValue("token")
			.header("alg", "HS512")
			.subject("authenticated-user-id")
			.build();
		var request = new ChangePasswordRequest();
		request.setCurrentPassword("old-password");
		request.setNewPassword("new-password");

		var response = userController.changePassword(jwt, request);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		verify(userService).changePassword("authenticated-user-id", request);
	}
}
