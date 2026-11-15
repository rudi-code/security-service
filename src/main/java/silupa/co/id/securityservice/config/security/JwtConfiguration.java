package silupa.co.id.securityservice.config.security;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
public class JwtConfiguration {

	@Bean
	SecretKey jwtSigningKey(@Value("${app.jwtSecret}") String encodedSecret) {
		final byte[] keyBytes;
		try {
			keyBytes = Base64.getDecoder().decode(encodedSecret);
		} catch (IllegalArgumentException ex) {
			throw new IllegalStateException("app.jwtSecret must be a Base64-encoded key", ex);
		}
		if (keyBytes.length < 64) {
			throw new IllegalStateException("app.jwtSecret must decode to at least 64 bytes for HS512");
		}
		return new SecretKeySpec(keyBytes, "HmacSHA512");
	}

	@Bean
	JwtEncoder jwtEncoder(SecretKey jwtSigningKey) {
		return new NimbusJwtEncoder(new ImmutableSecret<>(jwtSigningKey));
	}

	@Bean
	JwtDecoder jwtDecoder(SecretKey jwtSigningKey) {
		return NimbusJwtDecoder.withSecretKey(jwtSigningKey)
			.macAlgorithm(MacAlgorithm.HS512)
			.build();
	}
}
