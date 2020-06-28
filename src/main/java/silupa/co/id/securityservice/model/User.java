package silupa.co.id.securityservice.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USERS")
@Getter
@Setter
public class User {
	
	@Id
	@Column(name = "USER_ID")
	@GenericGenerator(name = "USER_ID", strategy = "silupa.co.id.securityservice.model.UserIdGenerator")
	@GeneratedValue(generator = "USER_ID")
	@Size(max = 6)
	private String userId;
	
	@Column(name="EMAIL", unique=true, nullable=false)
	@Size(max=50)
	private String email;
	
	@Column(name="USERNAME", unique=true, nullable=false)
	@Size(max=25)
	private String username;
	
	@Column(name="PASSWORD")
	@NotBlank
	@Size(min = 6, max = 100)
	private String password;
	
	@Column(name="FULLNAME")
	@NotNull
	@Size(max=50)
	private String fullname;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<Role> roles = new HashSet<>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	

}
