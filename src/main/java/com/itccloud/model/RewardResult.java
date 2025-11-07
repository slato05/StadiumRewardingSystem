package com.itccloud.model;

import lombok.Data;

@Data
public class RewardResult {
    private String fanId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String occupation;
    private String standId;
    private String seatId;

    public RewardResult() {}

    public RewardResult(String fanId, String firstName, String lastName, String email, String phone,
                        String occupation, String preferredStand, String seatId) {
        this.fanId = fanId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phone;
        this.occupation = occupation;
        this.standId = preferredStand;
        this.seatId = seatId;
    }

    // ---- Getters & Setters ----

    public String getFanId() { return fanId; }
    public void setFanId(String fanId) { this.fanId = fanId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String fn) { this.firstName = fn; }

    public String getLastName() { return lastName; }
    public void setLastName(String ln) { this.lastName = ln; }

    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String p) { this.phoneNumber = p; }

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    public String getStandId() { return standId; }
    public void setStandId(String ps) { this.standId = ps; }

    public String getSeatId() { return seatId; }
    public void setSeatId(String s) { this.seatId = s; }
}
