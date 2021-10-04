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
@Table(name = "OAM_USER_ROLE_MAPPING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OamUserRoleMapping.findAll", query = "SELECT o FROM OamUserRoleMapping o"),
    @NamedQuery(name = "OamUserRoleMapping.findById", query = "SELECT o FROM OamUserRoleMapping o WHERE o.id = :id"),
    @NamedQuery(name = "OamUserRoleMapping.findByUserId", query = "SELECT o FROM OamUserRoleMapping o WHERE o.userId = :userId"),
    @NamedQuery(name = "OamUserRoleMapping.findByRoleId", query = "SELECT o FROM OamUserRoleMapping o WHERE o.roleId = :roleId"),
    @NamedQuery(name = "OamUserRoleMapping.findByValidFrom", query = "SELECT o FROM OamUserRoleMapping o WHERE o.validFrom = :validFrom"),
    @NamedQuery(name = "OamUserRoleMapping.findByValidTo", query = "SELECT o FROM OamUserRoleMapping o WHERE o.validTo = :validTo"),
    @NamedQuery(name = "OamUserRoleMapping.findByStatus", query = "SELECT o FROM OamUserRoleMapping o WHERE o.status = :status")})
public class OamUserRoleMapping implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "ID")
    private Long id;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "ROLE_ID")
    private Integer roleId;
    @Column(name = "VALID_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;
    @Column(name = "VALID_TO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validTo;
    @Column(name = "STATUS")
    private Short status;

    public OamUserRoleMapping() {
    }

    public OamUserRoleMapping(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OamUserRoleMapping)) {
            return false;
        }
        OamUserRoleMapping other = (OamUserRoleMapping) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mine.pcrf.persistence.model.OamUserRoleMapping[ id=" + id + " ]";
    }
    
}
