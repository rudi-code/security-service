package silupa.co.id.securityservice.service.Impl;

import java.util.Collections;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import silupa.co.id.securityservice.config.security.JwtTokenProvider;
import silupa.co.id.securityservice.dao.RoleDao;
import silupa.co.id.securityservice.dao.UserDao;
import silupa.co.id.securityservice.model.ChangePasswordRequest;
import silupa.co.id.securityservice.model.Role;
import silupa.co.id.securityservice.model.SignInRequest;
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
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	

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
		user.setFullname(signUp.getFullname());
		user.setPassword(passwordEncoder.encode(signUp.getPassword()));
		user.setActive("1");
		user.setAlamat(signUp.getAlamat());
		user.setCreatedBy("Admin");
		
		user.setCreatedDate(new Date());
		user.setLastModified(new Date());
		user.setLastModifiedBy("Admin");
		user.setNoTelepon(signUp.getNoTelepon());
		user.setSmartphone(signUp.getSmartphone());
		
		Role role = roleDao.findByRole(signUp.getRole());
		user.setRoleId(role.getId());
//		user.setRoleId(2);

//		Role role = roleDao.findByName(StaticVariabel.ROLE_ADMIN)
//				/*.orElseThrow(() -> new AppException("User Role not set."))*/;
//		user.setRoles(Collections.singleton(role));
		System.out.println("SAVE USER  "+ user);
		User entity = userDao.save(user);


		return entity;
	}

	@Override
	public String autentication(@Valid SignInRequest signIn) {
		// TODO Auto-generated method stub
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							signIn.getUsernameOrEmail(), 
							signIn.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtTokenProvider.generateToken(authentication);
			return token;
		} catch (Exception e) {
			// TODO: handle exception
			return StaticVariabel.MESSAGE_FAILED;
		}
		
		
	}

	@Override
	public boolean existByUsernameOrEmail(String usernameOrEmail) {
		// TODO Auto-generated method stub

		return userDao.existByUsernameOrEmail(usernameOrEmail) < 1;
	}

	@Override
	public boolean changePassword(@Valid ChangePasswordRequest changePassword) {
		// TODO Auto-generated method stub
		try {
			userDao.updatePassword(changePassword.getUsernameOrEmail(), 
					passwordEncoder.encode(changePassword.getPassword()));
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return true;
		}
		
		
	}

}
