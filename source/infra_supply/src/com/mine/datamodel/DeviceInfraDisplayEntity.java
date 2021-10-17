package com.mine.datamodel;

import javax.persistence.*;

/**
 * Created by Mina Mimi on 10/10/2021.
 */
public class DeviceInfraDisplayEntity<T> {
    private int id;
    private Integer deviceId;
    private Integer infraId;
    private Integer count;
    private String note;
    private DeviceEntity device;
    private T infra;

    public DeviceInfraDisplayEntity(int id, Integer deviceId, Integer infraId, Integer count, String note, DeviceEntity device, T infra) {
        this.id = id;
        this.deviceId = deviceId;
        this.infraId = infraId;
        this.count = count;
        this.note = note;
        this.device = device;
        this.infra = infra;
    }

    public DeviceInfraDisplayEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getInfraId() {
        return infraId;
    }

    public void setInfraId(Integer infraId) {
        this.infraId = infraId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public DeviceEntity getDevice() {
        return device;
    }

    public void setDevice(DeviceEntity device) {
        this.device = device;
    }

    public T getInfra() {
        return infra;
    }

    public void setInfra(T infra) {
        this.infra = infra;
    }

    public static void main(String[] args) {
        DeviceInfraDisplayEntity test = new DeviceInfraDisplayEntity();
        test.setCount(100);
        test.setDevice(new DeviceEntity());
        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setCode("AAAAAA");
        test.setInfra(contractEntity);
        int x = 0;
    }
}
