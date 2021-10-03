package com.mine.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;

/**
 * Created by cert2 on 9/10/2017.
 */
@Entity
@Table(name = "shipping", schema = "xe", catalog = "")
public class ShippingEntity {
    private int id;
    private Date createTime;
    private Double income;
    private Double remainIncome;
    private Integer status;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "INCOME")
    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    @Basic
    @Column(name = "REMAIN_INCOME")
    public Double getRemainIncome() {
        return remainIncome;
    }

    public void setRemainIncome(Double remainIncome) {
        this.remainIncome = remainIncome;
    }

    @Basic
    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShippingEntity that = (ShippingEntity) o;

        if (id != that.id) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (income != null ? !income.equals(that.income) : that.income != null) return false;
        if (remainIncome != null ? !remainIncome.equals(that.remainIncome) : that.remainIncome != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (income != null ? income.hashCode() : 0);
        result = 31 * result + (remainIncome != null ? remainIncome.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
