/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.authen.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author anhdt8
 */
@Entity
@Table(name = "OAM_USER_ROLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OamUserRole.findAll", query = "SELECT o FROM OamUserRole o"),
    @NamedQuery(name = "OamUserRole.findByRoleId", query = "SELECT o FROM OamUserRole o WHERE o.roleId = :roleId"),
    @NamedQuery(name = "OamUserRole.findByRoleName", query = "SELECT o FROM OamUserRole o WHERE o.roleName = :roleName"),
    @NamedQuery(name = "OamUserRole.findByDescription", query = "SELECT o FROM OamUserRole o WHERE o.description = :description"),
    @NamedQuery(name = "OamUserRole.findByPageUrl", query = "SELECT o FROM OamUserRole o WHERE o.pageUrl = :pageUrl"),
    @NamedQuery(name = "OamUserRole.findByActionId", query = "SELECT o FROM OamUserRole o WHERE o.actionId = :actionId")})
public class OamUserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "ROLE_ID")
    private Integer roleId;
    @Column(name = "ROLE_NAME")
    private String roleName;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PAGE_URL")
    private String pageUrl;
    @Column(name = "ACTION_ID")
    private String actionId;

    public OamUserRole() {
    }

    public OamUserRole(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleId != null ? roleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OamUserRole)) {
            return false;
        }
        OamUserRole other = (OamUserRole) object;
        if ((this.roleId == null && other.roleId != null) || (this.roleId != null && !this.roleId.equals(other.roleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mine.pcrf.persistence.model.OamUserRole[ roleId=" + roleId + " ]";
    }
    
}
