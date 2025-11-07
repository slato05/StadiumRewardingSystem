package com.itccloud.model;

import lombok.Data;

@Data
public class Reward {
    private int rewardId;
    private int fanId;
    private String seatId;

    public Reward(int rewardId, int fanId, String seatId) {
        this.rewardId = rewardId;
        this.fanId = fanId;
        this.seatId = seatId;
    }

    public int getRewardId() {
        return rewardId;
    }

    public void setRewardId(int rewardId) {
        this.rewardId = rewardId;
    }

    public int getFanId() {
        return fanId;
    }

    public void setFanId(int fanId) {
        this.fanId = fanId;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }
}
