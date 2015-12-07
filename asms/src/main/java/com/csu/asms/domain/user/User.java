package com.csu.asms.domain.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author vijay_000
 * 
 *         This class is a user domain class which is ued to store the user
 *         values into the DB
 *
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4258930079222995495L;
	private Long csuid;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String addrLine1;
	private String addrLine2;
	private String city;
	private String country;
	private String state;
	private String zipCode;
	private String phoneNumber;
	private boolean admin;
	private String confirmPwd;
	private String guid;
	private String confirmemail;
	private String adminlogin;
	private Date registeredDate;
	private UserLogins userLogins;
	private Set<UserPost> userposts;
	private Set<Events> events;
	private String post;
	private String typeOfUser;
	private boolean edit;
	private CommonsMultipartFile postimg;
	private String postType;

	private String eventDesc;
	private Date eventDate;
	private String date;
	private String eventType;

	/**
	 * @return the edit
	 */
	public boolean isEdit() {
		return edit;
	}

	/**
	 * @param edit
	 *            the edit to set
	 */
	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	/**
	 * @return the events
	 */
	public Set<Events> getEvents() {
		return events;
	}

	/**
	 * @param events
	 *            the events to set
	 */
	public void setEvents(Set<Events> events) {
		this.events = events;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the addrLine1
	 */
	public String getAddrLine1() {
		return addrLine1;
	}

	/**
	 * @param addrLine1
	 *            the addrLine1 to set
	 */
	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	/**
	 * @return the addrLine2
	 */
	public String getAddrLine2() {
		return addrLine2;
	}

	/**
	 * @param addrLine2
	 *            the addrLine2 to set
	 */
	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the confirmPwd
	 */
	public String getConfirmPwd() {
		return confirmPwd;
	}

	/**
	 * @param confirmPwd
	 *            the confirmPwd to set
	 */
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	/**
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @param guid
	 *            the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * @return the confirmemail
	 */
	public String getConfirmemail() {
		return confirmemail;
	}

	/**
	 * @param confirmemail
	 *            the confirmemail to set
	 */
	public void setConfirmemail(String confirmemail) {
		this.confirmemail = confirmemail;
	}

	/**
	 * @return the adminlogin
	 */
	public String getAdminlogin() {
		return adminlogin;
	}

	/**
	 * @param adminlogin
	 *            the adminlogin to set
	 */
	public void setAdminlogin(String adminlogin) {
		this.adminlogin = adminlogin;
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
	 * @return the userLogins
	 */
	public UserLogins getUserLogins() {
		return userLogins;
	}

	/**
	 * @param userLogins
	 *            the userLogins to set
	 */
	public void setUserLogins(UserLogins userLogins) {
		this.userLogins = userLogins;
	}

	/**
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * @param admin
	 *            the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * @return the userposts
	 */
	public Set<UserPost> getUserposts() {
		return userposts;
	}

	/**
	 * @param userposts
	 *            the userposts to set
	 */
	public void setUserposts(Set<UserPost> userposts) {
		this.userposts = userposts;
	}

	/**
	 * @return the post
	 */
	public String getPost() {
		return post;
	}

	/**
	 * @param post
	 *            the post to set
	 */
	public void setPost(String post) {
		this.post = post;
	}

	/**
	 * @return the typeOfUser
	 */
	public String getTypeOfUser() {
		return typeOfUser;
	}

	/**
	 * @param typeOfUser
	 *            the typeOfUser to set
	 */
	public void setTypeOfUser(String typeOfUser) {
		this.typeOfUser = typeOfUser;
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
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the postimg
	 */
	public CommonsMultipartFile getPostimg() {
		return postimg;
	}

	/**
	 * @param postimg
	 *            the postimg to set
	 */
	public void setPostimg(CommonsMultipartFile postimg) {
		this.postimg = postimg;
	}

	/**
	 * @return the postType
	 */
	public String getPostType() {
		return postType;
	}

	/**
	 * @param postType
	 *            the postType to set
	 */
	public void setPostType(String postType) {
		this.postType = postType;
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
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

}
