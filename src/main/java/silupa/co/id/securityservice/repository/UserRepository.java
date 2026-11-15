package silupa.co.id.securityservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import silupa.co.id.securityservice.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	boolean existsByUsernameIgnoreCase(String username);

	boolean existsByEmailIgnoreCase(String email);

	@EntityGraph(attributePaths = "role")
	Optional<User> findByUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);

}
