package com.csu.asms.domain;

import java.util.Date;

public class PostJsonDto {

	private Long postid;
	private String post;
	private Date postDate;	
	private String name;	
	private String email;
	private Long csuId;
	private String imageName;
	private String postType;
	/**
	 * @return the post
	 */
	public String getPost() {
		return post;
	}
	/**
	 * @param post the post to set
	 */
	public void setPost(String post) {
		this.post = post;
	}
	/**
	 * @return the postDate
	 */
	public Date getPostDate() {
		return postDate;
	}
	/**
	 * @param postDate the postDate to set
	 */
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the csuId
	 */
	public Long getCsuId() {
		return csuId;
	}
	/**
	 * @param csuId the csuId to set
	 */
	public void setCsuId(Long csuId) {
		this.csuId = csuId;
	}
	/**
	 * @return the imageName
	 */
	public String getImageName() {
		return imageName;
	}
	/**
	 * @param imageName the imageName to set
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	/**
	 * @return the postid
	 */
	public Long getPostid() {
		return postid;
	}
	/**
	 * @param postid the postid to set
	 */
	public void setPostid(Long postid) {
		this.postid = postid;
	}
	/**
	 * @return the postType
	 */
	public String getPostType() {
		return postType;
	}
	/**
	 * @param postType the postType to set
	 */
	public void setPostType(String postType) {
		this.postType = postType;
	}
}
