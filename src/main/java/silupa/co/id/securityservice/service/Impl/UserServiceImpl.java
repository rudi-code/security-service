package silupa.co.id.securityservice.service.Impl;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import silupa.co.id.securityservice.dao.RoleDao;
import silupa.co.id.securityservice.dao.UserDao;
import silupa.co.id.securityservice.model.Role;
import silupa.co.id.securityservice.model.SignUpRequest;
import silupa.co.id.securityservice.model.User;
import silupa.co.id.securityservice.service.UserService;
import silupa.co.id.securityservice.utils.StaticVariabel;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	@Autowired
	RoleDao roleDao;
	@Autowired
	PasswordEncoder passwordEncoder;
	

	@Override
	public boolean existByUserName(String username) {
		// TODO Auto-generated method stub
		if(userDao.existsByUsername(username)) return true;
		return false;
	}

	@Override
	public boolean existByEmail(String email) {
		// TODO Auto-generated method stub
		if(userDao.existsByEmail(email)) return true;
		return false;
	}

	@Override
	public User signUp(@Valid SignUpRequest signUp) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUsername(signUp.getUsername());
		user.setEmail(signUp.getEmail());
		user.setFullname(signUp.getFullName());
		user.setPassword(passwordEncoder.encode(signUp.getPassword()));
		Role role = roleDao.findByName(StaticVariabel.ROLE_ADMIN)
				/*.orElseThrow(() -> new AppException("User Role not set."))*/;
		user.setRoles(Collections.singleton(role));
		
		User entity = userDao.save(user);
		return entity;
	}

}
