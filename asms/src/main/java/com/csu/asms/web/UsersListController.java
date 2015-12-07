
package com.csu.asms.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

import com.csu.asms.domain.EventsJsonDto;
import com.csu.asms.domain.JqGrid;
import com.csu.asms.domain.PostJsonDto;
import com.csu.asms.domain.user.User;
import com.csu.asms.domain.user.UserStoryJsonDto;
import com.csu.asms.service.UserService;
import com.csu.asms.validator.user.UserValidator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author vijay
 * 
 *         this controller handles requests for all user operations like events
 *         , posts etc.
 *
 */
@Controller
@SessionAttributes({ "user" })
public class UsersListController {

	@Autowired
	private UserService userService;

	@Autowired
	UserValidator userValidator;

	@Resource(name = "asmsProperties")
	private Properties asmsProperties;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}

	private static Logger log = LoggerFactory.getLogger(UsersListController.class);

	/**
	 * this method handles requests to display the posts view page
	 */
	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public String posts(HttpServletRequest request, Model model) {
		log.info("in the default method of User list controller to post page");
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
		}
		return "posts";
	}

	/**
	 * this method handles requests to display the events view page
	 */
	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public String events(HttpServletRequest request, Model model) {
		log.info("in the default method of User list controller to post page");
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
		}
		return "events";
	}

	/**
	 * this method handles requests to display the create post view page
	 */
	@RequestMapping(value = "/createpost", method = RequestMethod.GET)
	public String createpost(HttpServletRequest request, Model model) throws Exception {
		log.info("in register user controller create post method");
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			return "createpost";
		} else {
			return "login";
		}
	}

	/**
	 * this method handles requests to display the create event view page
	 */
	@RequestMapping(value = "/createevent", method = RequestMethod.GET)
	public String createevent(HttpServletRequest request, Model model) throws Exception {
		log.info("in register user controller create event method");
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
			return "createevent";
		} else {
			return "login";
		}
	}

	/**
	 * this method handles request to the create post request by the user
	 */
	@RequestMapping(value = "/storepost", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView storePost(@ModelAttribute("user") User user, @RequestParam String postType,
			@RequestParam MultipartFile postimg, Model model, BindingResult results) throws Exception {

		log.info("in store post of alumni method");
		ModelAndView mv = new ModelAndView();
		userValidator.emailValidation(user, results);
		try {
			String imageDir = null;
			String file = null;
			String fileSeperator = System.getProperty("file.separator");
			String imageName = null;

			if (!results.hasErrors()) {
				if (user.getCsuid() != null) {
					imageDir = asmsProperties.getProperty("POST_IMAGE_PATH");
					log.debug("POST_IMAGE_PATH" + imageDir);
					if (!postimg.isEmpty()) {
						byte[] bytes = postimg.getBytes();
						file = imageDir + fileSeperator + user.getCsuid() + fileSeperator;
						new File(file).mkdirs();
						Calendar calendar = Calendar.getInstance();
						imageName = calendar.getTimeInMillis() + "_" + postimg.getOriginalFilename();

						file = file + imageName;
						log.debug("post image saving at " + file + " for registered alumni");

						BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
						stream.write(bytes);
						stream.close();
					}
				}
				if (user != null) {
					userService.storePost(user, postType, imageName);
					mv.addObject("msg", "User Post successfully created !!!");
					mv.setViewName("posts");
					return mv;
				}
			} else {
				mv.addObject("feedbackinvalid", "invalid");
				mv.setViewName("createpost");
			}
		} catch (Exception e) {
			log.error("error in store post method in controller" + e);

		}

		return null;
	}

	/**
	 * this method handles request to the create event request by the user
	 */
	@RequestMapping(value = "/storeevent", method = RequestMethod.POST)
	public ModelAndView storeEvent(@ModelAttribute("user") User user, Model model, BindingResult results)
			throws Exception {

		log.info("in store event of alumni method");
		ModelAndView mv = new ModelAndView();
		userValidator.emailValidation(user, results);
		try {
			String eventDate = user.getDate();

			if (!results.hasErrors()) {
				if (user != null) {
					userService.storeEvent(user, eventDate);
					mv.addObject("msg", "User Event successfully created !!!");
					mv.setViewName("events");
					return mv;
				}
			} else {
				mv.addObject("feedbackinvalid", "invalid");
				mv.setViewName("createevent");
			}
		} catch (Exception e) {
			log.error("error in store event method in controller" + e);

		}

		return null;

	}

	/**
	 * this method handles the delete post request by the user
	 */
	@RequestMapping(value = "/deletepost/{postId}", method = RequestMethod.GET, headers = "Accept=*/*")
	public ModelAndView deletePost(@PathVariable Integer postId) throws Exception {
		ModelAndView mv = new ModelAndView();
		try {
			log.info("in delete user post");
			userService.removePost(postId);
			mv.addObject("msg", "User Post Deleted Successfully !!");
			mv.setViewName("viewposts");
		} catch (Exception e) {
			log.error("error in delete post method of userslist controller");
		}

		return mv;
	}

	/**
	 * this method handles the delete event request by the user
	 */
	@RequestMapping(value = "/deleteevent/{eventId}", method = RequestMethod.GET, headers = "Accept=*/*")
	public ModelAndView deleteEvent(@PathVariable Integer eventId) throws Exception {
		ModelAndView mv = new ModelAndView();
		try {
			log.info("in delete user post");
			userService.removeEvent(eventId);
			mv.addObject("msg", "User Event Deleted Successfully !!");
			mv.setViewName("viewallevents");
		} catch (Exception e) {
			log.error("error in delete event method of userslist controller");
		}

		return mv;
	}

	/**
	 * this method handles the view post request by the user
	 */
	@RequestMapping(value = "/getposts", method = RequestMethod.GET)
	public ModelAndView userPost(Map<String, Object> map, HttpServletRequest request) throws Exception

	{
		log.info("in userPost ");
		User user = new User();
		int recordsperpage = 10;
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			user = (User) session.getAttribute("user");
		}
		ModelAndView mv = new ModelAndView();
		Long csuid = user.getCsuid();
		log.debug("csuid=" + csuid);
		mv.setViewName("viewposts");
		PostJsonDto[] pjdo = getSingleUserPost(csuid, recordsperpage);
		log.info("posts array length" + pjdo.length);
		map.put("postList", pjdo);
		return mv;
	}

	/**
	 * this method lists the user posts of a specific user from the service
	 * 
	 * @param csuid
	 * @param recordsperpage
	 * @return
	 */
	public @ResponseBody PostJsonDto[] getSingleUserPost(Long csuid, int recordsperpage) {
		log.debug("getUserJsonPosts " + csuid);
		List<PostJsonDto> list = new ArrayList<PostJsonDto>();
		list = userService.userPosts(1, csuid, recordsperpage);
		return list.toArray(new PostJsonDto[list.size()]);

	}

	/**
	 * this method handles the view All post request by the user
	 */
	@RequestMapping(value = "/getAllposts", method = RequestMethod.GET)
	public ModelAndView allUserPosts(Map<String, Object> map, HttpServletRequest request) throws Exception

	{
		log.info("in all user posts ");
		User user = new User();
		int recordsperpage = 10;
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			user = (User) session.getAttribute("user");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("viewallposts");
		PostJsonDto[] pdto = getAllUserPosts(1, recordsperpage);
		log.info("all posts array length" + pdto.length);
		map.put("allpostList", pdto);
		return mv;
	}

	/**
	 * this method lists all user posts from the service
	 * 
	 * @param pageno
	 * @param recordsperpage
	 * @return
	 */
	public @ResponseBody PostJsonDto[] getAllUserPosts(int pageno, int recordsperpage) {

		List<PostJsonDto> list = new ArrayList<PostJsonDto>();
		list = userService.listUsersPosts(pageno, recordsperpage);
		Collections.reverse(list);
		return list.toArray(new PostJsonDto[list.size()]);

	}

	/**
	 * this method handles request to list all events
	 * 
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllevents", method = RequestMethod.GET)
	public ModelAndView allEvents(Map<String, Object> map, HttpServletRequest request) throws Exception

	{
		log.info("in all events ");
		User user = new User();
		int recordsperpage = 10;
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			user = (User) session.getAttribute("user");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("viewallevents");
		EventsJsonDto[] edto = getAllEvents(1, recordsperpage);
		System.out.println("all events array length" + edto.length);
		map.put("alleventList", edto);
		return mv;
	}

	/**
	 * this method handles request to list all events from the service
	 * 
	 * @param pageno
	 * @param recordsperpage
	 * @return
	 */
	public @ResponseBody EventsJsonDto[] getAllEvents(int pageno, int recordsperpage) {

		List<EventsJsonDto> list = new ArrayList<EventsJsonDto>();
		list = userService.listEvents(pageno, recordsperpage);
		Collections.reverse(list);
		return list.toArray(new EventsJsonDto[list.size()]);

	}

	/**
	 * @return this is used to navigate to grid page
	 */
	@RequestMapping("/getUsers")
	public String userGrid() {

		log.info("in userGrid to navigate userlist page");
		return "/userGrid";
	}

	/**
	 * 
	 * @param total
	 * @param columnName
	 * @param order
	 * @param pageNo
	 * @param recordsPerPage
	 * @param response
	 *            this method displays the all users details as grid
	 */
	@RequestMapping(value = "/listUsers", method = RequestMethod.GET)
	public void listUsers(@RequestParam(value = "total", required = false) String total,
			@RequestParam(value = "sidx", required = false) String columnName,
			@RequestParam(value = "sord", required = false) String order,
			@RequestParam(value = "page", required = false) Integer pageNo,
			@RequestParam(value = "rows", required = false) Integer recordsPerPage, HttpServletResponse response,
			HttpSession session) {
		try {

			/*
			 * if(!"".equalsIgnoreCase(columnName)){
			 * session.setAttribute("columname",columnName);
			 * session.setAttribute("order",order);
			 * 
			 * }else{
			 * 
			 * columnName=(String) session.getAttribute("columname");
			 * order=(String) session.getAttribute("order"); }
			 */

			List<UserStoryJsonDto> usjdto = userService.listUsers(columnName, order, pageNo, recordsPerPage);

			JqGrid<UserStoryJsonDto> jqGrid = new JqGrid<UserStoryJsonDto>(usjdto, null);

			int totalrecords = userService.getUserTotalRecords();
			int totalpresentpages = totalrecords / recordsPerPage + ((totalrecords % recordsPerPage == 0) ? 0 : 1);

			jqGrid.setTotal(totalpresentpages);// total pages
			jqGrid.setRecords(totalrecords); // total records
			jqGrid.setPage(pageNo); // page number
			log.info("total list users  --- " + totalrecords);
			response.setContentType("application/json");
			response.getWriter().write((new Gson().toJson(jqGrid, new TypeToken<JqGrid<UserStoryJsonDto>>() {
			}.getType())));
		} catch (Exception e) {
			log.error("error in listUsers", e);

		}
	}

}
