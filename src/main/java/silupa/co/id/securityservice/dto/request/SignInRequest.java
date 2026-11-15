package silupa.co.id.securityservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
	
	@NotBlank
	@Size(max = 254)
	private String usernameOrEmail;
	
	@NotBlank
	@Size(max = 72)
	private String password;

}
