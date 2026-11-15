package silupa.co.id.securityservice.dto.request;

import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
	
	@NotBlank
	@Size(min = 4, max = 30)
	private String fullname;
	
	@NotBlank
	@Size(min = 3, max = 25)
	@Pattern(regexp = "^[a-zA-Z0-9._-]+$")
	@Setter(AccessLevel.NONE)
	private String username;
	
	@NotBlank
	@Size(min = 8, max = 72)
	private String password;
	
	@NotBlank
	@Email
	@Size(max = 254)
	@Setter(AccessLevel.NONE)
	private String email;
	
	@NotBlank
	@Size(max = 15)
	private String noTelepon;
	
	@NotBlank
	@Size(max = 15)
	private String smartphone;
	
	@NotBlank
	@Size(max = 50)
	private String alamat;

	@AssertTrue(message = "Password must not exceed 72 UTF-8 bytes")
	@JsonIgnore
	public boolean isPasswordWithinBcryptLimit() {
		return password == null || password.getBytes(StandardCharsets.UTF_8).length <= 72;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.strip();
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.strip();
	}

}
