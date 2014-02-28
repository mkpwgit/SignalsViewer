package com.viewer.domain;

import java.util.Date;

/**
 * Created by mikalai on 2/28/14.
 */
public class Signal {

    private Long deviceId;
    private Date date;
    private Double latitude;
    private Double longitude;
    private Integer strength;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        return "Signal{" +
                "deviceId=" + deviceId +
                ", date=" + date +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", strength=" + strength +
                '}';
    }
}
