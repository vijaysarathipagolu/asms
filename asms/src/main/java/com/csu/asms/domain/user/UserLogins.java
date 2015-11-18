package com.csu.asms.domain.user;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vijay_000
 *
 */

public class UserLogins implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long csuid;
	private Long loginCount;
	private Date lastLogin;
	private User user;
	
	
	/**
	 * @return the csuid
	 */
	public Long getCsuid() {
		return csuid;
	}

	/**
	 * @param csuid the csuid to set
	 */
	public void setCsuid(Long csuid) {
		this.csuid = csuid;
	}

	
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the loginCount
	 */
	public Long getLoginCount() {
		return loginCount;
	}

	/**
	 * @param loginCount the loginCount to set
	 */
	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}

}
