package com.yash.CarPolling.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="offices")
public class Office {

    @Id
    private String officeId;
    private String officeName;
    private String officeAddress;

    public Office(String officeId, String officeName, String officeAddress) {
        this.officeId = officeId;
        this.officeName = officeName;
        this.officeAddress = officeAddress;
    }

    public Office() {
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }
}

