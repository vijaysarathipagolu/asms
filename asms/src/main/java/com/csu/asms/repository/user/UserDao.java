/**
 * 
 */
package com.csu.asms.repository.user;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.csu.asms.domain.PostJsonDto;
import com.csu.asms.domain.user.ResetPassword;
import com.csu.asms.domain.user.User;
import com.csu.asms.domain.user.UserLogins;
import com.csu.asms.domain.user.UserPost;



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

	public void savePost(UserPost post) throws DataAccessException;
	public List<PostJsonDto> userPosts(int pageNo,Long csuid,Integer recordsPerPage)throws DataAccessException;
	public int getPostTotalRecords() throws DataAccessException;
	public void removePost(Integer postId) throws DataAccessException;
	
	

}
