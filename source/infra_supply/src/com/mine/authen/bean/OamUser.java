/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.authen.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author anhdt8
 */
@Entity
@Table(name = "OAM_USER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OamUser.findAll", query = "SELECT o FROM OamUser o"),
    @NamedQuery(name = "OamUser.findByUserId", query = "SELECT o FROM OamUser o WHERE o.userId = :userId"),
    @NamedQuery(name = "OamUser.findByUsername", query = "SELECT o FROM OamUser o WHERE o.username = :username"),
    @NamedQuery(name = "OamUser.findByPassword", query = "SELECT o FROM OamUser o WHERE o.password = :password"),
    @NamedQuery(name = "OamUser.findByAccountStatus", query = "SELECT o FROM OamUser o WHERE o.accountStatus = :accountStatus"),
    @NamedQuery(name = "OamUser.findByCreateDate", query = "SELECT o FROM OamUser o WHERE o.createDate = :createDate"),
    @NamedQuery(name = "OamUser.findByLastModifiedDate", query = "SELECT o FROM OamUser o WHERE o.lastModifiedDate = :lastModifiedDate"),
    @NamedQuery(name = "OamUser.findByNumLoginFailAttempt", query = "SELECT o FROM OamUser o WHERE o.numLoginFailAttempt = :numLoginFailAttempt"),
    @NamedQuery(name = "OamUser.findByRandomString", query = "SELECT o FROM OamUser o WHERE o.randomString = :randomString"),
    @NamedQuery(name = "OamUser.findByMobileNumber", query = "SELECT o FROM OamUser o WHERE o.mobileNumber = :mobileNumber"),
    @NamedQuery(name = "OamUser.findByEmail", query = "SELECT o FROM OamUser o WHERE o.email = :email")})
public class OamUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ACCOUNT_STATUS")
    private Short accountStatus;
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "LAST_MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Column(name = "NUM_LOGIN_FAIL_ATTEMPT")
    private Short numLoginFailAttempt;
    @Column(name = "RANDOM_STRING")
    private String randomString;
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;
    @Column(name = "EMAIL")
    private String email;

    public OamUser() {
    }

    public OamUser(Long userId) {
        this.userId = userId;
    }

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OamUser)) {
            return false;
        }
        OamUser other = (OamUser) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mine.pcrf.persistence.model.OamUser[ userId=" + userId + " ]";
    }
    
}
