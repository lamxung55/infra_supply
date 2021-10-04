/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cert2
 */
@Entity
@Table(name = "app_version")
public class MyAppVersion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "APP_VERSION_ID")
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Integer appVersionId;
    @Column(name = "VERSION_NAME")
    private String versionName;
    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "IS_NEED")
    private Short isNeed;

    public MyAppVersion() {
    }

    public MyAppVersion(Integer appVersionId) {
        this.appVersionId = appVersionId;
    }

    public Integer getAppVersionId() {
        return appVersionId;
    }

    public void setAppVersionId(Integer appVersionId) {
        this.appVersionId = appVersionId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Short getIsNeed() {
        return isNeed;
    }

    public void setIsNeed(Short isNeed) {
        this.isNeed = isNeed;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appVersionId != null ? appVersionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MyAppVersion)) {
            return false;
        }
        MyAppVersion other = (MyAppVersion) object;
        if ((this.appVersionId == null && other.appVersionId != null) || (this.appVersionId != null && !this.appVersionId.equals(other.appVersionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tnt.gameonline.model.AppVersion[ appVersionId=" + appVersionId + " ]";
    }

}
