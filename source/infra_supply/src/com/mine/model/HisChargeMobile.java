/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mine.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author cert2
 */
@Entity
@Table(name = "his_charge_mobile")
public class HisChargeMobile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "HIS_CHARGE_ID")
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Long hisChargeId;
    @Column(name = "TIME")
    private String time;
    @Column(name = "CHARGE_INFO")
    private String chargeInfo;
    @Column(name = "USER_ID")
    private Long userId;

    public HisChargeMobile() {
    }

    public HisChargeMobile(Long hisChargeId) {
        this.hisChargeId = hisChargeId;
    }

    public Long getHisChargeId() {
        return hisChargeId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getChargeInfo() {
        return chargeInfo;
    }

    public void setChargeInfo(String chargeInfo) {
        this.chargeInfo = chargeInfo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
}
