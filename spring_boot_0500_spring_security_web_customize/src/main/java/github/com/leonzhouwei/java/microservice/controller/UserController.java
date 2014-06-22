package github.com.leonzhouwei.java.microservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
  
@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping("/")  
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView();  
        mav.setViewName("admin/user/list");  
        return mav;  
    }

}  