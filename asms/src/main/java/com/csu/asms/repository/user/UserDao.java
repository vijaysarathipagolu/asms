/**
 * 
 */
package com.csu.asms.repository.user;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.csu.asms.domain.EventsJsonDto;
import com.csu.asms.domain.PostJsonDto;
import com.csu.asms.domain.professor.Professor;
import com.csu.asms.domain.user.Events;
import com.csu.asms.domain.user.ResetPassword;
import com.csu.asms.domain.user.User;
import com.csu.asms.domain.user.UserEmail;
import com.csu.asms.domain.user.UserLogins;
import com.csu.asms.domain.user.UserPost;
import com.csu.asms.domain.user.UserStoryJsonDto;




/**
 * @author vijay
 *
 */
public interface UserDao {

	public void store(User user) throws DataAccessException;
	public void update(User user) throws DataAccessException;
	public void removeUser(Long csuid) throws DataAccessException;
	public User findUser(Long csuid) throws DataAccessException;
	public User validateUser(Long csuid, String password) throws DataAccessException;
	public User validateCsuid(Long csuid) throws DataAccessException;
	public User validateEmail(String email) throws DataAccessException;
	public void storeUserLogins(UserLogins userlogins) throws DataAccessException;
	public ResetPassword validateGuid(String guid)throws DataAccessException;
	public void removeGuid(String guid) throws DataAccessException;
	public UserLogins getUserLogins(Long csuid)throws DataAccessException;
	public void resetPassword(ResetPassword resetpassword) throws DataAccessException;
	public int getUserTotalRecords() throws DataAccessException;
	public List<UserStoryJsonDto> getUserList(String columnName, String order, int pageNo, int recordsPerPage) throws DataAccessException;
	public void savePost(UserPost post) throws DataAccessException;
	public void saveEvent(Events event) throws DataAccessException;
	public List<PostJsonDto> userPosts(int pageNo,Long csuid,Integer recordsPerPage)throws DataAccessException;
	public int getPostTotalRecords() throws DataAccessException;
	public void removePost(Integer postId) throws DataAccessException;
	public void removeEvent(Integer eventId) throws DataAccessException;
	public List<Professor> listProf()throws DataAccessException;
	public void saveEmail(UserEmail usermail)throws DataAccessException;
	public List<UserEmail> getEmailRequests() throws DataAccessException;
	public List<PostJsonDto> listUsersPosts(int pageNo, int recordsPerPage) throws DataAccessException;
	public List<EventsJsonDto> listEvents(int pageNo, int recordsPerPage) throws DataAccessException;

}
