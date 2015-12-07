/**
 * This class is used to store the professor details into the DB
 */
package com.csu.asms.domain.professor;

import java.io.Serializable;

/**
 * @author vijay
 *
 */
public class Professor implements Serializable {

	private static final long serialVersionUID = -5761928267185370992L;

	private int prof_id;
	private String profName;
	private String profEmail;
	private String phoneNumber;
	private String department;
	private String profResearch;

	/**
	 * @return the prof_id
	 */
	public int getProf_id() {
		return prof_id;
	}

	/**
	 * @param prof_id
	 *            the prof_id to set
	 */
	public void setProf_id(int prof_id) {
		this.prof_id = prof_id;
	}

	/**
	 * @return the profName
	 */
	public String getProfName() {
		return profName;
	}

	/**
	 * @param profName
	 *            the profName to set
	 */
	public void setProfName(String profName) {
		this.profName = profName;
	}

	/**
	 * @return the profEmail
	 */
	public String getProfEmail() {
		return profEmail;
	}

	/**
	 * @param profEmail
	 *            the profEmail to set
	 */
	public void setProfEmail(String profEmail) {
		this.profEmail = profEmail;
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
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the profResearch
	 */
	public String getProfResearch() {
		return profResearch;
	}

	/**
	 * @param profResearch
	 *            the profResearch to set
	 */
	public void setProfResearch(String profResearch) {
		this.profResearch = profResearch;
	}

}
