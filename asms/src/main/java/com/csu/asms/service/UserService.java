
package com.csu.asms.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.csu.asms.domain.EventsJsonDto;
import com.csu.asms.domain.PostJsonDto;
import com.csu.asms.domain.professor.Professor;
import com.csu.asms.domain.user.ResetPassword;
import com.csu.asms.domain.user.User;
import com.csu.asms.domain.user.UserLogins;
import com.csu.asms.domain.user.UserPost;
import com.csu.asms.domain.user.UserStoryJsonDto;

/**
 * @author vijay
 * 
 *         this interface is a service which is used to perform all the user
 *         operations
 *
 */
@Service
public interface UserService {

	public void store(User user, Boolean admin);

	public void update(User user);

	public void removeUser(Long csuid);

	public List<UserStoryJsonDto> listUsers(String columnName, String order, int pageNo, int recordsPerPage);

	public User findUser(Long csuid);

	public User validateUser(Long csuid, String password);

	public User validateEmail(String email);

	public User validateCsuid(Long csuid);

	public void storeUserLogins(UserLogins userlogins);

	public void resetPassword(Long csuid, String guivalue);

	public ResetPassword validateGuid(String guid);

	public void removeGuid(String guid);

	public void updateUserLogins(Long csuid);

	public void resetPassword(User user, String guid);

	public int getUserTotalRecords();

	public List<PostJsonDto> userPosts(int pageNo, Long csuid, Integer recordsPerPage);

	public void storePost(User user, String postType, String imageName);

	public void storeEvent(User user, String eventDate);

	public void removePost(Integer postId);

	public void removeEvent(Integer eventId);

	public List<Professor> listProf();

	public List<PostJsonDto> listUsersPosts(int pageNo, int recordsPerPage);

	public List<EventsJsonDto> listEvents(int pageNo, int recordsPerPage);
}
