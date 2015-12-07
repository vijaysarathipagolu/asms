package com.csu.asms.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.csu.asms.domain.EventsJsonDto;
import com.csu.asms.domain.PostJsonDto;
import com.csu.asms.domain.professor.Professor;
import com.csu.asms.domain.user.User;
import com.csu.asms.service.UserService;
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
	public String useregistration(Model model,HttpServletRequest request) {
		log.info("in register user controller");
		HttpSession session = request.getSession();
		User old = (User)session.getAttribute("user");
		User user = new User();
		if(old !=null && old.isAdmin() == true){
			model.addAttribute("registeruser", user);
			model.addAttribute("admin", old);
			System.out.println("added admin to model in register controller");
		}else{
		model.addAttribute("registeruser", user);
		}
		return "userregistration";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@ModelAttribute("registeruser") @Validated User user,
			BindingResult result, HttpSession session,Model model, final RedirectAttributes redirectAttributes/*SessionStatus status*/) {
		
		log.info("in student registration process method");	
		User adminUser = (User)session.getAttribute("user");
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
			if(adminUser !=null && adminUser.isAdmin() == true){
				user.setTypeOfUser("Event_Manager");
			}
			else{
				user.setTypeOfUser("Alumni");
			}
			//store the user in DB
			if(null ==uservalue1 && null ==uservalue2)
			{
				userService.store(user,admin);
				System.out.println("User Account Created!!!");
				if(adminUser !=null && adminUser.isAdmin() == true){
					model.addAttribute("msg", "Event Manager Created!!!");
					mv.setViewName("events");	
				}else{
					mv.addObject("user", user);
					//session.setAttribute("user", user);
					List<Professor> listProf =userService.listProf();
					Professor list = listProf.get(listProf.size() - 1);
					List<EventsJsonDto> edto = userService.listEvents(1, 10);
					EventsJsonDto alleventList= edto.get(edto.size() - 1);
					List<PostJsonDto>pdto = userService.listUsersPosts(1, 10);
					PostJsonDto allpostList = pdto.get(pdto.size() - 1);
					mv.addObject("listProf", list);
					mv.addObject("alleventList", alleventList);
					mv.addObject("allpostList", allpostList);
					
					mv.setViewName("userdashboard");
				}
			
			}else{
				System.out.println("User Already Exists!!!");
				model.addAttribute("msg", "User Already Exists!!!");
				if(adminUser !=null && adminUser.isAdmin() == true){
					mv.setViewName("events");	
				}else{
					result.rejectValue("email","email.exist");
					mv.setViewName("userregistration");
				}
			}
			
			
		}else{
			//result.rejectValue("email","email.exist");
			mv.setViewName("userregistration");
			return mv;
			}
			
		System.out.println("mv view name---"+mv.getViewName());
		return mv;

	}	
	
	/**
	 * This method is used to submit the edit user details by user
	 * @param user user details
	 * @param result Binding Result to bind errors object with UI	 * 
	 * @return an Object which has redirect url's
	 */
	@RequestMapping(value="/edit" , method = { RequestMethod.POST })
	public String updateUser(HttpServletRequest request,@ModelAttribute("edituser") User user,
			BindingResult result, Model model) {
		user.setEdit(true);	
		userValidator.validate(user, result);
		
		String info=null;
			User uservalue=userService.validateEmail(user.getEmail());
			
			if(uservalue!=null && (uservalue.getCsuid().longValue() != user.getCsuid().longValue())){				
				result.rejectValue("email","email.exist");
			}			
			
			if (user.getPassword() == null || "".equalsIgnoreCase(user.getPassword())) {
				user.setPassword(uservalue.getPassword());
			}
			if(user.getRegisteredDate() == null || "".equalsIgnoreCase(user.getPassword())){
				
				user.setRegisteredDate(uservalue.getRegisteredDate());
			}
			
			if (!result.hasErrors()) {	
			log.debug("in processSubmit to update the user details");
			userService.update(user);
			info = "update";
			//request.setAttribute("info", info);
			//mv.addObject("updated","update");
			model.addAttribute("info", info);
			return "/userdashboard";
			//mv.setViewName("userprofile");
			} else {
				info = "notvalid";
				//request.setAttribute("info", info);
				//mv.addObject("updated","update");
				model.addAttribute("info", info);
				//mv.setViewName("login");
				return "/login";
		}
		
	}

}
