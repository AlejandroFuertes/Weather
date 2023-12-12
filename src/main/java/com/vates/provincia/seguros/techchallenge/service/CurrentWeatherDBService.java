package com.vates.provincia.seguros.techchallenge.service;

import com.vates.provincia.seguros.techchallenge.dto.response.WeatherCityResponse;

import java.util.List;

public interface CurrentWeatherDBService {

    List<WeatherCityResponse> currentForecastByCity(String city);
    WeatherCityResponse currentForecastByCityAndCountry(String city, String country);

}
