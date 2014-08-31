package github.com.leonzhouwei.java.microservice.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
  
@Controller
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    @Secured("ROLE_USER")
    public ModelAndView upload(@RequestParam("key")String value) {  
    	String ret = "\"" + value + "\" wrapped in POST from user " + getUserName();
    	logger.debug(ret);
        ModelAndView mav = new ModelAndView();  
        mav.setViewName("upload");  
        mav.addObject("message", ret);  
        return mav;  
    }
    
    @RequestMapping(value="/uploadByPutInPost", method=RequestMethod.PUT)
    @Secured("ROLE_USER")
    public ModelAndView uploadByPutInPost() {  
    	String ret = "received HTTP PUT wrapped in POST from user " + getUserName();
    	logger.debug(ret);
        ModelAndView mav = new ModelAndView();  
        mav.setViewName("upload");  
        mav.addObject("message", ret);  
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