package com.mine.datamodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Mina Mimi on 10/7/2021.
 */
@Entity
@Table(name = "device", schema = "infra_supply")
public class DeviceEntity {
    private Integer id;
    private String code;
    private String name;
    private String configuration;
    private Double price;
    private String unit;
    private String deviceType;
    private String infraType;
    private Double vCpu;
    private Double vRam;
    private Double totalAvail;
    private Double ssd;
    private Double hdd;
    private Double tiering;
    private String note;

    @Id
    @Column(name = "ID")
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "CONFIGURATION")
    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    @Basic
    @Column(name = "PRICE")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "UNIT")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "DEVICE_TYPE")
    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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
    @Column(name = "V_CPU")
    public Double getvCpu() {
        return vCpu;
    }

    public void setvCpu(Double vCpu) {
        this.vCpu = vCpu;
    }

    @Basic
    @Column(name = "V_RAM")
    public Double getvRam() {
        return vRam;
    }

    public void setvRam(Double vRam) {
        this.vRam = vRam;
    }

    @Basic
    @Column(name = "TOTAL_AVAIL")
    public Double getTotalAvail() {
        return totalAvail;
    }

    public void setTotalAvail(Double totalAvail) {
        this.totalAvail = totalAvail;
    }

    @Basic
    @Column(name = "SSD")
    public Double getSsd() {
        return ssd;
    }

    public void setSsd(Double ssd) {
        this.ssd = ssd;
    }

    @Basic
    @Column(name = "HDD")
    public Double getHdd() {
        return hdd;
    }

    public void setHdd(Double hdd) {
        this.hdd = hdd;
    }

    @Basic
    @Column(name = "TIERING")
    public Double getTiering() {
        return tiering;
    }

    public void setTiering(Double tiering) {
        this.tiering = tiering;
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

        DeviceEntity that = (DeviceEntity) o;

        if (id != that.id) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (configuration != null ? !configuration.equals(that.configuration) : that.configuration != null)
            return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;
        if (deviceType != null ? !deviceType.equals(that.deviceType) : that.deviceType != null) return false;
        if (infraType != null ? !infraType.equals(that.infraType) : that.infraType != null) return false;
        if (vCpu != null ? !vCpu.equals(that.vCpu) : that.vCpu != null) return false;
        if (vRam != null ? !vRam.equals(that.vRam) : that.vRam != null) return false;
        if (totalAvail != null ? !totalAvail.equals(that.totalAvail) : that.totalAvail != null) return false;
        if (ssd != null ? !ssd.equals(that.ssd) : that.ssd != null) return false;
        if (hdd != null ? !hdd.equals(that.hdd) : that.hdd != null) return false;
        if (tiering != null ? !tiering.equals(that.tiering) : that.tiering != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;

        return true;
    }
}
