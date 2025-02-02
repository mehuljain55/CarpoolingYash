package com.yash.CarPolling.entity;

import com.yash.CarPolling.entity.enums.VechileStatus;
import com.yash.CarPolling.entity.enums.VechileType;
import jakarta.persistence.*;

@Entity
@Table(name="vechile")
public class Vechile {

    @Id
    private String vechineNo;
    private String rcPath;

    @ManyToOne
    @JoinColumn(name="user_email_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private VechileType vechileType;
    @Enumerated(EnumType.STRING)
    private VechileStatus status;
    private  int capacity;

    public Vechile(String vechineNo, String rcPath, User user, VechileType vechileType, VechileStatus status, int capacity) {
        this.vechineNo = vechineNo;
        this.rcPath = rcPath;
        this.user = user;
        this.vechileType = vechileType;
        this.status = status;
        this.capacity = capacity;
    }

    public Vechile() {
    }

    public String getVechineNo() {
        return vechineNo;
    }

    public void setVechineNo(String vechineNo) {
        this.vechineNo = vechineNo;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
