package com.develogical;

import com.weather.Day;
import com.weather.Region;

import java.util.HashMap;
import java.util.Map;

public class CachingWeatherService implements WeatherService {

    private WeatherService weatherService;

    private Map<String,String> cityDayCache = new HashMap<String,String>();

    public CachingWeatherService(WeatherService delegate) {
        weatherService = delegate;
    }

    @Override
    public String getWeather(Region region, Day day) {

        String mapKey = region.toString() + day.toString();

        if (!cityDayCache.containsKey(mapKey)) {
            String weather = weatherService.getWeather(region, day);

            cityDayCache.put(mapKey, weather);
        }

        return cityDayCache.get(mapKey);
    }

}
