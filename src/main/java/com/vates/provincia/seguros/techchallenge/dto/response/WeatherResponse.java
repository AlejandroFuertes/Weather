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
public class WeatherResponse {

    @JsonProperty("condicion_meteorologica")
    private String condition;
    @JsonProperty("valor_temperatura")
    private String temperatureValue;
    @JsonProperty("unidad_medida_temperatura")
    private String temperatureUnit;
    @JsonProperty("velocidad_viento")
    private String windSpeed;
    @JsonProperty("unidad_medida_viento")
    private String windUnit;
    @JsonProperty("porcentaje_humedad")
    private Integer relativeHumidity;
}
