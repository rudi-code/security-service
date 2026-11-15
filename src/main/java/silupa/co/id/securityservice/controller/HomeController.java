package silupa.co.id.securityservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index() {
		return "redirect:/swagger-ui.html";
	}
}
