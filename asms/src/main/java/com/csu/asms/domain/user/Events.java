/**
 * This class is used to store the events into the DB
 */
package com.csu.asms.domain.user;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vijay
 *
 */
public class Events implements Serializable {

	private static final long serialVersionUID = -7689183915375169914L;

	private int eventid;
	private Long csuid;
	private String eventDesc;
	private Date eventDate;
	private String eventType;
	private String email;

	/**
	 * @return the eventid
	 */
	public int getEventid() {
		return eventid;
	}

	/**
	 * @param eventid
	 *            the eventid to set
	 */
	public void setEventid(int eventid) {
		this.eventid = eventid;
	}

	/**
	 * @return the eventDesc
	 */
	public String getEventDesc() {
		return eventDesc;
	}

	/**
	 * @param eventDesc
	 *            the eventDesc to set
	 */
	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

	/**
	 * @return the csuid
	 */
	public Long getCsuid() {
		return csuid;
	}

	/**
	 * @param csuid
	 *            the csuid to set
	 */
	public void setCsuid(Long csuid) {
		this.csuid = csuid;
	}

	/**
	 * @return the eventDate
	 */
	public Date getEventDate() {
		return eventDate;
	}

	/**
	 * @param eventDate
	 *            the eventDate to set
	 */
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @param eventType
	 *            the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

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

}
