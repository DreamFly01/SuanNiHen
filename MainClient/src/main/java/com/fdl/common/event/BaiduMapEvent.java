package com.fdl.common.event;

/**
 * @author 陈自强
 * @date 2019/7/31
 */
public class BaiduMapEvent {
    private double latitude;
    private double longitude;

    public BaiduMapEvent() {
    }

    public BaiduMapEvent(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
