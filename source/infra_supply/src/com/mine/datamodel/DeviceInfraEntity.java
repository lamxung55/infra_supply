package com.mine.datamodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Mina Mimi on 10/10/2021.
 */
@Entity
@Table(name = "device_infra", schema = "infra_supply")
public class DeviceInfraEntity {
    private Long id;
    private Long deviceId;
    private String deviceCode;
    private String deviceName;
    private Long infraId;
    private String infraCode;
    private String infraName;
    private String infraType;
    private Long count;
    private String note;

    @Id
    @Column(name = "ID")
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DEVICE_ID")
    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    @Basic
    @Column(name = "INFRA_ID")
    public Long getInfraId() {
        return infraId;
    }

    public void setInfraId(Long infraId) {
        this.infraId = infraId;
    }

    @Basic
    @Column(name = "COUNT")
    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
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

    @Basic
    @Column(name = "INFRA_CODE")
    public String getInfraCode() {
        return infraCode;
    }

    public void setInfraCode(String infraCode) {
        this.infraCode = infraCode;
    }

    @Basic
    @Column(name = "DEVICE_CODE")
    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    @Basic
    @Column(name = "DEVICE_NAME")
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Basic
    @Column(name = "INFRA_NAME")
    public String getInfraName() {
        return infraName;
    }

    public void setInfraName(String infraName) {
        this.infraName = infraName;
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

//    @Override
//    public long hashCode() {
//        long result = id;
//        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
//        result = 31 * result + (infraId != null ? infraId.hashCode() : 0);
//        result = 31 * result + (count != null ? count.hashCode() : 0);
//        result = 31 * result + (note != null ? note.hashCode() : 0);
//        return result;
//    }

}
