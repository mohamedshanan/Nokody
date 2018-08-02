package com.nokody.merchant.data.models;

import org.parceler.Parcel;

@Parcel
public class LoginResponse {

    private Integer id;
    private Integer userId;
    private Double balance;
    private Boolean isActive;
    private String openedDate;
    private String passportNumber;
    private String braceletNumber;
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(String openedDate) {
        this.openedDate = openedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getBraceletNumber() {
        return braceletNumber;
    }

    public void setBraceletNumber(String braceletNumber) {
        this.braceletNumber = braceletNumber;
    }
}
