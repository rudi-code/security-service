package silupa.co.id.securityservice.config.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

class JwtTokenProviderTests {

	private final JwtConfiguration configuration = new JwtConfiguration();

	@Test
	void generatedTokenCanBeDecodedBySpringSecurity() {
		String encodedSecret = Base64.getEncoder().encodeToString(new byte[64]);
		var signingKey = configuration.jwtSigningKey(encodedSecret);
		JwtEncoder encoder = configuration.jwtEncoder(signingKey);
		JwtDecoder decoder = configuration.jwtDecoder(signingKey);
		JwtTokenProvider tokenProvider = new JwtTokenProvider(encoder, 60_000);
		UserPrincipal principal = new UserPrincipal(
			"US001", "Test User", "test", "test@example.com", "password", "ROLE_USER", true);

		Jwt jwt = decoder.decode(tokenProvider.generateToken(principal));

		assertThat(jwt.getSubject()).isEqualTo("US001");
		assertThat(jwt.getClaimAsStringList("roles")).containsExactly("ROLE_USER");
		assertThat(jwt.getExpiresAt()).isAfter(jwt.getIssuedAt());
	}

	@Test
	void rejectsSigningKeysShorterThanHs512Requirement() {
		String encodedSecret = Base64.getEncoder().encodeToString(new byte[32]);

		assertThatThrownBy(() -> configuration.jwtSigningKey(encodedSecret))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("at least 64 bytes");
	}
}
