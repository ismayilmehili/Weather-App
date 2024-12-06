package com.example.weatherapp;

import android.location.Location;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {

    @SerializedName("main")
    private Main main;

    @SerializedName("weather") // Added this line
    private List<Weather> weather; // Added this line

    @SerializedName("wind") // Added this line
    private Wind wind; // Added this line

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() { // Changed this line
        return weather; // Changed this line
    }

    public Wind getWind() { // Changed this line
        return wind; // Changed this line
    }

    public class Main {

        @SerializedName("temp")
        private float temperature;

        @SerializedName("humidity") // Added this line
        private float humidity; // Added this line

        public float getTemperature() {
            return temperature;
        }

        public float getHumidity() { // Changed this line
            return humidity; // Changed this line
        }
    }

    public class Weather { // Added this inner class

        @SerializedName("description")
        private String description;

        public String getDescription() {
            return description;
        }
    }

    public class Wind { // Added this inner class

        @SerializedName("speed")
        private float speed;

        public float getSpeed() {
            return speed;
        }
    }
}
