/**
 * 
 */
package com.csu.asms.repository.user;

import java.math.BigInteger;
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
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(user);
		} catch (DataAccessException e) {
			log.error("Exception in Update " + e);
			throw e;
		}

		
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

	@SuppressWarnings("unchecked")
	@Override
	public ResetPassword validateGuid(String guid) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			List<ResetPassword> resetpwds = sessionFactory
					.getCurrentSession()
					.createQuery(
							"from ResetPassword resetpwd where resetpwd.guiId=:guiId")
					.setString("guiId", guid).list();

			if (null != resetpwds && resetpwds.size() > 0)
				return resetpwds.get(0);
		} catch (HibernateException e) {
			log.error("error in validateGuid", e);
			throw e;
		}
		return null;
	}

	@Override
	public void removeGuid(String guid) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			log.debug("in removeGuid Method" + guid);
			ResetPassword resetpassword = validateGuid(guid);
			sessionFactory.getCurrentSession().delete(resetpassword);
		} catch (DataAccessException e) {
			log.error("Exception in removeGuid");
			throw e;
		}
		
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
		sessionFactory.getCurrentSession().merge(resetpassword);
		
	}
	
	/**
	 * this is used to get the total count for the users
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getUserTotalRecords() throws DataAccessException {
		// TODO Auto-generated method stub
		List<User> userlist = null;
		try {
			userlist = sessionFactory.getCurrentSession()
					.createQuery("from User user").list();
		} catch (DataAccessException e) {
			log.error("exception in getTotalRecords" + e);
			throw e;

		}
		return userlist.size();

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
	public List<PostJsonDto> listUsersPosts(int pageNo, int recordsPerPage) throws DataAccessException {
		// TODO Auto-generated method stub
		List<PostJsonDto> result = new ArrayList<PostJsonDto>();
		try{
			StringBuffer stringQueryBuffer = new StringBuffer();
			stringQueryBuffer.append("select post_id,post,post_date,post_type,csu_id ");
			stringQueryBuffer.append("from user_posts ");
			
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
				pdto.setCsuId(Long.parseLong(object[4].toString()));
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
	public List<EventsJsonDto> listEvents(int pageNo, int recordsPerPage) throws DataAccessException {
		// TODO Auto-generated method stub
		List<EventsJsonDto> result = new ArrayList<EventsJsonDto>();
		try{
			StringBuffer stringQueryBuffer = new StringBuffer();
			stringQueryBuffer.append("select event_id,csu_id,email,event_desc,event_date,event_type ");
			stringQueryBuffer.append("from events ");
			
			String stringQuery = stringQueryBuffer.toString();
			Query query = sessionFactory.getCurrentSession().createSQLQuery(
					stringQuery);
			query.setFirstResult((pageNo - 1) * recordsPerPage);
			query.setMaxResults(recordsPerPage);
			
			List<Object[]> list = query.list();
			for (int i=0; i < list.size(); i++){ 
				Object[] object = list.get(i);
				EventsJsonDto edto = new EventsJsonDto();
				edto.setEventid(Integer.parseInt(object[0].toString()));
				edto.setCsuid(Long.parseLong(object[1].toString()));
				edto.setEmail(object[2].toString());
				edto.setEventDesc(object[3].toString());
				edto.setEventDate((Date)object[4]);
				edto.setEventType(object[5].toString());
				log.info("in dao printing events" +edto.toString());
				result.add(edto);
			}
			log.info("the size of events"+result.size());
			
		}catch(HibernateException e){
			log.error("in all events method in dao" +e);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Professor> listProf() throws DataAccessException {
		// TODO Auto-generated method stub
		log.info("in list prof method of dao");
		List<Professor> result = new ArrayList<Professor>();
		try{
			Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from professors");
			List<Object[]> list = query.list();
			for (int i=0; i < list.size(); i++){ 
				Object[] object = list.get(i);
				Professor p = new Professor();
				p.setProf_id(Integer.parseInt(object[0].toString()));
				p.setProfName(object[1].toString());
				p.setProfEmail(object[2].toString());
				p.setPhoneNumber(object[3].toString());
				p.setDepartment(object[4].toString());
				p.setProfResearch(object[5].toString());
				result.add(p);
			}
		}catch(HibernateException e){
			log.error("error in list prof method of dao class"+ e);
		}
		return result;
	}

	@Override
	public void saveEmail(UserEmail usermail) throws DataAccessException {
		// TODO Auto-generated method stub
		log.info("in save user email method in dao");
		if (sessionFactory.getCurrentSession()!= null) {
			try {
				sessionFactory.getCurrentSession().save(usermail);
			} catch (DataAccessException ex) {
				log.error(ex.getMessage(),ex);
				throw ex;
			} catch( Throwable t){
				throw new DataAccessException(t.getMessage(),t) {

					/**
					 * 
					 */
					private static final long serialVersionUID = -344409435816433507L;
				};
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEmail> getEmailRequests() throws DataAccessException {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from UserEmail where scheduled=:scheduled")
				.setBoolean("scheduled", false).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserStoryJsonDto> getUserList(String columnName, String order, int pageNo, int recordsPerPage)
			throws DataAccessException {
		// TODO Auto-generated method stub

		List<UserStoryJsonDto> userList = new ArrayList<UserStoryJsonDto>();

		StringBuffer stringQueryBuffer = new StringBuffer();
		stringQueryBuffer.append("select UD.csu_id as csu_id,UD.email as email, ")
						 .append("UD.created_date as created_date, ")
						 .append("UD.user_type as user_type, ")
						 .append("UL.last_login as last_login, ")
						 .append("UL.login_count as login_count, ")
						 .append("(select count(*) from user_posts UP ")
						 .append("where UP.csu_id=UL.csu_id ")
						 .append("and UP.csu_id=UD.csu_id) ")
						 .append("as up_count ")
						 .append("from user_details UD ")
						 .append("left outer join user_logins UL ")
						 .append("on UD.csu_id = UL.csu_id ")
						 .append("left outer join user_posts UP ")
						 .append("on UD.csu_id = UP.csu_id ")
						 .append("group by UD.csu_id ");
							
		if (null != columnName) {
			if (columnName.equals("csu_id"))
				if (order.equals("desc"))
					stringQueryBuffer.append("order by UD.csu_id desc");
				else
					stringQueryBuffer.append("order by UD.csu_id asc");
			else if (columnName.equals("email"))
				if (order.equals("desc"))
					stringQueryBuffer.append("order by UD.email desc");
				else
					stringQueryBuffer.append("order by UD.email asc");

			else if (columnName.equals("registeredDate"))
				if (order.equals("desc"))
					stringQueryBuffer.append("order by UD.created_date desc");
				else
					stringQueryBuffer.append("order by UD.created_date asc");
			else if (columnName.equals("userType"))
				if (order.equals("desc"))
					stringQueryBuffer.append("order by UD.user_type desc");
				else
					stringQueryBuffer.append("order by UD.user_type asc");

			else if (columnName.equals("lastLoginDate"))
				if (order.equals("desc"))
					stringQueryBuffer.append("order by UL.last_login desc");
				else
					stringQueryBuffer.append("order by UL.last_login asc");

			else if (columnName.equals("loginCount"))
				if (order.equals("desc"))
					stringQueryBuffer.append("order by UL.login_count desc");
				else
					stringQueryBuffer.append("order by UL.login_count asc");

			else if (columnName.equals("postCount"))
				if (order.equals("desc"))
					stringQueryBuffer.append("order by up_count desc");
				else
					stringQueryBuffer.append("order by up_count asc");

		}

		String stringQuery = stringQueryBuffer.toString();

		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				stringQuery);

		query.setFirstResult((pageNo - 1) * recordsPerPage);
		query.setMaxResults(recordsPerPage);

		List<Object[]> list = query.list();

		for (int i = 0; i < list.size(); i++) {

			Object[] objs = list.get(i);
			UserStoryJsonDto userStory = new UserStoryJsonDto();
			userStory.setCsu_id((Long.valueOf(objs[0].toString())));
			System.out.println("csu ids--"+userStory.getCsu_id());
			userStory.setEmail(objs[1].toString());
			userStory.setRegisteredDate((Date) objs[2]);
			if(objs[3] !=null)
				userStory.setUser_type(objs[3].toString());
			if (objs[4] != null)
				userStory.setLastLoginDate((Date) objs[4]);
			if (objs[5] != null)
				userStory.setLoginCount((Integer) objs[5]);
			if (objs[6] != null)
				userStory.setPostCount(((BigInteger) objs[6]).intValue());
			

			userList.add(userStory);

			log.debug(objs[0] + "\t" + objs[1] + "\t" + objs[2] + "\t"
					+ objs[3] + "\t" + objs[4] + "\t" + objs[5] + "\t" + objs[6]);

		}

		return userList;

	}

	@Override
	public void saveEvent(Events event) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			log.info("in save post method in dao");
			sessionFactory.getCurrentSession().save(event);
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void removeEvent(Integer eventId) throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			log.info("in remove event method of dao");
			StringBuffer query = new StringBuffer();
			query.append("delete from ");
			query.append("events ");
			query.append("where event_id="+eventId);
			String stringQuery = query.toString();
			Query query1 = sessionFactory.getCurrentSession().createSQLQuery(stringQuery);
			int result = query1.executeUpdate();
			System.out.println("the result -- "+result);
			
		}catch(HibernateException e){
			log.error("in remove event in dao" +e);
		}
	}
	
}
