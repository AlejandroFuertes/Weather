package com.vates.provincia.seguros.techchallenge.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherCityResponse {

    @JsonProperty("ciudad")
    private CityResponse city;
    @JsonProperty("clima")
    private WeatherResponse weather;
}
