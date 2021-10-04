/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.authen.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Date;


public class OamUserFormBean implements Serializable {
	private Long userId;
	private String username;
	private String password;
	private Short accountStatus;
	private Date createDate;
	private Date lastModifiedDate;
	private Short numLoginFailAttempt;
	private String randomString;
	private String mobileNumber;
	private String email;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Short getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Short accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Short getNumLoginFailAttempt() {
		return numLoginFailAttempt;
	}

	public void setNumLoginFailAttempt(Short numLoginFailAttempt) {
		this.numLoginFailAttempt = numLoginFailAttempt;
	}

	public String getRandomString() {
		return randomString;
	}

	public void setRandomString(String randomString) {
		this.randomString = randomString;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
