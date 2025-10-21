package com.itccloud.model;

import java.math.BigDecimal;

public class PreferenceSummary {
    private String standId;
    private String standName;
    private int availableSeats;
    private int numberOfPreferredSeats;
    private BigDecimal discountPrice;
    private BigDecimal estimatedTotalEarnings;

    public String getStandId() {
        return standId;
    }

    public void setStandId(String standId) {
        this.standId = standId;
    }

    public String getStandName() {
        return standName;
    }

    public void setStandName(String standName) {
        this.standName = standName;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getNumberOfPreferredSeats() {
        return numberOfPreferredSeats;
    }

    public void setNumberOfPreferredSeats(int numberOfPreferredSeats) {
        this.numberOfPreferredSeats = numberOfPreferredSeats;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getEstimatedTotalEarnings() {
        return estimatedTotalEarnings;
    }

    public void setEstimatedTotalEarnings(BigDecimal estimatedTotalEarnings) {
        this.estimatedTotalEarnings = estimatedTotalEarnings;
    }

}

