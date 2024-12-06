package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ForecastActivity extends AppCompatActivity {
    private List<WeatherForecast> forecastList = new ArrayList<>();

    private TextView[] forecastDays = new TextView[5];
    private ImageView[] forecastIcons = new ImageView[5];
    private TextView[] forecastTemps = new TextView[5];

    private static final String API_KEY = "f88b892bb85561e4d6771ba43b4176e9";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/forecast?lat=50.72&lon=10.42&appid=" + API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast_activity);

        forecastDays[0] = findViewById(R.id.id_forecastDay1);
        forecastDays[1] = findViewById(R.id.id_forecastDay2);
        forecastDays[2] = findViewById(R.id.id_forecastDay3);
        forecastDays[3] = findViewById(R.id.id_forecastDay4);
        forecastDays[4] = findViewById(R.id.id_forecastDay5);

        forecastIcons[0] = findViewById(R.id.id_forecastIcon1);
        forecastIcons[1] = findViewById(R.id.id_forecastIcon2);
        forecastIcons[2] = findViewById(R.id.id_forecastIcon3);
        forecastIcons[3] = findViewById(R.id.id_forecastIcon4);
        forecastIcons[4] = findViewById(R.id.id_forecastIcon5);

        forecastTemps[0] = findViewById(R.id.id_forecastTemp1);
        forecastTemps[1] = findViewById(R.id.id_forecastTemp2);
        forecastTemps[2] = findViewById(R.id.id_forecastTemp3);
        forecastTemps[3] = findViewById(R.id.id_forecastTemp4);
        forecastTemps[4] = findViewById(R.id.id_forecastTemp5);

        fetchWeatherForecast();

        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When the back button is clicked, go back to the LocationSearchActivity
                Intent intent = new Intent(ForecastActivity.this, LocationSearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchWeatherForecast() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("API Request", "Failed to make request: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d("API Response", responseData);
                    try {
                        JSONObject json = new JSONObject(responseData);
                        JSONArray forecasts = json.getJSONArray("list");

                        for (int i = 0; i < forecasts.length(); i += 8) {
                            JSONObject forecastData = forecasts.getJSONObject(i);
                            long timestamp = forecastData.getLong("dt");
                            JSONObject temp = forecastData.getJSONObject("main");

                            // Convert temperatures from Kelvin to Celsius
                            double tempMaxKelvin = temp.getDouble("temp_max");
                            double tempMinKelvin = temp.getDouble("temp_min");
                            double tempMaxCelsius = tempMaxKelvin - 273.15;
                            double tempMinCelsius = tempMinKelvin - 273.15;

                            JSONArray weatherArray = forecastData.getJSONArray("weather");
                            JSONObject weather = weatherArray.getJSONObject(0);
                            String description = weather.getString("description");
                            String icon = weather.getString("icon");

                            Date date = new Date(timestamp * 1000L);
                            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d", Locale.getDefault());
                            String formattedDate = sdf.format(date);

                            WeatherForecast weatherForecast = new WeatherForecast(formattedDate, description, tempMinCelsius, tempMaxCelsius, icon);
                            forecastList.add(weatherForecast);

                            int dayIndex = i / 8;
                            int tempMaxInt = (int) Math.round(tempMaxCelsius);
                            int tempMinInt = (int) Math.round(tempMinCelsius);

                            String tempRangeFormat = "%02d°C - %02d°C";

                            String tempRange = String.format(Locale.getDefault(), tempRangeFormat, tempMinInt, tempMaxInt);

                            runOnUiThread(new Runnable() {
                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                public void run() {
                                    forecastDays[dayIndex].setText(formattedDate);
                                    forecastIcons[dayIndex].setImageResource(getWeatherIconResourceId(icon));
                                    forecastTemps[dayIndex].setText(tempRange);
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("API Response", "Unsuccessful response: " + response.code());
                }
            }
        });
    }


    private int getWeatherIconResourceId(String icon) {
        switch (icon) {
            case "01d":
                return R.drawable.sunny;
            case "02d":
                return R.drawable.cloudy;
            case "03d":
            case "04d":
                return R.drawable.w04d;
            case "09d":
            case "10d":
                return R.drawable.lightrain;
            case "11d":
                return R.drawable.thunderstorm1;
            case "13d":
                return R.drawable.snow1;
            case "50d":
                return R.drawable.snow2;
            case "01n":
                return R.drawable.fog;
            case "02n":
                return R.drawable.shower;
            case "03n":
            case "04n":
                return R.drawable.thunderstorm2;
            case "09n":
            case "10n":
                return R.drawable.sunny1;

            default:
                return R.drawable.w03d;
        }
    }
}
