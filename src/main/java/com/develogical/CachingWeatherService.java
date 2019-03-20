package com.develogical;

import com.weather.Day;
import com.weather.Region;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CachingWeatherService implements WeatherService {

    private WeatherService weatherService;

    private Map<String,String> cityDayCache = new LinkedHashMap<>();
    private Map<String,Date> cityDayDateCache = new LinkedHashMap<>();

    private int numberOfCachedEntries = 2;


    public CachingWeatherService(WeatherService delegate) {
        weatherService = delegate;
    }

    @Override
    public String getWeather(Region region, Day day) {

        // Clearing of the cache:

        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.SECOND, -20);
        Date currentDateTransformed = calendar.getTime();

        // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


        if(cityDayDateCache.size()>0){
            for( Map.Entry<String,Date> entry : cityDayDateCache.entrySet()){
                //System.out.println("Key: "+ entry.getKey());
                if(cityDayDateCache.get(entry.getKey()).before(currentDateTransformed)){
                    cityDayDateCache.remove(entry.getKey());
                    cityDayCache.remove(entry.getKey());
                }
            }
        }


        // Deriving the weather:
        String mapKey = region.toString() + day.toString();

        if (!cityDayCache.containsKey(mapKey)) {
            String weather = weatherService.getWeather(region, day);

            if(cityDayCache.size()>=numberOfCachedEntries){

                Iterator<Map.Entry<String,String>> entries = cityDayCache.entrySet().iterator();

                if (entries.hasNext()) {
                    Map.Entry<String, String> entry = entries.next();
                    cityDayCache.remove(entry.getKey());
                    cityDayDateCache.remove(entry.getKey());
                }

            }

            cityDayCache.put(mapKey, weather);
            cityDayDateCache.put(mapKey, currentDate);
        }
        else{
            String weather = cityDayCache.get(mapKey);
            cityDayCache.remove(mapKey);
            cityDayCache.put(mapKey,weather);
        }

        return cityDayCache.get(mapKey);
    }

}
