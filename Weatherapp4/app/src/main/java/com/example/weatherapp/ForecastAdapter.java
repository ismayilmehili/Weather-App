package com.example.weatherapp;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.WeatherForecast;

import java.util.List;
import java.util.Locale;

public class ForecastAdapter {

    private Context context;
    private List<WeatherForecast> forecastList;
    private TextView[] forecastDays = new TextView[5];
    private ImageView[] forecastIcons = new ImageView[5];
    private TextView[] forecastTemps = new TextView[5];

    public ForecastAdapter(Context context, List<WeatherForecast> forecastList,
                           TextView[] forecastDays, ImageView[] forecastIcons, TextView[] forecastTemps) {
        this.context = context;
        this.forecastList = forecastList;
        this.forecastDays = forecastDays;
        this.forecastIcons = forecastIcons;
        this.forecastTemps = forecastTemps;
    }

    public void updateForecastViews() {
        for (int i = 0; i < forecastList.size(); i++) {
            WeatherForecast forecast = forecastList.get(i);

            forecastDays[i].setText(forecast.getDate());
            forecastTemps[i].setText(String.format(Locale.getDefault(), "%.0f° - %.0f°", forecast.getTempMin(), forecast.getTempMax()));

            String icon = forecast.getIcon();
            int iconResource = context.getResources().getIdentifier(icon, "drawable", context.getPackageName());
            forecastIcons[i].setImageResource(iconResource);
        }
    }
}
