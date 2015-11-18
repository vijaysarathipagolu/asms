package com.csu.asms.domain.user;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author vijay_000
 *
 */

public class UserPost implements Serializable {
	
	private static final long serialVersionUID = 3493575439990787046L;
	
	private Long postId;
	private Long csuid;
	private String post;
	private Date postDate;
	private String emailId;
	private String postType;
	private CommonsMultipartFile postimg;
	private String imageName;
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
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	/**
	 * @return the postId
	 */
	public Long getPostId() {
		return postId;
	}
	/**
	 * @param postId the postId to set
	 */
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	
	
	/**
	 * @return the postimg
	 */
	public CommonsMultipartFile getPostimg() {
		return postimg;
	}
	/**
	 * @param postimg the postimg to set
	 */
	public void setPostimg(CommonsMultipartFile postimg) {
		this.postimg = postimg;
	}
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
	
	
	

}
