package github.com.leonzhouwei.java.microservice.web;

import java.util.Date;
import java.util.Map;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	@Secured("ROLE_ADMIN")
	public String home(Map<String, Object> model) {
		model.put("message", "Hello World");
		model.put("title", "Hello Home");
		model.put("date", new Date());
		return "home";
	}

}