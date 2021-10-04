package com.mine.datamodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Mina Mimi on 10/3/2021.
 */
@Entity
@Table(name = "ha", schema = "infra_supply")
public class HaEntity {
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Integer id;
    private String code;
    private String name;
    private Integer infraType;
    private Integer supplyType;
    private String note;

    @Id
    @Column(name = "ID")
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
    @Column(name = "INFRA_TYPE")
    public Integer getInfraType() {
        return infraType;
    }

    public void setInfraType(Integer infraType) {
        this.infraType = infraType;
    }

    @Basic
    @Column(name = "SUPPLY_TYPE")
    public Integer getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(Integer supplyType) {
        this.supplyType = supplyType;
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

        HaEntity haEntity = (HaEntity) o;

        if (id != haEntity.id) return false;
        if (code != null ? !code.equals(haEntity.code) : haEntity.code != null) return false;
        if (name != null ? !name.equals(haEntity.name) : haEntity.name != null) return false;
        if (infraType != null ? !infraType.equals(haEntity.infraType) : haEntity.infraType != null) return false;
        if (supplyType != null ? !supplyType.equals(haEntity.supplyType) : haEntity.supplyType != null) return false;
        if (note != null ? !note.equals(haEntity.note) : haEntity.note != null) return false;

        return true;
    }

}
