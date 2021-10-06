package com.mine.datamodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Mina Mimi on 10/3/2021.
 */
@Entity
@Table(name = "bbbg", schema = "infra_supply")
public class BbbgEntity {

    private Integer id;
    private String code;
    private String name;
    private String submitter;
    private Date approvedTime;
    private String attachFile;
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
    @Column(name = "SUBMITTER")
    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    @Basic
    @Column(name = "APPROVED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getApprovedTime() {
        return approvedTime;
    }

    public void setApprovedTime(Date approvedTime) {
        this.approvedTime = approvedTime;
    }

    @Basic
    @Column(name = "ATTACH_FILE")
    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
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

        BbbgEntity that = (BbbgEntity) o;

        if (id != that.id) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (submitter != null ? !submitter.equals(that.submitter) : that.submitter != null) return false;
        if (approvedTime != null ? !approvedTime.equals(that.approvedTime) : that.approvedTime != null) return false;
        if (attachFile != null ? !attachFile.equals(that.attachFile) : that.attachFile != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;

        return true;
    }
}
