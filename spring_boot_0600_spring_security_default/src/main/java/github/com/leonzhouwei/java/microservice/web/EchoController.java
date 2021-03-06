package github.com.leonzhouwei.java.microservice.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
  
@Controller
@RequestMapping("/echo")
public class EchoController {
	private static final Logger logger = LoggerFactory.getLogger(EchoController.class);
	
    @RequestMapping("/")
    @Secured("ROLE_USER")
    public ModelAndView home() {  
        ModelAndView mav = new ModelAndView();  
        mav.setViewName("echo");  
        mav.addObject("message", "Hello!");  
        return mav;  
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @Secured("ROLE_USER")
    public ModelAndView echo(@PathVariable("id") String id) {  
        System.out.println("oops: in EchoController.echo() now");
        logger.debug("oops: in EchoController.echo() now");
        logger.info("oops: in EchoController.echo() now");
        ModelAndView mav = new ModelAndView();  
        mav.setViewName("echo");  
        mav.addObject("message", id + " from " + getUserName());  
        return mav;  
    }
    
    private String getUserName() {
    	String username = null;
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	if (principal instanceof UserDetails) {
    		username = ((UserDetails)principal).getUsername();
    	} else {
    		username = principal.toString();
    	}
    	return username;
    }

}  