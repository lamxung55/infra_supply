package com.mine.datamodel;

import javax.persistence.*;

/**
 * Created by Mina Mimi on 10/10/2021.
 */
@Entity
@Table(name = "device_infra", schema = "infra_supply")
public class DeviceInfraEntity {
    private int id;
    private Integer deviceId;
    private Integer infraId;
    private String infraType;
    private Integer count;
    private String note;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DEVICE_ID")
    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    @Basic
    @Column(name = "INFRA_ID")
    public Integer getInfraId() {
        return infraId;
    }

    public void setInfraId(Integer infraId) {
        this.infraId = infraId;
    }

    @Basic
    @Column(name = "COUNT")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Basic
    @Column(name = "INFRA_TYPE")
    public String getInfraType() {
        return infraType;
    }

    public void setInfraType(String infraType) {
        this.infraType = infraType;
    }

    @Basic
    @Column(name = "NOTE")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceInfraEntity that = (DeviceInfraEntity) o;

        if (id != that.id) return false;
        if (deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null) return false;
        if (infraId != null ? !infraId.equals(that.infraId) : that.infraId != null) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (infraId != null ? infraId.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

}
