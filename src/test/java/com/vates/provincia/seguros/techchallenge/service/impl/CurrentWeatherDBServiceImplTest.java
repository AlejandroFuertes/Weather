package com.vates.provincia.seguros.techchallenge.service.impl;

import com.vates.provincia.seguros.techchallenge.converter.CityConverter;
import com.vates.provincia.seguros.techchallenge.converter.WeatherConverter;
import com.vates.provincia.seguros.techchallenge.dto.Location;
import com.vates.provincia.seguros.techchallenge.dto.WeatherCurrentConditions;
import com.vates.provincia.seguros.techchallenge.dto.response.WeatherCityResponse;
import com.vates.provincia.seguros.techchallenge.entity.CityEntity;
import com.vates.provincia.seguros.techchallenge.entity.WeatherEntity;
import com.vates.provincia.seguros.techchallenge.exception.CityNotFoundException;
import com.vates.provincia.seguros.techchallenge.exception.CountryNotFoundException;
import com.vates.provincia.seguros.techchallenge.repository.CityRepository;
import com.vates.provincia.seguros.techchallenge.repository.WeatherRepository;
import com.vates.provincia.seguros.techchallenge.service.LoadDBService;
import com.vates.provincia.seguros.techchallenge.service.WeatherClientService;
import com.vates.provincia.seguros.techchallenge.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class CurrentWeatherDBServiceImplTest {

    @Mock
    WeatherClientService weatherClientService;
    @Mock
    CityConverter cityConverter;
    @Mock
    LoadDBService loadDBService;
    @Mock
    WeatherConverter weatherConverter;
    @Mock
    CityRepository cityRepository;
    @Mock
    WeatherRepository weatherRepository;

    WeatherEntity weatherEntity;
    WeatherEntity weatherEntityLastUpdatedExceeded;
    List<WeatherCurrentConditions> weatherCurrentConditions;
    WeatherCityResponse weatherCityResponse;
    List<Location> locations;
    CityEntity cityEntityByLocations;
    @InjectMocks
    CurrentWeatherDBServiceImpl currentWeatherDBService;

    @BeforeEach
    void initMocks() {
        weatherEntity = TestUtils.buildWeatherEntity();
        weatherEntityLastUpdatedExceeded = TestUtils.buildWeatherEntityLastUpdatedExceeded();
        weatherCurrentConditions = TestUtils.buildWeatherCurrentConditions();
        weatherCityResponse = TestUtils.buildWeatherCityResponse();
        locations = TestUtils.buildLocations();
        cityEntityByLocations = TestUtils.buildCityEntityByLocations();
    }

    @Test
    void currentForecastByCityWhenCityExist() {
        String testCity = "testCity";
        Mockito.when(cityRepository.existsByNameContainingIgnoreCase(anyString())).thenReturn(Boolean.TRUE);
        currentWeatherDBService.currentForecastByCity(testCity);
        Mockito.verify(cityRepository, Mockito.times(1)).existsByNameContainingIgnoreCase(testCity);
    }

    @Test
    void currentForecastByCityWhenCityNotExist() {
        String testCity = "testCity";
        Mockito.when(cityRepository.existsByNameContainingIgnoreCase(testCity)).thenReturn(false);
        Assertions.assertThrows(CityNotFoundException.class, () -> {
            currentWeatherDBService.currentForecastByCity(testCity);
        });
        Mockito.verify(cityRepository, Mockito.times(1)).existsByNameContainingIgnoreCase(testCity);
    }
    @Test
    void currentForecastByCityAndCountryWhenCityExist() {
        String testCity = "testCity";
        String testCountry = "testCountry";
        Mockito.when(cityRepository.existsByNameContainingIgnoreCaseAndCountryContainingIgnoreCase(anyString(), anyString())).thenReturn(Boolean.TRUE);
        Mockito.when(weatherRepository.findByCityNameContainingIgnoreCaseAndCity_CountryContainingIgnoreCase(anyString(),anyString())).thenReturn(weatherEntity);
        currentWeatherDBService.currentForecastByCityAndCountry(testCity, testCountry);
        Mockito.verify(cityRepository, Mockito.times(1)).existsByNameContainingIgnoreCaseAndCountryContainingIgnoreCase(testCity, testCountry);
    }

    @Test
    void currentForecastByCityAndCountryWhenCityExistButLastUpdatedExceeded() {
        String testCity = "testCity";
        String testCountry = "testCountry";
        Mockito.when(cityRepository.existsByNameContainingIgnoreCaseAndCountryContainingIgnoreCase(anyString(), anyString())).thenReturn(Boolean.TRUE);
        Mockito.when(weatherRepository.findByCityNameContainingIgnoreCaseAndCity_CountryContainingIgnoreCase(anyString(),anyString())).thenReturn(weatherEntityLastUpdatedExceeded);
        Mockito.when(weatherClientService.getCurrentConditions(anyString())).thenReturn(weatherCurrentConditions);
        currentWeatherDBService.currentForecastByCityAndCountry(testCity, testCountry);
        Mockito.verify(cityRepository, Mockito.times(1)).existsByNameContainingIgnoreCaseAndCountryContainingIgnoreCase(testCity, testCountry);
    }

    @Test
    void currentForecastByCityAndCountryWhenCityNotExistInDBAndNotExistCityByCountry() {
        String testCity = "testCity";
        String testCountry = "testCountry";
        Mockito.when(cityRepository.existsByNameContainingIgnoreCaseAndCountryContainingIgnoreCase(anyString(), anyString())).thenReturn(Boolean.FALSE);
        Mockito.when(weatherClientService.getCitiesByText(anyString())).thenReturn(locations);
        Mockito.when(cityConverter.convertLocationToCityEntity(any())).thenReturn(cityEntityByLocations);

        Assertions.assertThrows(CountryNotFoundException.class, () -> {
            currentWeatherDBService.currentForecastByCityAndCountry(testCity, testCountry);
        });

        Mockito.verify(cityRepository, Mockito.times(1)).existsByNameContainingIgnoreCaseAndCountryContainingIgnoreCase(testCity, testCountry);
    }
}