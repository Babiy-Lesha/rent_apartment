package org.example.rent_apartment.model.dto.integration_dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"error"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoResponseDto {

    @JsonProperty(value = "results")
    private List<ResultsDto> results;


}
