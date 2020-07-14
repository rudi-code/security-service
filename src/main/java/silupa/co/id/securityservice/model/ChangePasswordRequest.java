package silupa.co.id.securityservice.model;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class ChangePasswordRequest {

	@ApiModelProperty(required=true, position=0)
	@NotBlank
	private String usernameOrEmail;
	
	@ApiModelProperty(required=true, position=1)
	@NotBlank
	private String password;

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
