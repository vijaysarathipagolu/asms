/**
 * 
 */
package com.csu.asms.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.csu.asms.domain.PostJsonDto;
import com.csu.asms.domain.service.UserService;
import com.csu.asms.domain.user.User;
import com.csu.asms.domain.user.UserPost;
import com.csu.asms.validator.user.UserValidator;



/**
 * @author vijay
 *
 */
@Controller
@SessionAttributes({ "user" })
public class UsersListController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	UserValidator userValidator;
	
	@Resource(name="asmsProperties")
	private Properties asmsProperties;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}
	
	private static Logger log = LoggerFactory.getLogger(UsersListController.class);
	
	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public String login(Model model) {
		log.info("in the default method of User list controller to post page");
		
		return "posts";
	}
	
	
	@RequestMapping(value="/createpost", method = RequestMethod.GET)
	public String createpost(HttpServletRequest request,Model model) throws Exception{
		log.info("in register user controller create post method");
		HttpSession session = request.getSession();
		if(session.getAttribute("user")!= null){
		User user = (User)session.getAttribute("user");
		model.addAttribute("user", user);
		return "createpost";
		}else{
			return "login";
		}
	}
	
	@RequestMapping(value = "/storepost", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView storePost(@ModelAttribute("user") User user,
			 @RequestParam String postType,@RequestParam MultipartFile postimg,
			 	Model model,BindingResult results) throws Exception{
		
		log.info("in store post of alumni method");	
		ModelAndView mv = new ModelAndView();
		userValidator.emailValidation(user, results);
		try{
			String imageDir = null;
			String file = null;
			String fileSeperator = System.getProperty("file.separator");
			String imageName = null;
			
		if(!results.hasErrors())
		{
			if(user.getCsuid()!=null){
				imageDir = asmsProperties.getProperty("POST_IMAGE_PATH");
				log.debug("POST_IMAGE_PATH"+imageDir);
				if(!postimg.isEmpty()){
					byte[] bytes = postimg.getBytes();
				
				
				file = imageDir+fileSeperator+user.getCsuid()+fileSeperator;
				
				new File(file).mkdirs();
				
				Calendar calendar = Calendar.getInstance();
				imageName = calendar.getTimeInMillis()+"_"+postimg.getOriginalFilename();
				
				file = file+imageName;
				log.debug("post image saving at "+file+" for registered alumni");
				
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(file));
                stream.write(bytes);
                stream.close();
				}
			}
			if(user!=null){
			userService.storePost(user, postType, imageName);
			mv.addObject("msg", "User Post successfully created !!!");
			mv.setViewName("posts");
			return mv;
			}
		}else{
			mv.addObject("feedbackinvalid", "invalid");
			mv.setViewName("createpost");
			}
		}catch(Exception e){
			log.error("error in store post method in controller" + e);
			
		}
		
		return null;
		
	}
	
	@RequestMapping(value="/deletepost/{postId}", method=RequestMethod.GET, headers="Accept=*/*")
	public ModelAndView  deletePost(@PathVariable Integer postId) throws Exception{
		ModelAndView mv = new ModelAndView();
		try{
		log.info("in delete user post");
		userService.removePost(postId);
		mv.addObject("msg","User Post Deleted Successfully !!");
		mv.setViewName("viewposts");
		}catch(Exception e){
			log.error("error in delete post method of controller");
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/getposts", method = RequestMethod.GET)
	public ModelAndView userPost(Map<String, Object> map,
			HttpServletRequest request) throws Exception

		{
		log.info("in feedbackUser ");
		User user = new User();
		int recordsperpage=10;
		HttpSession session = request.getSession();
		if(session.getAttribute("user")!= null){
		user = (User)session.getAttribute("user");
		}
		ModelAndView mv = new ModelAndView();
		Long csuid = user.getCsuid();
		log.debug("csuid=" + csuid);
		mv.setViewName("viewposts");
		PostJsonDto[] pjdo = getSingleUserPost(csuid, recordsperpage);
		System.out.println("posts array length"+pjdo.length);
		map.put("postList", pjdo);
		return mv;
		}

	
	public @ResponseBody
	PostJsonDto[] getSingleUserPost(Long csuid, int recordsperpage) {
		log.debug("getUserJsonPosts " + csuid);
		List<PostJsonDto> list = new ArrayList<PostJsonDto>();
		list = userService.userPosts(1, csuid, recordsperpage);
		return list.toArray(new PostJsonDto[list.size()]);
		
	}
	
}
