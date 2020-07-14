package silupa.co.id.securityservice.service;

import javax.validation.Valid;

import silupa.co.id.securityservice.model.ChangePasswordRequest;
import silupa.co.id.securityservice.model.SignInRequest;
import silupa.co.id.securityservice.model.SignUpRequest;
import silupa.co.id.securityservice.model.User;

public interface UserService {

	boolean existByUserName(String username);

	boolean existByEmail(String email);

	User signUp(@Valid SignUpRequest signUp);

	String autentication(@Valid SignInRequest signIn);

	boolean existByUsernameOrEmail(String usernameOrEmail);

	boolean changePassword(@Valid ChangePasswordRequest changePassword);


}
