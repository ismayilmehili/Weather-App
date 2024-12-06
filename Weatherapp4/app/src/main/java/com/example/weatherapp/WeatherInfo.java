package com.example.weatherapp;

public class WeatherInfo {

    private float temperature;
    private String weatherCondition;
    private float humidity; // Changed the type from String to float
    private String windSpeed;

    public WeatherInfo(float temperature, String weatherCondition, String humidity, String windSpeed) {
        this.temperature = temperature;
        this.weatherCondition = weatherCondition;
        this.humidity = Float.parseFloat(humidity);
        this.windSpeed = windSpeed;
    }

    public float getTemperature() {
        return temperature;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public float getHumidity() { // Changed the return type to float
        return humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }
}