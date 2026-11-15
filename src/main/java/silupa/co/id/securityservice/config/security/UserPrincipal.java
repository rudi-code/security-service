package silupa.co.id.securityservice.config.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import silupa.co.id.securityservice.entity.User;

public class UserPrincipal implements UserDetails {
	
    private String userid;

    private String fullname;

    private String username;
	private String role;
	private boolean enabled;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

	public UserPrincipal(String userid, String fullName, String username, String email, String password,
			String role, boolean enabled) {
        this.userid = userid;
        this.fullname = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
		this.role = role;
		this.enabled = enabled;
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(
                user.getUserId(),
                user.getFullname(),
                user.getUsername(),
                user.getEmail(),
				user.getPassword(),
				user.getRole().getRole().startsWith("ROLE_")
					? user.getRole().getRole()
					: "ROLE_" + user.getRole().getRole(),
				user.isActive()
        );
    }

    public String getUserId() {
        return userid;
    }

    public String getFullName() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(userid, that.userid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userid);
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(role));
	}
}