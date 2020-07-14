package silupa.co.id.securityservice.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

public class SignUpRequest {
	
	@ApiModelProperty(required=true, position=0)
	@NotBlank
	@Size(min = 4, max = 30)
	private String fullname;
	
	@ApiModelProperty(required=true, position=1)
	@NotBlank
	@Size(max = 25)
	private String username;
	
	@ApiModelProperty(required=true, position=2)
	@NotBlank
	private String password;
	
	@ApiModelProperty(required=true, position=3)
	@NotBlank
	@Email
	private String email;
	
	@ApiModelProperty(required=true, position=4)
	@NotBlank
	private String noTelepon;
	
	@ApiModelProperty(required=true, position=5)
	@NotBlank
	private String smartphone;
	
	@ApiModelProperty(required=true, position=6)
	@NotBlank
	private String role;
	
	@ApiModelProperty(required=true, position=7)
	@NotBlank
	private String alamat;
	
	
	

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getNoTelepon() {
		return noTelepon;
	}

	public void setNoTelepon(String noTelepon) {
		this.noTelepon = noTelepon;
	}

	public String getSmartphone() {
		return smartphone;
	}

	public void setSmartphone(String smartphone) {
		this.smartphone = smartphone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
