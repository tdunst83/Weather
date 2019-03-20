package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

public class ForecasterAdapter implements WeatherService {

    private Forecaster forecaster;

    public ForecasterAdapter(Forecaster forecaster) {
        this.forecaster = forecaster;
    }

    public String getWeather(Region region, Day day) {
        Forecast forecast = forecaster.forecastFor(region, day);

        return forecast.summary() + " - temperature = " + forecast.temperature();
    }
}
