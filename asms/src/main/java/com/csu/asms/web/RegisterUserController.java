package com.csu.asms.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.csu.asms.domain.service.UserService;
import com.csu.asms.domain.user.User;
import com.csu.asms.validator.user.UserValidator;



@Controller
@SessionAttributes("user")
@RequestMapping(value = "/registeruser")
@Configuration
@ComponentScan("com.csu.asms")
public class RegisterUserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	UserValidator userValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}
	
	private static Logger log = LoggerFactory.getLogger(RegisterUserController.class);
	/**
	 * This method is used load user registration page 
	 * @param model to add user details 
	 * @return a string which redirects user to user registration page 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String useregistration(Model model) {
		log.info("in register user controller");
		User user = new User();
		model.addAttribute("registeruser", user);
		return "userregistration";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@ModelAttribute("registeruser") @Validated User user,
			BindingResult result, HttpSession session, final RedirectAttributes redirectAttributes/*SessionStatus status*/) {
		
		log.info("in student registration process method");	

		boolean admin=false;
		ModelAndView mv = new ModelAndView();
		userValidator.validate(user, result);	
		if(!result.hasErrors()){
			
			//check if the csuid is unique in database
			User uservalue1=userService.validateCsuid(user.getCsuid());
			System.out.println("csuid validated for user "+uservalue1);
			
			//check if the email is unique in database
			User uservalue2=userService.validateEmail(user.getEmail());
			System.out.println("email validated for user "+uservalue2);
				
			//store the user in DB
			if(null ==uservalue1 && null ==uservalue2){
			userService.store(user,admin);
			System.out.println("User Account Created!!!");
			}else{
				System.out.println("User Already Exists!!!");
				redirectAttributes.addFlashAttribute("msg", "User Already Exists!!!");
				mv.setViewName("userregistration");
				return mv;	
			
			}
			mv.addObject("user", user);
			//session.setAttribute("user", user);
			mv.setViewName("userdashboard");
			
		}else{
			result.rejectValue("email","email.exist");
			mv.setViewName("userregistration");
			return mv;
		}
			System.out.println("mv view name---"+mv.getViewName());
			return mv;

	}	

}
