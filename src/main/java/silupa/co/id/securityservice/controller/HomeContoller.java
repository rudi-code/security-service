package silupa.co.id.securityservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeContoller {

	@GetMapping("/")
	String index() {
		return "redirect:/swagger-ui.html";		
	}
}
