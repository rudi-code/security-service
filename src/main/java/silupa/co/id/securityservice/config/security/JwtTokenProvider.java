package silupa.co.id.securityservice.config.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

	private final JwtEncoder jwtEncoder;
	private final long jwtExpiration;

	public JwtTokenProvider(
			JwtEncoder jwtEncoder,
			@Value("${app.jwtExpiration}") long jwtExpiration) {
		this.jwtEncoder = jwtEncoder;
		this.jwtExpiration = jwtExpiration;
	}

	public String generateToken(UserPrincipal userPrincipal) {
		Instant issuedAt = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
			.subject(userPrincipal.getUserId())
			.claim("roles", userPrincipal.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList()))
			.issuedAt(issuedAt)
			.expiresAt(issuedAt.plus(jwtExpiration, ChronoUnit.MILLIS))
			.build();
		JwsHeader header = JwsHeader.with(MacAlgorithm.HS512).build();

		return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
	}
}
