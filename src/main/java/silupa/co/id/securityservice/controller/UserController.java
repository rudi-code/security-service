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

import silupa.co.id.securityservice.model.SignInRequest;
import silupa.co.id.securityservice.model.SignUpRequest;
import silupa.co.id.securityservice.model.response.ApiResponse;
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
			apiResponse.setMessage("Username already exist.");
		}else if (userService.existByEmail(signUp.getEmail())) {
			apiResponse.setSuccess(false);
			apiResponse.setMessage("Email already exist.");
		}else {
			userService.signUp(signUp);
			apiResponse.setSuccess(true);
			apiResponse.setMessage("Username already registered.");
		}
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
	@PostMapping(StaticVariabel.SIGN_IN_PATH)
	public ResponseEntity<ApiResponse> signin(@Valid @RequestBody SignInRequest signIn) {
		ApiResponse apiResponse = new ApiResponse();
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
}
