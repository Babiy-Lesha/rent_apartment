package org.example.rent_apartment.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"error"})
@Data
public class GeoResponseDto {

    @JsonProperty(value = "results")
    private List<ResultsDto> results;


}
