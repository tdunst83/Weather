package com.develogical;

import com.weather.Day;
import com.weather.Forecaster;
import com.weather.Region;

public class Client {
    public static void main(String[] args) {


        // Using the ForecasterAdapter:
        ForecasterAdapter service = new ForecasterAdapter(new Forecaster());

        System.out.println("London weather MON: " + service.getWeather(Region.LONDON, Day.MONDAY));

        System.out.println("Edinburgh weather MON: " + service.getWeather(Region.EDINBURGH, Day.MONDAY));


        // Using the CachingWeatherService:
        CachingWeatherService cachingWeatherService = new CachingWeatherService(service);

        System.out.println("London weather MON: " + cachingWeatherService.getWeather(Region.LONDON, Day.MONDAY));

        System.out.println("Birmingham weather WED: " + cachingWeatherService.getWeather(Region.BIRMINGHAM, Day.WEDNESDAY));

        System.out.println("Birmingham weather WED: " + cachingWeatherService.getWeather(Region.BIRMINGHAM, Day.WEDNESDAY));

        System.out.println("Edinburgh weather TUE: " + cachingWeatherService.getWeather(Region.EDINBURGH, Day.TUESDAY));

        System.out.println("Birmingham weather WED: " + cachingWeatherService.getWeather(Region.BIRMINGHAM, Day.WEDNESDAY));

        System.out.println("Edinburgh weather MON: " + cachingWeatherService.getWeather(Region.EDINBURGH, Day.MONDAY));

        System.out.println("Birmingham weather WED: " + cachingWeatherService.getWeather(Region.BIRMINGHAM, Day.WEDNESDAY));
    }
}
