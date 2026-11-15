package silupa.co.id.securityservice.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import silupa.co.id.securityservice.config.security.JwtTokenProvider;
import silupa.co.id.securityservice.config.security.UserPrincipal;
import silupa.co.id.securityservice.constants.AppConstants;
import silupa.co.id.securityservice.dto.request.ChangePasswordRequest;
import silupa.co.id.securityservice.dto.request.SignInRequest;
import silupa.co.id.securityservice.dto.request.SignUpRequest;
import silupa.co.id.securityservice.entity.Role;
import silupa.co.id.securityservice.entity.User;
import silupa.co.id.securityservice.repository.RoleRepository;
import silupa.co.id.securityservice.repository.UserRepository;
import silupa.co.id.securityservice.service.UserAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {

	@Mock
	private UserRepository userRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtTokenProvider jwtTokenProvider;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void signupAlwaysAssignsDefaultUserRoleAndNormalizesIdentity() {
		Role defaultRole = new Role();
		defaultRole.setRole(AppConstants.ROLE_USER);
		when(roleRepository.findByRole(AppConstants.ROLE_USER)).thenReturn(Optional.of(defaultRole));
		when(passwordEncoder.encode("password123")).thenReturn("encoded-password");
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
		SignUpRequest request = signUpRequest();
		request.setUsername(" Test-User ");
		request.setEmail(" TEST@Example.com ");

		User createdUser = userService.signUp(request);

		assertThat(createdUser.getRole()).isSameAs(defaultRole);
		assertThat(createdUser.getPassword()).isEqualTo("encoded-password");
		assertThat(createdUser.getUsername()).isEqualTo("test-user");
		assertThat(createdUser.getEmail()).isEqualTo("test@example.com");
		assertThat(createdUser.isActive()).isTrue();
		assertThat(createdUser.getCreatedBy()).isEqualTo("SYSTEM");
		verify(roleRepository).findByRole(AppConstants.ROLE_USER);
	}

	@Test
	void signupFailsClearlyWhenDefaultRoleIsNotConfigured() {
		when(roleRepository.findByRole(AppConstants.ROLE_USER)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> userService.signUp(signUpRequest()))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining(AppConstants.ROLE_USER);
	}

	@Test
	void signupRejectsDuplicateUsernameOrEmail() {
		when(userRepository.existsByUsernameIgnoreCase("test-user")).thenReturn(true);

		assertThatThrownBy(() -> userService.signUp(signUpRequest()))
			.isInstanceOf(UserAlreadyExistsException.class);
		verify(userRepository, never()).save(any(User.class));
		verify(roleRepository, never()).findByRole(AppConstants.ROLE_USER);
	}

	@Test
	void signupRejectsDuplicateEmail() {
		when(userRepository.existsByUsernameIgnoreCase("test-user")).thenReturn(false);
		when(userRepository.existsByEmailIgnoreCase("test@example.com")).thenReturn(true);

		assertThatThrownBy(() -> userService.signUp(signUpRequest()))
			.isInstanceOf(UserAlreadyExistsException.class);
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void signinAuthenticatesNormalizedIdentifierAndIssuesToken() {
		UserPrincipal principal = new UserPrincipal(
			"US001", "Test User", "test-user", "test@example.com", "encoded",
			AppConstants.ROLE_USER, true);
		Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(
			principal, null, principal.getAuthorities());
		when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
		when(jwtTokenProvider.generateToken(principal)).thenReturn("signed-token");
		SignInRequest request = new SignInRequest();
		request.setUsernameOrEmail("  TEST@EXAMPLE.COM ");
		request.setPassword("password123");

		assertThat(userService.authenticate(request)).isEqualTo("signed-token");
		verify(authenticationManager).authenticate(any(Authentication.class));
	}

	@Test
	void signinPropagatesAuthenticationFailureForGenericControllerHandling() {
		when(authenticationManager.authenticate(any(Authentication.class)))
			.thenThrow(new BadCredentialsException("bad credentials"));
		SignInRequest request = new SignInRequest();
		request.setUsernameOrEmail("missing");
		request.setPassword("password123");

		assertThatThrownBy(() -> userService.authenticate(request))
			.isInstanceOf(BadCredentialsException.class);
	}

	@Test
	void changePasswordRequiresCurrentPasswordAndSavesNewHash() {
		User user = existingUser();
		when(userRepository.findById("user-id")).thenReturn(Optional.of(user));
		when(passwordEncoder.matches("old-password", "old-hash")).thenReturn(true);
		when(passwordEncoder.encode("new-password")).thenReturn("new-hash");
		ChangePasswordRequest request = changePasswordRequest("old-password", "new-password");

		userService.changePassword("user-id", request);

		assertThat(user.getPassword()).isEqualTo("new-hash");
		assertThat(user.getLastModifiedBy()).isEqualTo("user-id");
		verify(userRepository).save(user);
	}

	@Test
	void changePasswordRejectsAnIncorrectCurrentPassword() {
		when(userRepository.findById("user-id")).thenReturn(Optional.of(existingUser()));
		when(passwordEncoder.matches("wrong-password", "old-hash")).thenReturn(false);

		assertThatThrownBy(() -> userService.changePassword(
			"user-id", changePasswordRequest("wrong-password", "new-password")))
			.isInstanceOf(BadCredentialsException.class);
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void changePasswordRejectsCurrentPasswordsOverBcryptByteLimit() {
		String overlongPassword = "a".repeat(73);

		assertThatThrownBy(() -> userService.changePassword(
			"user-id", changePasswordRequest(overlongPassword, "new-password")))
			.isInstanceOf(BadCredentialsException.class);
		verify(passwordEncoder, never()).matches(any(), any());
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void inactiveUsersAreDisabledAndReceiveTheirRoleAuthority() {
		User user = existingUser();
		user.setActive(false);

		UserPrincipal principal = UserPrincipal.create(user);

		assertThat(principal.isEnabled()).isFalse();
		assertThat(principal.getAuthorities())
			.extracting("authority")
			.containsExactly(AppConstants.ROLE_USER);
	}

	private User existingUser() {
		User user = new User();
		user.setUsername("test-user");
		user.setEmail("test@example.com");
		user.setPassword("old-hash");
		user.setActive(true);
		Role role = new Role();
		role.setRole(AppConstants.ROLE_USER);
		user.setRole(role);
		return user;
	}

	private ChangePasswordRequest changePasswordRequest(String currentPassword, String newPassword) {
		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setCurrentPassword(currentPassword);
		request.setNewPassword(newPassword);
		return request;
	}

	private SignUpRequest signUpRequest() {
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
