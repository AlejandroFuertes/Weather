package com.vates.provincia.seguros.techchallenge.service.impl;

import com.vates.provincia.seguros.techchallenge.client.AccuWeatherClient;
import com.vates.provincia.seguros.techchallenge.dto.Country;
import com.vates.provincia.seguros.techchallenge.dto.Location;
import com.vates.provincia.seguros.techchallenge.dto.Region;
import com.vates.provincia.seguros.techchallenge.dto.WeatherCurrentConditions;
import com.vates.provincia.seguros.techchallenge.service.WeatherClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WeatherClientServiceImpl implements WeatherClientService {

    private static final String LANGUAGE = "es-ES";
    private final AccuWeatherClient accuWeatherClient;

    @Value("${accuweather.api.key}")
    private String apiKey;

    @Autowired
    public WeatherClientServiceImpl(AccuWeatherClient accuWeatherClient) {
        this.accuWeatherClient = accuWeatherClient;
    }

    @Override
    public List<WeatherCurrentConditions> getCurrentConditions(String codeLocation) {
        boolean moreDetails = true;
        return accuWeatherClient
                .getCurrentConditionsByLocation(codeLocation, apiKey, LANGUAGE, moreDetails);

    }

    @Override
    public List<Country> getCountriesByCodeRegion(String codeRegion) {
        return accuWeatherClient.getCountriesByCodeRegion(codeRegion, apiKey, LANGUAGE);
    }

    @Override
    public List<Location> getCitiesByText(String text) {
        return accuWeatherClient.getIdLocationsByText(apiKey, text, LANGUAGE);
    }

    public List<Region> getAllRegions() {
        return accuWeatherClient.getAllRegions(apiKey, LANGUAGE);
    }
}
