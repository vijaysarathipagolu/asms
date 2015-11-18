/**
 * 
 */
package com.csu.asms.domain.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.csu.asms.domain.PostJsonDto;
import com.csu.asms.domain.user.ResetPassword;
import com.csu.asms.domain.user.User;
import com.csu.asms.domain.user.UserLogins;
import com.csu.asms.domain.user.UserPost;
import com.csu.asms.domain.user.UserStoryJsonDto;
import com.csu.asms.repository.user.UserDao;



/**
 * @author vijay
 *
 */
@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS)
public  class UserServiceImpl implements UserService{

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
			//userDao.storeUserLogins(userlogins);

		} catch (DataAccessException e) {
			log.error("In store ", e);
			throw e;

		}

		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(User user) {
		// TODO Auto-generated method stub
		
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
		return null;
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
		User user=null;
		log.info("in validate user login method");
		try{
			user = userDao.validateUser(csuid, password);
		}catch(DataAccessException e){
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
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ResetPassword validateGuid(String guid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeGuid(String guid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUserLogins(Long csuid) {
		// TODO Auto-generated method stub
		try{
			UserLogins ulogin = userDao.getUserLogins(csuid);
			
			if(ulogin !=null){
				Long count = ulogin.getLoginCount();
				log.debug("in updateUserLogins,count" + count);
				count++;
				ulogin.setLoginCount(count);
				ulogin.setLastLogin(new Date());
				userDao.storeUserLogins(ulogin);
			}else{
				User user=userDao.findUser(csuid);
				ulogin=new UserLogins();
				ulogin.setUser(user);				
				ulogin.setLoginCount(1L);
				ulogin.setLastLogin(new Date());
				user.setUserLogins(ulogin);
				userDao.store(user);
				
			}
			
		}catch(DataAccessException e){
			log.error("in error of update loginS", e);
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void resetPassword(User user, String guid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<PostJsonDto> userPosts(int pageNo, Long csuid, Integer recordsPerPage) {
		// TODO Auto-generated method stub
		List<PostJsonDto> uposts = new ArrayList<PostJsonDto>();
		try{
			uposts = userDao.userPosts(pageNo, csuid, recordsPerPage);
			log.info("the posts lenght in service"+uposts.size());
		}catch(DataAccessException e){
			log.error("in error of userposts in service" +e);
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

		try{
		userpost.setCsuid(user.getCsuid());
		userpost.setEmailId(user.getEmail());
		userpost.setPost(user.getPost());
		userpost.setPostType(postType);
		userpost.setPostDate(new java.util.Date());
		userpost.setImageName(imageName);
		userDao.savePost(userpost);
		
	}catch(DataAccessException e){
		log.error("error in store alumni post method" +e);	
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
		User user=null;
		log.debug("in validate CSUID",csuid);
		try{
			user= userDao.validateCsuid(csuid);
			
		}catch (DataAccessException e) {
			log.error("in validateEmail ", e);
			throw e;
			
		}
		return user;
	}

	@Override
	/**
	 * @param postId
	 * this method is used to delete post of user
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void removePost(Integer postId) {
		// TODO Auto-generated method stub
		log.info("in remove post in service class");
		try{
			userDao.removePost(postId);
		}catch(DataAccessException e){
			log.error("in remove post method in service"+ e);
		}
		
		
	}

}
