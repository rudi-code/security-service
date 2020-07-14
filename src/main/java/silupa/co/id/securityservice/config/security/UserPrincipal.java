package silupa.co.id.securityservice.config.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import silupa.co.id.securityservice.model.User;

public class UserPrincipal implements UserDetails {
	
    private String userid;

    private String fullname;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

//    private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(String userid, String fullName, String username, String email,
			String password/* , Collection<? extends GrantedAuthority> authorities */) {
        this.userid = userid;
        this.fullname = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
//        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
		/*
		 * List<GrantedAuthority> authorities = ( user.getRoles()).stream().map(role ->
		 * new SimpleGrantedAuthority( (role).getName()) ).collect(Collectors.toList());
		 */

        return new UserPrincipal(
                user.getUserId(),
                user.getFullname(),
                user.getUsername(),
                user.getEmail(),
				user.getPassword()/*
									 * , authorities
									 */
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

	/*
	 * @Override public Collection<? extends GrantedAuthority> getAuthorities() {
	 * return authorities; }
	 */

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
        return true;
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
		// TODO Auto-generated method stub
		return null;
	}
}