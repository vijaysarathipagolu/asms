
package com.csu.asms.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.csu.asms.domain.EventsJsonDto;
import com.csu.asms.domain.PostJsonDto;
import com.csu.asms.domain.professor.Professor;
import com.csu.asms.domain.user.Events;
import com.csu.asms.domain.user.ResetPassword;
import com.csu.asms.domain.user.User;
import com.csu.asms.domain.user.UserLogins;
import com.csu.asms.domain.user.UserPost;
import com.csu.asms.domain.user.UserStoryJsonDto;
import com.csu.asms.repository.user.UserDao;

/**
 * @author vijay
 *
 *         this is a service implementation class for all user operations
 */
@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Transactional(propagation = Propagation.REQUIRED)
	public void store(User user, Boolean admin) {
		// TODO Auto-generated method stub
		try {
			log.info("in store method admin value" + admin);
			UserLogins userlogins = new UserLogins();
			userlogins.setCsuid(user.getCsuid());
			if (admin) {
				userlogins.setLoginCount(0L);
			} else {
				userlogins.setLoginCount(1L);
				userlogins.setLastLogin(new java.util.Date());
			}

			user.setRegisteredDate(new java.util.Date());
			user.setUserLogins(userlogins);
			userlogins.setUser(user);
			userDao.store(user);
			// userDao.storeUserLogins(userlogins);

		} catch (DataAccessException e) {
			log.error("In store ", e);
			throw e;

		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(User user) {
		// TODO Auto-generated method stub
		log.info("In update method");
		try {
			userDao.update(user);
		} catch (DataAccessException e) {
			log.error("in update ", e);
			throw e;
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeUser(Long csuid) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<UserStoryJsonDto> listUsers(String columnName, String order, int pageNo, int recordsPerPage) {
		// TODO Auto-generated method stub
		List<UserStoryJsonDto> list = null;
		try {
			log.debug("in listUsers method,columnName" + columnName + "order" + order);
			List<UserStoryJsonDto> userList = userDao.getUserList(columnName, order, pageNo, recordsPerPage);

			list = new ArrayList<UserStoryJsonDto>();

			if (userList != null && userList.size() > 0) {
				for (UserStoryJsonDto userStory : userList) {
					list.add(userStory);
				}
			}
		} catch (DataAccessException e) {
			log.error("in listUsers ", e);
			throw e;
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User findUser(Long csuid) {
		// TODO Auto-generated method stub
		User user = null;
		log.debug("in findUser method,userId" + csuid);
		try {
			user = userDao.findUser(csuid);

		} catch (DataAccessException e) {
			log.error("in findUser ", e);
			throw e;
		}
		return user;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User validateUser(Long csuid, String password) {
		// TODO Auto-generated method stub
		User user = null;
		log.info("in validate user login method");
		try {
			user = userDao.validateUser(csuid, password);
		} catch (DataAccessException e) {
			log.error("in validate user", e);
		}

		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User validateEmail(String email) {
		// TODO Auto-generated method stub
		User user = null;
		log.debug("in validateEmail,email", email);
		try {
			user = userDao.validateEmail(email);
		} catch (DataAccessException e) {
			log.error("in validateEmail ", e);
			throw e;
		}
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void storeUserLogins(UserLogins userlogins) {
		// TODO Auto-generated method stub
		log.info("in storeUserLogins");
		try {
			userDao.storeUserLogins(userlogins);
		} catch (DataAccessException e) {
			log.error("in storeUserLogins ", e);
			throw e;
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void resetPassword(Long csuid, String guivalue) {
		// TODO Auto-generated method stub
		log.debug("in resetPassword ,userId " + csuid + "guivalue " + guivalue);
		try {
			ResetPassword resetPassword = new ResetPassword();
			resetPassword.setCsuid(csuid);
			resetPassword.setGuiId(guivalue);
			resetPassword.setResetDate(new Date());
			userDao.resetPassword(resetPassword);
		} catch (DataAccessException e) {
			log.error("in resetPassword ", e);
			throw e;
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ResetPassword validateGuid(String guid) {
		// TODO Auto-generated method stub
		ResetPassword resetpwd = null;
		log.info("in validateGuid ");
		try {
			resetpwd = userDao.validateGuid(guid);
		} catch (DataAccessException e) {
			log.error("in validateGuid ", e);
			throw e;
		}
		return resetpwd;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeGuid(String guid) {
		// TODO Auto-generated method stub
		try {
			log.debug("in removeGuid guid" + guid);
			userDao.removeGuid(guid);
		} catch (DataAccessException e) {
			log.error("In removeGuid ", e);
			throw e;
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUserLogins(Long csuid) {
		// TODO Auto-generated method stub
		try {
			UserLogins ulogin = userDao.getUserLogins(csuid);

			if (ulogin != null) {
				Long count = ulogin.getLoginCount();
				log.debug("in updateUserLogins,count" + count);
				count++;
				ulogin.setLoginCount(count);
				ulogin.setLastLogin(new Date());
				userDao.storeUserLogins(ulogin);
			} else {
				User user = userDao.findUser(csuid);
				ulogin = new UserLogins();
				ulogin.setUser(user);
				ulogin.setLoginCount(1L);
				ulogin.setLastLogin(new Date());
				user.setUserLogins(ulogin);
				userDao.store(user);

			}

		} catch (DataAccessException e) {
			log.error("in error of update loginS", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void resetPassword(User user, String guid) {
		// TODO Auto-generated method stub
		try {
			log.info("in reset password service method");
			String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
			user.setPassword(hashed);
			userDao.update(user);
			userDao.removeGuid(guid);
			log.info("In resetPassword to update and delete guid");
		} catch (DataAccessException e) {
			log.error("in resetPassword ", e);
			throw e;
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<PostJsonDto> userPosts(int pageNo, Long csuid, Integer recordsPerPage) {
		// TODO Auto-generated method stub
		List<PostJsonDto> uposts = new ArrayList<PostJsonDto>();
		try {
			uposts = userDao.userPosts(pageNo, csuid, recordsPerPage);
			log.info("the posts lenght in service" + uposts.size());
		} catch (DataAccessException e) {
			log.error("in error of userposts in service" + e);
			throw e;
		}
		return uposts;
	}

	@Override
	public List<PostJsonDto> listUsersPosts(int pageNo, int recordsPerPage) {
		// TODO Auto-generated method stub
		List<PostJsonDto> uposts = new ArrayList<PostJsonDto>();
		try {
			uposts = userDao.listUsersPosts(pageNo, recordsPerPage);
			log.info("the posts lenght in service" + uposts.size());
		} catch (DataAccessException e) {
			log.error("in error of userposts in service" + e);
			throw e;
		}
		return uposts;
	}

	@Override
	public List<EventsJsonDto> listEvents(int pageNo, int recordsPerPage) {
		// TODO Auto-generated method stub
		List<EventsJsonDto> uposts = new ArrayList<EventsJsonDto>();
		try {
			uposts = userDao.listEvents(pageNo, recordsPerPage);
			log.info("the posts lenght in service" + uposts.size());
		} catch (DataAccessException e) {
			log.error("in error of userposts in service" + e);
			throw e;
		}
		return uposts;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void storePost(User user, String postType, String imageName) {
		// TODO Auto-generated method stub
		log.debug("in store alumni post method");
		UserPost userpost = new UserPost();

		try {
			userpost.setCsuid(user.getCsuid());
			userpost.setEmailId(user.getEmail());
			userpost.setPost(user.getPost());
			userpost.setPostType(postType);
			userpost.setPostDate(new java.util.Date());
			userpost.setImageName(imageName);
			userDao.savePost(userpost);

		} catch (DataAccessException e) {
			log.error("error in store alumni post method" + e);
		}
	}

	@Override
	/**
	 * @param email
	 * @return User to check the user based on email
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public User validateCsuid(Long csuid) {
		// TODO Auto-generated method stub
		User user = null;
		log.debug("in validate CSUID", csuid);
		try {
			user = userDao.validateCsuid(csuid);

		} catch (DataAccessException e) {
			log.error("in validateEmail ", e);
			throw e;

		}
		return user;
	}

	@Override
	/**
	 * @param postId
	 *            this method is used to delete post of user
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void removePost(Integer postId) {
		// TODO Auto-generated method stub
		log.info("in remove post in service class");
		try {
			userDao.removePost(postId);
		} catch (DataAccessException e) {
			log.error("in remove post method in service" + e);
		}

	}

	@Override
	public List<Professor> listProf() {
		// TODO Auto-generated method stub
		log.info("in list prof method of service");
		List<Professor> list = new ArrayList<Professor>();
		try {
			list = userDao.listProf();
			log.info("the list of professors in service method" + list.size());
		} catch (DataAccessException e) {
			log.error("error in list prof method of service class" + e);
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int getUserTotalRecords() {
		// TODO Auto-generated method stub
		int totalrecords = 0;
		try {
			totalrecords = userDao.getUserTotalRecords();
			log.debug("in getUserTotalRecords,total records" + totalrecords);
		} catch (DataAccessException e) {
			log.error("in getUserTotalRecords ", e);
			throw e;
		}
		return totalrecords;

	}

	@Override
	public void storeEvent(User user, String eventDate) {
		// TODO Auto-generated method stub
		log.debug("in store event method");
		Events event = new Events();

		try {
			event.setCsuid(user.getCsuid());
			event.setEventDesc(user.getEventDesc());
			DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
			// eventDate = eventDate.substring(0,eventDate.lastIndexOf(" "));
			System.out.println("the event time is --" + eventDate);
			Date date = format.parse(eventDate);
			event.setEventDate(date);
			event.setEventType(user.getEventType());
			event.setEmail(user.getEmail());

			userDao.saveEvent(event);

		} catch (DataAccessException e) {
			log.error("error in store event method" + e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void removeEvent(Integer eventId) {
		// TODO Auto-generated method stub
		log.info("in remove event in service class");
		try {
			userDao.removeEvent(eventId);
		} catch (DataAccessException e) {
			log.error("in remove event method in service" + e);
		}
	}

}
