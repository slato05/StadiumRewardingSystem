package com.itccloud.model;

import lombok.Data;

@Data
public class Fan {
    private Integer fanId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String occupation;

    public Integer getFanId() {
        return fanId;
    }

    public void setFanId(Integer fanId) {
        this.fanId = fanId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
