package com.develogical;

import com.weather.Day;
import com.weather.Region;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertNotEquals;

public class CachingWeatherServiceTest {
    @Test
    public void delegatesToAnotherWeatherService() throws Exception {
        WeatherService delegate = mock(WeatherService.class);
        when(delegate.getWeather(Region.BIRMINGHAM, Day.FRIDAY)).thenReturn("Some weather");

        WeatherService weatherService = new CachingWeatherService(delegate);

        String weather = weatherService.getWeather(Region.BIRMINGHAM, Day.FRIDAY);

        assertThat(weather, is("Some weather"));
    }

    @Test
    public void doesNotDelegateToWeatherServiceOnSecondCall() {

        WeatherService delegate = mock(WeatherService.class);
        when(delegate.getWeather(Region.BIRMINGHAM, Day.FRIDAY)).thenReturn("Some weather");

        WeatherService weatherService = new CachingWeatherService(delegate);

        weatherService.getWeather(Region.BIRMINGHAM, Day.FRIDAY);
        verify(delegate).getWeather(Region.BIRMINGHAM, Day.FRIDAY);

        weatherService.getWeather(Region.BIRMINGHAM, Day.FRIDAY);
        verifyNoMoreInteractions(delegate);
    }

    @Test
    public void usesDelegateForTwoDifferentCities() {
        WeatherService delegate = mock(WeatherService.class);
        when(delegate.getWeather(Region.BIRMINGHAM, Day.FRIDAY)).thenReturn("Birmingham weather");
        when(delegate.getWeather(Region.LONDON, Day.MONDAY)).thenReturn("London weather");

        WeatherService weatherService = new CachingWeatherService(delegate);

        String birminghamWeather = weatherService.getWeather(Region.BIRMINGHAM, Day.FRIDAY);
        String londonWeather = weatherService.getWeather(Region.LONDON, Day.MONDAY);

        verify(delegate).getWeather(Region.BIRMINGHAM, Day.FRIDAY);
        verify(delegate).getWeather(Region.LONDON, Day.MONDAY);

        assertThat(birminghamWeather, is("Birmingham weather"));
        assertThat(londonWeather, is("London weather"));
    }

    @Test
    public void usesDelegateForThreeDifferentCities() {
        WeatherService delegate = mock(WeatherService.class);
        when(delegate.getWeather(Region.BIRMINGHAM, Day.FRIDAY)).thenReturn("Birmingham weather");
        when(delegate.getWeather(Region.LONDON, Day.MONDAY)).thenReturn("London weather");

        WeatherService weatherService = new CachingWeatherService(delegate);

        String birminghamWeather = weatherService.getWeather(Region.BIRMINGHAM, Day.FRIDAY);
        verify(delegate).getWeather(Region.BIRMINGHAM, Day.FRIDAY);

        String londonWeather = weatherService.getWeather(Region.LONDON, Day.MONDAY);
        verify(delegate).getWeather(Region.LONDON, Day.MONDAY);

        String birminghamWeather2 = weatherService.getWeather(Region.BIRMINGHAM, Day.FRIDAY);
        verifyNoMoreInteractions(delegate);

    }

    @Test
    public void usesDelegateForThreeDifferentCitiesOnDifferentDays() {
        WeatherService delegate = mock(WeatherService.class);
        when(delegate.getWeather(Region.BIRMINGHAM, Day.FRIDAY)).thenReturn("Birmingham Friday weather");
        when(delegate.getWeather(Region.LONDON, Day.MONDAY)).thenReturn("London Monday weather");
        when(delegate.getWeather(Region.LONDON, Day.WEDNESDAY)).thenReturn("London Wednesday weather");

        WeatherService weatherService = new CachingWeatherService(delegate);

        String birminghamWeather = weatherService.getWeather(Region.BIRMINGHAM, Day.FRIDAY);
        verify(delegate).getWeather(Region.BIRMINGHAM, Day.FRIDAY);

        String londonWeatherMon = weatherService.getWeather(Region.LONDON, Day.MONDAY);
        verify(delegate).getWeather(Region.LONDON, Day.MONDAY);

        String londonWeatherWed = weatherService.getWeather(Region.LONDON, Day.WEDNESDAY);
        verify(delegate).getWeather(Region.LONDON, Day.WEDNESDAY);
    }

    @Test
    public void reuseExistingDayCacheForCity() {
        WeatherService delegate = mock(WeatherService.class);
        when(delegate.getWeather(Region.BIRMINGHAM, Day.FRIDAY)).thenReturn("Birmingham Friday weather");
        when(delegate.getWeather(Region.LONDON, Day.MONDAY)).thenReturn("London Monday weather");
        when(delegate.getWeather(Region.LONDON, Day.WEDNESDAY)).thenReturn("London Wednesday weather");

        WeatherService weatherService = new CachingWeatherService(delegate);

        String birminghamWeather = weatherService.getWeather(Region.BIRMINGHAM, Day.FRIDAY);
        verify(delegate).getWeather(Region.BIRMINGHAM, Day.FRIDAY);

        String londonWeatherMon = weatherService.getWeather(Region.LONDON, Day.MONDAY);
        verify(delegate).getWeather(Region.LONDON, Day.MONDAY);

        String londonWeatherWed = weatherService.getWeather(Region.LONDON, Day.WEDNESDAY);
        verify(delegate).getWeather(Region.LONDON, Day.WEDNESDAY);

        String londonWeatherMon2 = weatherService.getWeather(Region.LONDON, Day.MONDAY);
        verifyNoMoreInteractions(delegate);
    }
}


