package com.vates.provincia.seguros.techchallenge.service;

import com.vates.provincia.seguros.techchallenge.dto.Country;
import com.vates.provincia.seguros.techchallenge.dto.Location;
import com.vates.provincia.seguros.techchallenge.dto.Region;
import com.vates.provincia.seguros.techchallenge.dto.WeatherCurrentConditions;

import java.util.List;

public interface WeatherClientService {

    List<WeatherCurrentConditions> getCurrentConditions(String codeLocation);
    List<Country> getCountriesByCodeRegion(String codeRegion);

    List<Location> getCitiesByText(String text);

    List<Region> getAllRegions();
}
