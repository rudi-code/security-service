package silupa.co.id.securityservice.service.impl;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Locale;

import jakarta.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
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
import silupa.co.id.securityservice.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	@Transactional
	public User signUp(@Valid SignUpRequest signUp) {
		String username = normalize(signUp.getUsername());
		String email = normalize(signUp.getEmail());
		if (userRepository.existsByUsernameIgnoreCase(username) || userRepository.existsByEmailIgnoreCase(email)) {
			throw new UserAlreadyExistsException();
		}

		Role defaultRole = roleRepository.findByRole(AppConstants.ROLE_USER)
			.orElseThrow(() -> new IllegalStateException(
				"Default role " + AppConstants.ROLE_USER + " is not configured"));
		Instant now = Instant.now();
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setFullname(signUp.getFullname().strip());
		user.setPassword(passwordEncoder.encode(signUp.getPassword()));
		user.setActive(true);
		user.setAlamat(signUp.getAlamat().strip());
		user.setNoTelepon(signUp.getNoTelepon().strip());
		user.setSmartphone(signUp.getSmartphone().strip());
		user.setRole(defaultRole);
		user.setCreatedBy("SYSTEM");
		user.setCreatedDate(now);
		user.setLastModified(now);
		user.setLastModifiedBy("SYSTEM");

		return userRepository.save(user);
	}

	@Override
	public String authenticate(@Valid SignInRequest signIn) {
		if (signIn.getPassword().getBytes(StandardCharsets.UTF_8).length > 72) {
			throw new BadCredentialsException("Invalid username/email or password");
		}
		String usernameOrEmail = normalize(signIn.getUsernameOrEmail());
		Authentication authentication = authenticationManager.authenticate(
			UsernamePasswordAuthenticationToken.unauthenticated(usernameOrEmail, signIn.getPassword()));
		return jwtTokenProvider.generateToken((UserPrincipal) authentication.getPrincipal());
	}

	@Override
	@Transactional
	public void changePassword(String userId, @Valid ChangePasswordRequest changePassword) {
		if (changePassword.getCurrentPassword().getBytes(StandardCharsets.UTF_8).length > 72) {
			throw new BadCredentialsException("Current password is incorrect");
		}

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UsernameNotFoundException("Authenticated user no longer exists"));
		if (!passwordEncoder.matches(changePassword.getCurrentPassword(), user.getPassword())) {
			throw new BadCredentialsException("Current password is incorrect");
		}

		user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
		user.setLastModified(Instant.now());
		user.setLastModifiedBy(userId);
		userRepository.save(user);
	}

	private String normalize(String value) {
		return value.strip().toLowerCase(Locale.ROOT);
	}
}
