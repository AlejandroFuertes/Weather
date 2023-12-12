package com.vates.provincia.seguros.techchallenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherCurrentConditions {

    @JsonProperty("WeatherText")
    private String weatherCondition;
    @JsonProperty("Temperature")
    private Temperature temperature;
    @JsonProperty("RelativeHumidity")
    private Integer relativeHumidity;
    @JsonProperty("Wind")
    private Wind wind;
}
