package com.yash.CarPolling.entity;

import com.yash.CarPolling.entity.enums.DocumentStatus;
import com.yash.CarPolling.entity.enums.UserRoles;
import com.yash.CarPolling.entity.enums.UserStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="user")
public class User {

    @Id
    private String emailId;
    private String name;
    private String mobileNo;
    private String password;
    private String licenceNo;
    @Enumerated(EnumType.STRING)
    private DocumentStatus licence;
    private String licencePath;


    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Bookings bookings;

    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Vechile> vechileList;

    private String officeId;

    public User(String emailId, String name, String mobileNo, String password, String licenceNo, DocumentStatus licence, String licencePath, Bookings bookings, UserRoles role, UserStatus status, List<Vechile> vechileList, String officeId) {
        this.emailId = emailId;
        this.name = name;
        this.mobileNo = mobileNo;
        this.password = password;
        this.licenceNo = licenceNo;
        this.licence = licence;
        this.licencePath = licencePath;
        this.bookings = bookings;
        this.role = role;
        this.status = status;
        this.vechileList = vechileList;
        this.officeId = officeId;
    }

    public User() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public List<Vechile> getVechileList() {
        return vechileList;
    }

    public void setVechileList(List<Vechile> vechileList) {
        this.vechileList = vechileList;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public DocumentStatus getLicence() {
        return licence;
    }

    public void setLicence(DocumentStatus licence) {
        this.licence = licence;
    }

    public Bookings getBookings() {
        return bookings;
    }

    public void setBookings(Bookings bookings) {
        this.bookings = bookings;
    }

    public String getLicencePath() {
        return licencePath;
    }

    public void setLicencePath(String licencePath) {
        this.licencePath = licencePath;
    }

    @Override
    public String toString() {
        return "User{" +
                "emailId='" + emailId + '\'' +
                ", name='" + name + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", password='" + password + '\'' +
                ", licenceNo='" + licenceNo + '\'' +
                ", licence=" + licence +
                ", licencePath='" + licencePath + '\'' +
                ", bookings=" + bookings +
                ", role=" + role +
                ", status=" + status +
                ", vechileList=" + vechileList +
                ", officeId='" + officeId + '\'' +
                '}';
    }
}
