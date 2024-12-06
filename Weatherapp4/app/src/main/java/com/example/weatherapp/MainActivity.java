package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String API_KEY = "f88b892bb85561e4d6771ba43b4176e9";

    private WeatherService service;
    private EditText cityEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(WeatherService.class);

        // Initialize cityEditText
        cityEditText = findViewById(R.id.cityEditText);

        // Initialize searchButton and set click listener
        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = cityEditText.getText().toString().trim();
                if (!cityName.isEmpty()) {
                    getWeatherData(cityName);
                }
            }
        });

        // Initialize backButton and set click listener
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This will close the current activity and go back to the previous one
            }
        });

        // Default search
        getWeatherData("Schmalkalden");
    }

    private void getWeatherData(String cityName) {
        Call<WeatherResponse> call = service.getWeather(cityName, API_KEY, "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        WeatherInfo weatherInfo = extractWeatherInfo(weatherResponse);
                        updateUI(weatherInfo);
                    }
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private WeatherInfo extractWeatherInfo(WeatherResponse weatherResponse) {
        float temperature = weatherResponse.getMain().getTemperature();
        String weatherCondition = weatherResponse.getWeather().get(0).getDescription();
        String humidity = String.valueOf(weatherResponse.getMain().getHumidity());
        String windSpeed = String.valueOf(weatherResponse.getWind().getSpeed());

        return new WeatherInfo(temperature, weatherCondition, humidity, windSpeed);
    }

    private void updateUI(WeatherInfo weatherInfo) {
        TextView temperatureTextView = findViewById(R.id.temperatureTextView);
        temperatureTextView.setText(String.format("%.1f °C", weatherInfo.getTemperature()));

        TextView weatherConditionTextView = findViewById(R.id.weatherConditionTextView);
        weatherConditionTextView.setText("Weather Condition: " + weatherInfo.getWeatherCondition());

        TextView humidityTextView = findViewById(R.id.humidityTextView);
        humidityTextView.setText("Humidity: " + weatherInfo.getHumidity());

        TextView windSpeedTextView = findViewById(R.id.windSpeedTextView);
        windSpeedTextView.setText("Wind Speed: " + weatherInfo.getWindSpeed());
    }
}
