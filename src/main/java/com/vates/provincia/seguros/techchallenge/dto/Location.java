package com.vates.provincia.seguros.techchallenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @JsonProperty("Key")
    private String locationKey;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Country")
    private Country country;
    @JsonProperty("AdministrativeArea")
    private AdministrativeArea administrativeArea;
    @JsonProperty("LocalizedName")
    private String localizedName;
}
