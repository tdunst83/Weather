package com.develogical;

import com.weather.Day;
import com.weather.Forecaster;
import com.weather.Region;

public class Client {
    public static void main(String[] args) {

        ForecasterAdapter service = new ForecasterAdapter(new Forecaster());

        System.out.println("London weather: " + service.getWeather(Region.LONDON, Day.MONDAY));

        System.out.println("Edinburgh weather: " + service.getWeather(Region.EDINBURGH, Day.MONDAY));
    }
}
