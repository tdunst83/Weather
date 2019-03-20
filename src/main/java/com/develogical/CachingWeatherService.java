package com.develogical;

import com.weather.Day;
import com.weather.Region;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CachingWeatherService implements WeatherService {

    private WeatherService weatherService;

    private Map<String,String> cityDayCache = new LinkedHashMap<String,String>();

    private int numberOfCachedEntries = 2;


    public CachingWeatherService(WeatherService delegate) {
        weatherService = delegate;
    }

    @Override
    public String getWeather(Region region, Day day) {

        String mapKey = region.toString() + day.toString();

        if (!cityDayCache.containsKey(mapKey)) {
            String weather = weatherService.getWeather(region, day);


            if(cityDayCache.size()>=numberOfCachedEntries){

                String mapKey2 = "";

                Iterator<Map.Entry<String,String>> entries = cityDayCache.entrySet().iterator();

                if (entries.hasNext()) {
                    Map.Entry<String, String> entry = entries.next();
                    mapKey2 = entry.getKey();
                }

                cityDayCache.remove(mapKey2);

            }

            cityDayCache.put(mapKey, weather);
        }

        return cityDayCache.get(mapKey);
    }

}
