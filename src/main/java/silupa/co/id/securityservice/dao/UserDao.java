package silupa.co.id.securityservice.dao;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import silupa.co.id.securityservice.model.ChangePasswordRequest;
import silupa.co.id.securityservice.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Object> {

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	Optional<User> findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail2);

	

	@Query(
		  value = "SELECT count(1) FROM users u WHERE u.username in (?1, ?1) or u.email in (?1, ?1)", 
		  nativeQuery = true)
	int existByUsernameOrEmail(String usernameOrEmail);

	@Query(
		  value = "UPDATE users SET password = ?2 WHERE username in (?1, ?1) or email in (?1, ?1)", 
		  nativeQuery = true)
	void updatePassword(String usernameOrEmail, String password);


}
