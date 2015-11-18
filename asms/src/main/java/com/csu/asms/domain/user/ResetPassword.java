/**
 * 
 */
package com.csu.asms.domain.user;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vijay
 *
 */
public class ResetPassword implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long csuid;
	private String guiId;
	private Date resetDate;
	
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
	public String getGuiId() {
		return guiId;
	}
	public void setGuiId(String guiId) {
		this.guiId = guiId;
	}
	public Date getResetDate() {
		return resetDate;
	}
	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}

}
