package com.vates.provincia.seguros.techchallenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Region {

    @JsonProperty("ID")
    private String idRegion;
    @JsonProperty("LocalizedName")
    private String localizedName;
}
