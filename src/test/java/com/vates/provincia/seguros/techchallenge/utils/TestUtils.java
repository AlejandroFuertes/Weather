package com.vates.provincia.seguros.techchallenge.utils;

import com.vates.provincia.seguros.techchallenge.dto.*;
import com.vates.provincia.seguros.techchallenge.dto.response.CityResponse;
import com.vates.provincia.seguros.techchallenge.dto.response.WeatherCityResponse;
import com.vates.provincia.seguros.techchallenge.dto.response.WeatherResponse;
import com.vates.provincia.seguros.techchallenge.entity.CityEntity;
import com.vates.provincia.seguros.techchallenge.entity.WeatherEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class TestUtils {


    public static CityEntity buildCityEntity() {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setId(1L);
        cityEntity.setName("testCity");
        cityEntity.setCodeLocation("testCodeLocation");
        cityEntity.setCountry("testCountry");
        cityEntity.setCodeCountry("testCountry");
        return cityEntity;
    }
    public static WeatherEntity buildWeatherEntity() {

        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setCondition("testCondition");
        weatherEntity.setTemperatureValue("25.5");
        weatherEntity.setTemperatureUnit("C");
        weatherEntity.setWindSpeed("25.5");
        weatherEntity.setWindUnit("km/h");
        weatherEntity.setRelativeHumidity(1);
        weatherEntity.setLastUpdated(new Date());

        weatherEntity.setCity(buildCityEntity());
        return weatherEntity;
    }

    public static WeatherEntity buildWeatherEntityLastUpdatedExceeded() {

        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setCondition("testCondition");
        weatherEntity.setTemperatureValue("testTemperatureValue");
        weatherEntity.setTemperatureUnit("testTemperatureUnit");
        weatherEntity.setWindSpeed("testWindSpeed");
        weatherEntity.setWindUnit("testWindUnit");
        weatherEntity.setRelativeHumidity(1);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -5);
        Date fiveDaysAgo = calendar.getTime();
        weatherEntity.setLastUpdated(fiveDaysAgo);
        weatherEntity.setCity(buildCityEntity());
        return weatherEntity;
    }

    public static List<WeatherCurrentConditions> buildWeatherCurrentConditions() {
        List<WeatherCurrentConditions> weatherCurrentConditions = new ArrayList<>();
        WeatherCurrentConditions weatherCurrentCondition = new WeatherCurrentConditions();
        Temperature temperature = new Temperature();
        Wind wind = new Wind();
        Speed speed = new Speed();
        Metric metric = new Metric();
        Metric metricSpeed = new Metric();

        metric.setValue(25.5);
        metric.setUnit("C");
        temperature.setMetric(metric);
        metricSpeed.setValue(25.5);
        metricSpeed.setUnit("km/h");
        speed.setMetric(metricSpeed);
        wind.setSpeedResponse(speed);

        weatherCurrentCondition.setWeatherCondition("testCondition");
        weatherCurrentCondition.setTemperature(temperature);
        weatherCurrentCondition.setRelativeHumidity(1);
        weatherCurrentCondition.setWind(wind);

        weatherCurrentConditions.add(weatherCurrentCondition);
        return weatherCurrentConditions;
    }

    public static WeatherCityResponse buildWeatherCityResponse() {
        WeatherEntity weatherEntity = buildWeatherEntity();
        WeatherCityResponse weatherCityResponse = new WeatherCityResponse();

        CityResponse city = new CityResponse();
        WeatherResponse weather = new WeatherResponse();

        city.setName(weatherEntity.getCity().getName());
        city.setCodeLocation(weatherEntity.getCity().getCodeLocation());
        city.setCountry(weatherEntity.getCity().getCountry());
        city.setCodeCountry(weatherEntity.getCity().getCodeCountry());

        weather.setCondition(weatherEntity.getCondition());
        weather.setTemperatureValue(weatherEntity.getTemperatureValue());
        weather.setTemperatureUnit(weatherEntity.getTemperatureUnit());
        weather.setWindSpeed(weatherEntity.getWindSpeed());
        weather.setWindUnit(weatherEntity.getWindUnit());
        weather.setRelativeHumidity(weatherEntity.getRelativeHumidity());

        return new WeatherCityResponse(city, weather);
    }

    public static List<Location> buildLocations() {

        List<Location> locationList = new ArrayList<>();
        Location location = new Location();
        Country country = new Country("1", "testNameCountry");
        AdministrativeArea administrativeArea = new AdministrativeArea("1", "testNameAdministrativeArea");

        location.setLocationKey("1");
        location.setType("testSetType");
        location.setCountry(country);
        location.setAdministrativeArea(administrativeArea);
        location.setLocalizedName("testLocalizedName");

        locationList.add(location);
        return locationList;
    }

    public static CityEntity buildCityEntityByLocations() {
        List<Location> locationList = buildLocations();
        Location location = locationList.get(0);

        return CityEntity.builder()
                .id(1L)
                .name(location.getLocalizedName())
                .codeLocation(location.getLocationKey())
                .country(location.getCountry().getName())
                .codeCountry(location.getCountry().getId())
                .build();
    }
}
