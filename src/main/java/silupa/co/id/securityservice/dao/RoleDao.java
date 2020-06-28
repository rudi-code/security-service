package silupa.co.id.securityservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import silupa.co.id.securityservice.model.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Object> {

	Role findByName(String roleAdmin);

}
