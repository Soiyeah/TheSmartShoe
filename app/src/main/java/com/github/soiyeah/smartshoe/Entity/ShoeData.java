package com.github.soiyeah.smartshoe.Entity;


import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

@IgnoreExtraProperties
public class ShoeData {

    @PropertyName("Latitude")
    private Double latitude;

    @PropertyName("Longitude")
    private Double longitude;

    @PropertyName("Pressure")
    private Long pressure;

    @PropertyName("Speed")
    private Long speed;

    @PropertyName("Steps")
    private Long steps;

    public ShoeData() {
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

    public Long getPressure() {
        return pressure;
    }

    public void setPressure(Long pressure) {
        this.pressure = pressure;
    }

    public Long getSpeed() {
        return speed;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

    public Long getSteps() {
        return steps;
    }

    public void setSteps(Long steps) {
        this.steps = steps;
    }
}
