package com.develogical;

import com.weather.Day;
import com.weather.Region;

public interface WeatherService {

    public String getWeather(Region region, Day day);

}
