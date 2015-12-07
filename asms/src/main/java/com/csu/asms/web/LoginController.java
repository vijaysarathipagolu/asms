package com.csu.asms.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.csu.asms.domain.EventsJsonDto;
import com.csu.asms.domain.PostJsonDto;
import com.csu.asms.domain.professor.Professor;
import com.csu.asms.domain.user.User;
import com.csu.asms.domain.user.UserPost;
import com.csu.asms.service.UserService;

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
		//User user = new User();
		//model.addAttribute(user);
		return "index";
	}
	
	@RequestMapping(value = "/signIn", method = RequestMethod.GET)
	public String signIn(Locale locale, Model model) {
		log.info("in the signIn method of login controller");
		User user = new User();
		model.addAttribute(user);
		return "login";
	}

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request,
			@RequestParam Long csuid, @RequestParam String password) {
		ModelAndView mv = null;
		String info=null;
		HttpSession session = request.getSession();
		User user = userService.validateUser(csuid, password);
		log.info("in login method in controller");
		if(null == user){
			mv = new ModelAndView();
			log.info(" user validation failed");
			info = "notvalid";
			request.setAttribute("info", info);
			
			log.debug(info);
			
			mv.setViewName("login");
			mv.addObject("errormessage", "Entered Wrong CSUid or Password");
		}else{
			mv = new ModelAndView();
			session.setAttribute("user", user);
			if(user.isAdmin()){
				mv.setViewName("admindashboard");
				mv.addObject("user", user);
			}else{
				userService.updateUserLogins(user.getCsuid());
				List<Professor> listProf =userService.listProf();
				Professor list = listProf.get(listProf.size() - 1);
				List<EventsJsonDto> edto = userService.listEvents(1, 10);
				EventsJsonDto alleventList= edto.get(edto.size() - 1);
				List<PostJsonDto>pdto = userService.listUsersPosts(1, 10);
				PostJsonDto allpostList = pdto.get(pdto.size() - 1);
				mv.setViewName("userdashboard");
				
				//mv.setViewName("forward:/userdashboard");
				mv.addObject("user", user);
				mv.addObject("listProf", list);
				System.out.println(list);
				
				mv.addObject("alleventList", alleventList);
				mv.addObject("allpostList", allpostList);
			}
		}
		return mv;
	
	}
	
	
	
	
	@RequestMapping(value="/userdashboard", method = RequestMethod.GET)
	public ModelAndView userdashboard(HttpServletRequest request,Model model) {
		log.info("in register user controller user dashboard method");
		HttpSession session = request.getSession();
		ModelAndView mv = new ModelAndView();
	
		if(session.getAttribute("user")!= null){
			System.out.println("in userdashboard method to get user details"+session.getAttribute("user").toString());
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
		}else{
			mv.setViewName("login");
		}
		return mv;
	}
	
	@RequestMapping(value="/admindashboard", method = RequestMethod.GET)
	public String admindashboard(HttpServletRequest request,Model model) {
		log.info("in login controller admin dashboard method");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		if(user!= null && user.isAdmin()){
			System.out.println("in admindashboard method to get admin details"+session.getAttribute("user").toString());
		return "admindashboard";
		}else{
			return "login";
		}
	}
	
	@RequestMapping(value="/searchuser", method = RequestMethod.POST)
	public ModelAndView searchUser(@RequestParam Long csuid,HttpServletRequest request,Model model) {
		log.info("in login controller search user  method");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		ModelAndView mv = new ModelAndView();
		if(user != null){
			User searchUser = userService.findUser(csuid);
			if(searchUser !=null){
			mv.setViewName("searchuser");
			mv.addObject("searchUser", searchUser);
			}else{
				mv.setViewName("userdashboard");
				mv.addObject("errormessage", "No User with the entered CSU ID");
				System.out.println("no user with the entered csuid");
			}
		}else{
			mv.setViewName("login");
		}
		return mv;
	}
	
	@RequestMapping(value="/professors", method = RequestMethod.GET)
	public ModelAndView getProfessors(HttpServletRequest request,Model model) {
		log.info("in login controller get professors method");
		HttpSession session = request.getSession();
		ModelAndView mv = new ModelAndView();
		List<Professor> listProf = new ArrayList<Professor>();
		try{
			listProf=userService.listProf();
		}catch(Exception e){
			log.error("in get professors method of login controller");
		}
	
		if(session.getAttribute("user")!= null){
			System.out.println("in userdashboard method to get user details"+session.getAttribute("user").toString());
			mv.addObject("listProf", listProf);
			mv.setViewName("professors");
		}else{
			mv.setViewName("login");
		}
		return mv;
	}
	
	/**
	 * This method is used to load reset password page for user
	 * 
	 * @return a string which redirects user to reset password page
	 */
	@RequestMapping("/forgotpassword")
	public String forgotPwd(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "forgotpassword";
	}
	
	/**
	 * This method is used to load reset password page for user
	 * 
	 * @return a string which redirects user to reset password page
	 */
	@RequestMapping("/changepassword")
	public String changePwd(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "forgotpassword";
	}


	/**
	 * This method is used to edit the user details in my profile page
	 * 
	 * @return a string which redirects user to my profile page
	 */
	@RequestMapping(value="/userprofile",  method = RequestMethod.GET)
	public String editProfile(Model model , HttpServletRequest request) {
		
		User edituser = (User) request.getSession().getAttribute("user");
		model.addAttribute("edituser", edituser);
		return "userprofile";
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
