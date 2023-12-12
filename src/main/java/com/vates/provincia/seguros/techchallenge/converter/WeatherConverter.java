package com.vates.provincia.seguros.techchallenge.converter;

import com.vates.provincia.seguros.techchallenge.dto.WeatherCurrentConditions;
import com.vates.provincia.seguros.techchallenge.dto.response.CityResponse;
import com.vates.provincia.seguros.techchallenge.dto.response.WeatherCityResponse;
import com.vates.provincia.seguros.techchallenge.dto.response.WeatherResponse;
import com.vates.provincia.seguros.techchallenge.entity.WeatherEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WeatherConverter {

    public WeatherEntity convertDtoToEntity(WeatherCurrentConditions dto) {
        return WeatherEntity.builder()
                .condition(dto.getWeatherCondition())
                .temperatureValue(String.valueOf(dto.getTemperature().getMetric().getValue()))
                .temperatureUnit(dto.getTemperature().getMetric().getUnit())
                .windSpeed(String.valueOf(dto.getWind().getSpeedResponse().getMetric().getValue()))
                .windUnit(dto.getWind().getSpeedResponse().getMetric().getUnit())
                .relativeHumidity(dto.getRelativeHumidity())
                .build();
    }

    public List<WeatherCityResponse> convertEntitiesToListResponse(List<WeatherEntity> entities) {

        return entities.stream()
                .map(entity -> {
                    CityResponse city = new CityResponse();
                    WeatherResponse weather = new WeatherResponse();

                    city.setName(entity.getCity().getName());
                    city.setCodeLocation(entity.getCity().getCodeLocation());
                    city.setCountry(entity.getCity().getCountry());
                    city.setCodeCountry(entity.getCity().getCodeCountry());

                    weather.setCondition(entity.getCondition());
                    weather.setTemperatureValue(entity.getTemperatureValue());
                    weather.setTemperatureUnit(entity.getTemperatureUnit());
                    weather.setWindSpeed(entity.getWindSpeed());
                    weather.setWindUnit(entity.getWindUnit());
                    weather.setRelativeHumidity(entity.getRelativeHumidity());

                    return new WeatherCityResponse(city, weather);
                }).collect(Collectors.toList());
    }

    public WeatherCityResponse convertEntityToResponse(WeatherEntity entity) {
        CityResponse city = new CityResponse();
        WeatherResponse weather = new WeatherResponse();

        city.setName(entity.getCity().getName());
        city.setCodeLocation(entity.getCity().getCodeLocation());
        city.setCountry(entity.getCity().getCountry());
        city.setCodeCountry(entity.getCity().getCodeCountry());

        weather.setCondition(entity.getCondition());
        weather.setTemperatureValue(entity.getTemperatureValue());
        weather.setTemperatureUnit(entity.getTemperatureUnit());
        weather.setWindSpeed(entity.getWindSpeed());
        weather.setWindUnit(entity.getWindUnit());
        weather.setRelativeHumidity(entity.getRelativeHumidity());

        return new WeatherCityResponse(city, weather);
    }
}
