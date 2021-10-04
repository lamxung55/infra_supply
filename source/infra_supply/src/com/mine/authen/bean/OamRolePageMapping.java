/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.authen.bean;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author anhdt8
 */
@Entity
@Table(name = "OAM_ROLE_PAGE_MAPPING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OamRolePageMapping.findAll", query = "SELECT o FROM OamRolePageMapping o"),
    @NamedQuery(name = "OamRolePageMapping.findById", query = "SELECT o FROM OamRolePageMapping o WHERE o.id = :id"),
    @NamedQuery(name = "OamRolePageMapping.findByRoleId", query = "SELECT o FROM OamRolePageMapping o WHERE o.roleId = :roleId"),
    @NamedQuery(name = "OamRolePageMapping.findByPageId", query = "SELECT o FROM OamRolePageMapping o WHERE o.pageId = :pageId"),
    @NamedQuery(name = "OamRolePageMapping.findByValidFrom", query = "SELECT o FROM OamRolePageMapping o WHERE o.validFrom = :validFrom"),
    @NamedQuery(name = "OamRolePageMapping.findByValidTo", query = "SELECT o FROM OamRolePageMapping o WHERE o.validTo = :validTo"),
    @NamedQuery(name = "OamRolePageMapping.findByStatus", query = "SELECT o FROM OamRolePageMapping o WHERE o.status = :status")})
public class OamRolePageMapping implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "ROLE_ID")
    private Long roleId;
    @Column(name = "PAGE_ID")
    private Long pageId;
    @Column(name = "VALID_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;
    @Column(name = "VALID_TO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validTo;
    @Column(name = "STATUS")
    private Short status;

    public OamRolePageMapping() {
    }

    public OamRolePageMapping(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
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
        if (!(object instanceof OamRolePageMapping)) {
            return false;
        }
        OamRolePageMapping other = (OamRolePageMapping) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mine.pcrf.persistence.model.OamRolePageMapping[ id=" + id + " ]";
    }
    
}
