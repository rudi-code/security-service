package silupa.co.id.securityservice.controller;

import java.sql.SQLException;

import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import silupa.co.id.securityservice.constants.AppConstants;
import silupa.co.id.securityservice.dto.request.ChangePasswordRequest;
import silupa.co.id.securityservice.dto.request.SignInRequest;
import silupa.co.id.securityservice.dto.request.SignUpRequest;
import silupa.co.id.securityservice.dto.response.ApiResponse;
import silupa.co.id.securityservice.dto.response.AuthenticationResponse;
import silupa.co.id.securityservice.service.UserAlreadyExistsException;
import silupa.co.id.securityservice.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping(AppConstants.SIGN_UP_PATH)
	public ResponseEntity<ApiResponse> signup(@Valid @RequestBody SignUpRequest signUp) {
		try {
			userService.signUp(signUp);
			return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse(true, "User registered successfully", null));
		} catch (UserAlreadyExistsException | DataIntegrityViolationException ex) {
			if (ex instanceof DataIntegrityViolationException && !isUniqueConstraintViolation(ex)) {
				throw ex;
			}
			return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ApiResponse(false, "Username or email is already registered", null));
		}
	}

	private boolean isUniqueConstraintViolation(Throwable error) {
		for (Throwable cause = error; cause != null; cause = cause.getCause()) {
			if (cause instanceof SQLException sqlException && "23505".equals(sqlException.getSQLState())) {
				return true;
			}
		}
		return false;
	}

	@PostMapping(AppConstants.SIGN_IN_PATH)
	public ResponseEntity<ApiResponse> signin(@Valid @RequestBody SignInRequest signIn) {
		try {
			String token = userService.authenticate(signIn);
			AuthenticationResponse payload = new AuthenticationResponse(token, AppConstants.TOKEN_PREFIX);
			return ResponseEntity.ok(new ApiResponse(true, "Token ready", payload));
		} catch (AuthenticationException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ApiResponse(false, "Invalid username/email or password", null));
		}
	}

	@PostMapping(AppConstants.CHANGE_PASSWORD_PATH)
	public ResponseEntity<ApiResponse> changePassword(
			@AuthenticationPrincipal Jwt jwt,
			@Valid @RequestBody ChangePasswordRequest changePassword) {
		try {
			userService.changePassword(jwt.getSubject(), changePassword);
			return ResponseEntity.ok(new ApiResponse(true, "Change password success", null));
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiResponse(false, "Current password is incorrect", null));
		} catch (AuthenticationException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ApiResponse(false, "Authentication is no longer valid", null));
		}
	}
}
