package silupa.co.id.securityservice.service.impl;

import java.util.Locale;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import silupa.co.id.securityservice.config.security.UserPrincipal;
import silupa.co.id.securityservice.entity.User;
import silupa.co.id.securityservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		String normalized = usernameOrEmail.strip().toLowerCase(Locale.ROOT);
		User user = userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCase(normalized, normalized)
			.orElseThrow(() -> new UsernameNotFoundException("Invalid username/email or password"));
		return UserPrincipal.create(user);
	}
}
