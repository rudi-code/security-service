package silupa.co.id.securityservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import silupa.co.id.securityservice.model.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Object> {

//	Role findByName(String roleAdmin);

//	@Query("from Role where role=:role")
//	Role findByRole(@Param("role")String role);
	
	@Query(
			  value = "SELECT * FROM roles u WHERE u.role = ?1", 
			  nativeQuery = true)
	Role findByRole(String role);

}
