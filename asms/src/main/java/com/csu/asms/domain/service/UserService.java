/**
 * 
 */
package com.csu.asms.domain.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.csu.asms.domain.PostJsonDto;
import com.csu.asms.domain.user.ResetPassword;
import com.csu.asms.domain.user.User;
import com.csu.asms.domain.user.UserLogins;
import com.csu.asms.domain.user.UserStoryJsonDto;




/**
 * @author vijay
 *
 */
@Service
public interface UserService {

	public void store(User user, Boolean admin);
	public void update(User user);
	public void removeUser(Long csuid) ;
	public List<UserStoryJsonDto> listUsers(String columnName, String order, int pageNo, int recordsPerPage) ;
	public User findUser(Long csuid) ;
	public User validateUser(Long csuid, String password) ;
	public User validateEmail(String email) ;
	public User validateCsuid(Long csuid);
	public void storeUserLogins(UserLogins userlogins) ;
	public void resetPassword(Long csuid,String guivalue);
	public ResetPassword validateGuid(String guid);
	public void removeGuid(String guid) ;
	public void updateUserLogins(Long csuid);
	public void resetPassword(User user,String guid) ;
	public List<PostJsonDto> userPosts(int pageNo,Long csuid,Integer recordsPerPage);
	public void storePost(User user,String postType ,String imageName);
	public void removePost(Integer postId);
	
}
