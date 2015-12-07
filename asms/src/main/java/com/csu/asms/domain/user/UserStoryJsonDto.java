/**
 * 
 */
package com.csu.asms.domain.user;

import java.util.Date;

/**
 * @author vijay
 * 
 *         this class is used to display the user information from DB into the
 *         Jqgrid
 *
 */
public class UserStoryJsonDto {

	private String email;
	private Date registeredDate;
	private Date lastLoginDate;
	private int loginCount;
	private int postCount;
	private Long csu_id;
	private String user_type;

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the registeredDate
	 */
	public Date getRegisteredDate() {
		return registeredDate;
	}

	/**
	 * @param registeredDate
	 *            the registeredDate to set
	 */
	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	/**
	 * @return the lastLoginDate
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param lastLoginDate
	 *            the lastLoginDate to set
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return the loginCount
	 */
	public int getLoginCount() {
		return loginCount;
	}

	/**
	 * @param loginCount
	 *            the loginCount to set
	 */
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	/**
	 * @return the postCount
	 */
	public int getPostCount() {
		return postCount;
	}

	/**
	 * @param postCount
	 *            the postCount to set
	 */
	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

	/**
	 * @return the user_type
	 */
	public String getUser_type() {
		return user_type;
	}

	/**
	 * @param user_type
	 *            the user_type to set
	 */
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	/**
	 * @return the csu_id
	 */
	public Long getCsu_id() {
		return csu_id;
	}

	/**
	 * @param csu_id
	 *            the csu_id to set
	 */
	public void setCsu_id(Long csu_id) {
		this.csu_id = csu_id;
	}

}
