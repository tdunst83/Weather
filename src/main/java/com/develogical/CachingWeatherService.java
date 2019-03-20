package com.develogical;

import com.weather.Day;
import com.weather.Region;

import java.util.HashMap;
import java.util.Map;

public class CachingWeatherService implements WeatherService {

    private WeatherService weatherService;

    private Map<Region,Map<Day,String>> cityDayCache = new HashMap<Region,Map<Day,String>>();

    public CachingWeatherService(WeatherService delegate) {
        weatherService = delegate;
    }

    @Override
    public String getWeather(Region region, Day day) {

        if (!cityDayCache.containsKey(region) || !cityDayCache.get(region).containsKey(day) ) {
            String weather = weatherService.getWeather(region, day);

            Map<Day,String> helpMap;
            if (cityDayCache.containsKey(region)){
                helpMap = cityDayCache.get(region);
            }else{
                helpMap = new HashMap<Day,String>();
            }

            helpMap.put(day,weather);

            cityDayCache.put(region,helpMap);
        }

        return cityDayCache.get(region).get(day);
    }

}
