package silupa.co.id.securityservice.service;

import jakarta.validation.Valid;

import silupa.co.id.securityservice.dto.request.ChangePasswordRequest;
import silupa.co.id.securityservice.dto.request.SignInRequest;
import silupa.co.id.securityservice.dto.request.SignUpRequest;
import silupa.co.id.securityservice.entity.User;

public interface UserService {

	User signUp(@Valid SignUpRequest signUp);

	String authenticate(@Valid SignInRequest signIn);

	void changePassword(String userId, @Valid ChangePasswordRequest changePassword);

}
