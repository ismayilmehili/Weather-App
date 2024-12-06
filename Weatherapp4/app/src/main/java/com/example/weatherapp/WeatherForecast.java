package com.example.weatherapp;

public class WeatherForecast {
    private String date;
    private String description;
    private double tempMin;
    private double tempMax;
    private String icon;

    public WeatherForecast(String date, String description, double tempMin, double tempMax, String icon) {
        this.date = date;
        this.description = description;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
