package silupa.co.id.securityservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import silupa.co.id.securityservice.model.ChangePasswordRequest;
import silupa.co.id.securityservice.model.SignInRequest;
import silupa.co.id.securityservice.model.SignUpRequest;
import silupa.co.id.securityservice.model.response.ApiResponse;
import silupa.co.id.securityservice.model.response.AuthenticationResponse;
import silupa.co.id.securityservice.service.UserService;
import silupa.co.id.securityservice.utils.StaticVariabel;;


@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping(StaticVariabel.SIGN_UP_PATH)
	public ResponseEntity<ApiResponse> signup(@Valid @RequestBody SignUpRequest signUp) {
		ApiResponse apiResponse = new ApiResponse();
		if(userService.existByUserName(signUp.getUsername())) {
			apiResponse.setSuccess(false);
			apiResponse.setMessage(StaticVariabel.MESSAGE_USERNAME
					+StaticVariabel.SEPARATOR_SPACE
					+StaticVariabel.MESSAGE_ALREADY_EXIST);
		}else if (userService.existByEmail(signUp.getEmail())) {
			apiResponse.setSuccess(false);
			apiResponse.setMessage(StaticVariabel.MESSAGE_EMAIL
					+StaticVariabel.SEPARATOR_SPACE
					+StaticVariabel.MESSAGE_ALREADY_EXIST);
		}else {
			userService.signUp(signUp);
			apiResponse.setSuccess(true);
			apiResponse.setMessage(StaticVariabel.MESSAGE_USERNAME
					+StaticVariabel.SEPARATOR_SPACE
					+StaticVariabel.MESSAGE_SUCCESS
					+StaticVariabel.SEPARATOR_SPACE
					+StaticVariabel.MESSAGE_REGISTER);
		}
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
	@PostMapping(StaticVariabel.SIGN_IN_PATH)
	public ResponseEntity<Object> signin(@Valid @RequestBody SignInRequest signIn) {
		
		ApiResponse apiResponse = new ApiResponse();
		if(userService.existByUsernameOrEmail(signIn.getUsernameOrEmail())) {
			
			apiResponse.setSuccess(false);
			apiResponse.setMessage("Username or email didn't exist.");
			
		}else {
			String auth = userService.autentication(signIn);
			if(auth.equals(StaticVariabel.MESSAGE_FAILED)) {
				apiResponse.setSuccess(false);
				apiResponse.setMessage("Password Wrong.");
			}else {
				AuthenticationResponse response = 
						new AuthenticationResponse(auth, StaticVariabel.TOKEN_PREFIX);
				apiResponse.setSuccess(true);
				apiResponse.setMessage("Token ready");
				apiResponse.setPayload(response);
			}
		}
		
		
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@PostMapping(StaticVariabel.CHANGE_PASSWORD)
	public ResponseEntity<ApiResponse> changePassword(@Valid @RequestBody ChangePasswordRequest changePassword) {
		
		ApiResponse apiResponse = new ApiResponse();
		
		if(userService.existByUsernameOrEmail(changePassword.getUsernameOrEmail())) {
			apiResponse.setSuccess(false);
			apiResponse.setMessage("Username or email didn't exist.");
		}else if(userService.changePassword(changePassword)){
			apiResponse.setSuccess(true);
			apiResponse.setMessage("Change Password Success.");
		}else {

			apiResponse.setSuccess(false);
			apiResponse.setMessage("Change Password Failed.");
			
		}
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
		
	}
	
}
