package com.yash.CarPolling.entity.models;

import com.sun.jdi.event.StepEvent;
import com.yash.CarPolling.entity.enums.BuddyTag;

public class BuddiesDetail {
    private String emailId;
    private String name;
    private String contactNo;
    private BuddyTag buddyTag;

    public BuddiesDetail(String emailId, String name, String contactNo, BuddyTag buddyTag) {
        this.emailId = emailId;
        this.name = name;
        this.contactNo = contactNo;
        this.buddyTag = buddyTag;
    }

    public BuddiesDetail() {
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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public BuddyTag getBuddyTag() {
        return buddyTag;
    }

    public void setBuddyTag(BuddyTag buddyTag) {
        this.buddyTag = buddyTag;
    }
}
