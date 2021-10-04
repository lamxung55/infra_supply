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
@Table(name = "his_charge")
public class HisCharge implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "HIS_CHARGE_ID")
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Long hisChargeId;
    @Column(name = "TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "RESULT_CODE")
    private String resultCode;
    @Column(name = "RESULT_DES")
    private String resultDes;
    @Column(name = "TEL_CO_ID")
    private String telCoId;
    @Column(name = "SERIAL")
    private String serial;
    @Column(name = "PIN")
    private String pin;
    @Column(name = "MONEY")
    private String money;

    public HisCharge() {
    }

    public HisCharge(Long hisChargeId) {
        this.hisChargeId = hisChargeId;
    }

    public Long getHisChargeId() {
        return hisChargeId;
    }

    public void setHisChargeId(Long hisChargeId) {
        this.hisChargeId = hisChargeId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDes() {
        return resultDes;
    }

    public void setResultDes(String resultDes) {
        this.resultDes = resultDes;
    }

    public String getTelCoId() {
        return telCoId;
    }

    public void setTelCoId(String telCoId) {
        this.telCoId = telCoId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hisChargeId != null ? hisChargeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HisCharge)) {
            return false;
        }
        HisCharge other = (HisCharge) object;
        if ((this.hisChargeId == null && other.hisChargeId != null) || (this.hisChargeId != null && !this.hisChargeId.equals(other.hisChargeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mine.model.HisCharge[ hisChargeId=" + hisChargeId + " ]";
    }
    
}
