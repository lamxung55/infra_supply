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
@Table(name = "OAM_ROLE_ACTION_MAPPING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OamRoleActionMapping.findAll", query = "SELECT o FROM OamRoleActionMapping o"),
    @NamedQuery(name = "OamRoleActionMapping.findById", query = "SELECT o FROM OamRoleActionMapping o WHERE o.id = :id"),
    @NamedQuery(name = "OamRoleActionMapping.findByRoleId", query = "SELECT o FROM OamRoleActionMapping o WHERE o.roleId = :roleId"),
    @NamedQuery(name = "OamRoleActionMapping.findByActionId", query = "SELECT o FROM OamRoleActionMapping o WHERE o.actionId = :actionId")})
public class OamRoleActionMapping implements Serializable {
    private static final long serialVersionUID = 1L;

    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "ID")
    private Long id;
    @Column(name = "ROLE_ID")
    private Long roleId;
    @Column(name = "ACTION_ID")
    private Short actionId;

    public OamRoleActionMapping() {
    }

    public OamRoleActionMapping(Long id) {
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

    public Short getActionId() {
        return actionId;
    }

    public void setActionId(Short actionId) {
        this.actionId = actionId;
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
        if (!(object instanceof OamRoleActionMapping)) {
            return false;
        }
        OamRoleActionMapping other = (OamRoleActionMapping) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mine.pcrf.persistence.model.OamRoleActionMapping[ id=" + id + " ]";
    }
    
}
