/**
 * 
 */
package com.csu.asms.repository.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.csu.asms.domain.PostJsonDto;
import com.csu.asms.domain.user.ResetPassword;
import com.csu.asms.domain.user.User;
import com.csu.asms.domain.user.UserLogins;
import com.csu.asms.domain.user.UserPost;

/**
 * @author vijay
 *
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {
	
	private SessionFactory sessionFactory;
	
	private static Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

	/**
	 * @param sessionFactory
	 */
	@Autowired
	public UserDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/*public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
*/

	@Override
	public void store(User user) throws DataAccessException {
		// TODO Auto-generated method stub
		log.debug("in store method in dao");
		try {
			if(! user.isAdmin()){
				String hashed = BCrypt.hashpw(user.getPassword(),
						BCrypt.gensalt(12));
				user.setPassword(hashed);			
				
			}
			sessionFactory.getCurrentSession().save(user);

		} catch (DataAccessException e) {
			log.error("In store " + e);
			throw e;
		}
		
	}

	@Override
	public void update(User user) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUser(Long csuid) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findUser(Long csuid) throws DataAccessException {
		// TODO Auto-generated method stub
		
		log.debug("received request for user " + csuid);
		User user = null;
		try {
			user = (User) sessionFactory.getCurrentSession().get(User.class,
					csuid);

		} catch (DataAccessException e) {
			log.error("In FindUser " + e);
			throw e;
		}
		log.info("exit from the findUser In DaoImpl");
		return user;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public User validateUser(Long csuid, String password) throws DataAccessException {
		// TODO Auto-generated method stub
		List<User> users = null;
		log.info("in validate user in dao");
		try {
			users = sessionFactory.getCurrentSession()
					.createQuery("from User user where user.csuid=:csuid")
					.setLong("csuid", csuid).list();

			if (null != users && users.size() > 0) {

				User user = users.get(0);
				if (BCrypt.checkpw(password, user.getPassword()))
					return user;
			}

		}catch(HibernateException e){
			log.error("error in validate user"+ e);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public User validateCsuid(Long csuid) throws DataAccessException {
		// TODO Auto-generated method stub
		List<User> users = null;
		log.debug("in validate csuid ");
		try{
		users= sessionFactory.getCurrentSession()
				.createQuery("from User user where user.csuid=:csuid")
				 .setLong("csuid", csuid).list();
		if (null != users && users.size() > 0)
			return users.get(0);
		}catch(HibernateException e){
			log.error("exception in validate csuid" + e);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public User validateEmail(String email) throws DataAccessException {
		// TODO Auto-generated method stub
		List<User> users=null;
		log.debug("in validate email ");
		try{
		users=sessionFactory.getCurrentSession()
				.createQuery("from User user where user.email=:email")
				 .setString("email", email).list();
		if(null != users && users.size() > 0)
			return users.get(0);
		}catch(HibernateException e){
			log.error("exception in validate email" + e);
		}
		return null;
	}

	@Override
	public void storeUserLogins(UserLogins userlogins) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			sessionFactory.getCurrentSession().merge(userlogins);

		} catch (Throwable t) {
			log.error("Exception in storeUserLogins", t);
			throw new DataAccessException(t.getMessage(),t) {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1820315571900481711L;
			};
		}
		
	}

	@Override
	public ResetPassword validateGuid(String guid) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeGuid(String guid) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserLogins getUserLogins(Long csuid) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			log.debug("csuid in getUserLogins " + csuid);
			List<UserLogins> userlogins = sessionFactory
					.getCurrentSession()
					.createQuery(
							"from UserLogins userlogins where userlogins.csuid=:csuid")
					.setLong("csuid", csuid).list();

			if (null != userlogins && userlogins.size() > 0)
				return userlogins.get(0);
		} catch (DataAccessException e) {
			log.error("Exception in getUserLogins" + e);
			throw e;
		}
		return null;
	}

	@Override
	public void resetPassword(ResetPassword resetpassword) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getUserTotalRecords() throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void savePost(UserPost post) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			log.info("in save post method in dao");
			sessionFactory.getCurrentSession().save(post);
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PostJsonDto> userPosts(int pageNo, Long csuid, Integer recordsPerPage) throws DataAccessException {
		// TODO Auto-generated method stub
		List<PostJsonDto> result = new ArrayList<PostJsonDto>();
		try{
			StringBuffer stringQueryBuffer = new StringBuffer();
			stringQueryBuffer.append("select post_id,post,post_date,post_type ");
			stringQueryBuffer.append("from user_posts ");
			if(csuid!=null){
				stringQueryBuffer.append("where csu_id="+csuid);
			}
			String stringQuery = stringQueryBuffer.toString();
			Query query = sessionFactory.getCurrentSession().createSQLQuery(
					stringQuery);
			query.setFirstResult((pageNo - 1) * recordsPerPage);
			query.setMaxResults(recordsPerPage);
			
			List<Object[]> list = query.list();
			for (int i=0; i < list.size(); i++){ 
				Object[] object = list.get(i);
				PostJsonDto pdto = new PostJsonDto();
				pdto.setPostid(Long.parseLong(object[0].toString()));
				pdto.setPost(object[1].toString());
				pdto.setPostDate((Date)object[2]);
				pdto.setPostType(object[3].toString());
				log.info("in dao printing posts" +pdto.toString());
				result.add(pdto);
			}
			log.info("the size of posts"+result.size());
			
		}catch(HibernateException e){
			log.error("in user posts method in dao" +e);
		}
		
		return result;
	}

	@Override
	public int getPostTotalRecords() throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removePost(Integer postId) throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			log.info("in remove post method of dao");
			StringBuffer query = new StringBuffer();
			query.append("delete from ");
			query.append("user_posts ");
			query.append("where post_id="+postId);
			String stringQuery = query.toString();
			Query query1 = sessionFactory.getCurrentSession().createSQLQuery(stringQuery);
			int result = query1.executeUpdate();
			System.out.println("the result -- "+result);
			
		}catch(HibernateException e){
			log.error("in remove post in dao" +e);
		}
		
	}

}
