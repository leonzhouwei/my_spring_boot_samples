package github.com.leonzhouwei.java.microservice;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

	@RequestMapping("/{id}")
	public User view(@PathVariable("id") String id) {
		User user = new User();
		user.setId(id);
		user.setName("zhang");
		return user;
	}

}
