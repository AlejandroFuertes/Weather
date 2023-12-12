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
public class CityResponse {

    @JsonProperty("nombre_ciudad")
    private String name;
    @JsonProperty("codigo_ubicacion")
    private String codeLocation;
    @JsonProperty("pais")
    private String country;
    @JsonProperty("codigo_pais")
    private String codeCountry;
}
