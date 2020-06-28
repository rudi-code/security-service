package silupa.co.id.securityservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import silupa.co.id.securityservice.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Object> {

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);


}
