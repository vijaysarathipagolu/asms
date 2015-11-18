package com.csu.asms.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.csu.asms.domain.service.UserService;
import com.csu.asms.domain.user.User;
import com.csu.asms.domain.user.UserPost;

/**
 * Handles requests for the application login home page.
 */
@Controller
@SessionAttributes("user")
@RequestMapping(value = "/")
public class LoginController {
	
	@Autowired
	UserService userService;
	
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {
		log.info("in the default method of login controller");
		User user = new User();
		model.addAttribute(user);
		return "login";
	}
	

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request,
			@RequestParam Long csuid, @RequestParam String password) {
		ModelAndView mv = null;
		HttpSession session = request.getSession();
		User user = userService.validateUser(csuid, password);
		log.info("in login method in controller");
		if(null == user){
			mv = new ModelAndView();
			log.info(" user validation failed");
			mv.setViewName("login");
			mv.addObject("errormessage", "Entered Wrong CSUid or Password");
		}else{
			mv = new ModelAndView();
			if(user.isAdmin()){
				mv.setViewName("admindashboard");
				mv.addObject("user", user);
			}else{
				userService.updateUserLogins(user.getCsuid());
				
				mv.setViewName("userdashboard");
				session.setAttribute("user", user);
				//mv.setViewName("forward:/userdashboard");
				mv.addObject("user", user);
			}
		}
		return mv;
	
	}
	
	
	
	
	@RequestMapping(value="/userdashboard", method = RequestMethod.GET)
	public String userdashboard(HttpServletRequest request,Model model) {
		log.info("in register user controller user dashboard method");
		HttpSession session = request.getSession();
	
		if(session.getAttribute("user")!= null){
			System.out.println("in userdashboard method to get user details"+session.getAttribute("user").toString());
		return "userdashboard";
		}else{
			return "login";
		}
	}

	/**
	 * This method is used logout from the application
	 * 
	 * @param request
	 *            
	 * @return
	 */
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		
		request.getSession().removeAttribute("user");
		request.getSession().invalidate();
		return "redirect:/";
	}	
	
}
