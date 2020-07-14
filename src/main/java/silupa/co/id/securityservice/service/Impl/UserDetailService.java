package silupa.co.id.securityservice.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import silupa.co.id.securityservice.config.security.UserPrincipal;
import silupa.co.id.securityservice.dao.UserDao;
import silupa.co.id.securityservice.model.User;




@Service
public class UserDetailService implements UserDetailsService{

	
	@Autowired
	UserDao userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		
		// can login with username or email
		User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow( () -> new UsernameNotFoundException("User not found with username or email" + usernameOrEmail)
		);
		return UserPrincipal.create(user);
	}
	
	//used by JWT authentication filter	
	@Transactional
	public UserDetails loadUserById(String id) {
		User user = userRepository.findById(id)
				.orElseThrow( () -> new UsernameNotFoundException("User not found with id " + id)
			);
		return UserPrincipal.create(user);
		
	}

}
