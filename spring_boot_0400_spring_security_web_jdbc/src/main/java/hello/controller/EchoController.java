package hello.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
  
@Controller
@RequestMapping("/echo")
public class EchoController {  
	private static final Logger logger = LoggerFactory.getLogger(EchoController.class);

    @RequestMapping("/{id}")  
    public ModelAndView echo(@PathVariable("id") String id) {  
    	logger.info("oops: in EchoController.echo() now");
        ModelAndView mav = new ModelAndView();  
        mav.setViewName("echo");  
        mav.addObject("message", "Hello " + id + " !");  
        return mav;  
    }

}  