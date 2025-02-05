package com.yash.CarPolling.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yash.CarPolling.entity.enums.DocumentStatus;
import com.yash.CarPolling.entity.enums.VechileStatus;
import com.yash.CarPolling.entity.enums.VechileType;
import jakarta.persistence.*;

@Entity
@Table(name="vechile")
public class Vechile {

    @Id
    private String vechileNo;
    private String vechileName;
    private String rcPath;

    @ManyToOne
    @JoinColumn(name="user_email_id")
    @JsonBackReference
    private User user;
    @Enumerated(EnumType.STRING)
    private VechileType vechileType;
    @Enumerated(EnumType.STRING)
    private VechileStatus status;
    private  int max_capacity;
    private int  available_capacity;
    private DocumentStatus rc_status;


    public Vechile(String vechileNo, String rcPath, User user, VechileType vechileType, VechileStatus status, int max_capacity, int available_capacity) {
        this.vechileNo = vechileNo;
        this.rcPath = rcPath;
        this.user = user;
        this.vechileType = vechileType;
        this.status = status;
        this.max_capacity = max_capacity;
        this.available_capacity = available_capacity;
    }

    public Vechile() {
    }

    public String getVechileNo() {
        return vechileNo;
    }

    public void setVechileNo(String vechileNo) {
        this.vechileNo = vechileNo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VechileType getVechileType() {
        return vechileType;
    }

    public void setVechileType(VechileType vechileType) {
        this.vechileType = vechileType;
    }

    public String getRcPath() {
        return rcPath;
    }

    public void setRcPath(String rcPath) {
        this.rcPath = rcPath;
    }

    public VechileStatus getStatus() {
        return status;
    }

    public void setStatus(VechileStatus status) {
        this.status = status;
    }

    public int getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }

    public int getAvailable_capacity() {
        return available_capacity;
    }

    public void setAvailable_capacity(int available_capacity) {
        this.available_capacity = available_capacity;
    }

    public DocumentStatus getRc_status() {
        return rc_status;
    }

    public void setRc_status(DocumentStatus rc_status) {
        this.rc_status = rc_status;
    }

    public String getVechileName() {
        return vechileName;
    }

    public void setVechileName(String vechileName) {
        this.vechileName = vechileName;
    }
}
