package silupa.co.id.securityservice.dto.request;

import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

	@NotBlank
	@Size(max = 72)
	private String currentPassword;
	
	@NotBlank
	@Size(min = 8, max = 72)
	private String newPassword;

	@AssertTrue(message = "New password must not exceed 72 UTF-8 bytes")
	@JsonIgnore
	public boolean isNewPasswordWithinBcryptLimit() {
		return newPassword == null || newPassword.getBytes(StandardCharsets.UTF_8).length <= 72;
	}

}
