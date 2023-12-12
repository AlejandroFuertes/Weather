package com.vates.provincia.seguros.techchallenge.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class WeatherEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "condition")
    private String condition;
    @Column(name = "temperature_value")
    private String temperatureValue;
    @Column(name = "temperature_unit")
    private String temperatureUnit;
    @Column(name = "wind_speed")
    private String windSpeed;
    @Column(name = "wind_unit")
    private String windUnit;
    @Column(name = "relative_humidity")
    private Integer relativeHumidity;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated")
    private Date lastUpdated;
    @OneToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;
}
