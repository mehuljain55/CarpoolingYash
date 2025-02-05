package com.yash.CarPolling.entity;

import jakarta.persistence.*;

@Entity
@Table(name="offices")
public class Office {

    @Id
    private String officeId;
    private String officeName;
    private String officeAddress;
    private String city;



    public Office() {
    }

    public Office(String officeId, String officeName, String officeAddress, String city) {
        this.officeId = officeId;
        this.officeName = officeName;
        this.officeAddress = officeAddress;
        this.city = city;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
