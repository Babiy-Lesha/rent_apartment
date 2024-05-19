package org.example.rent_apartment.model.dto.integration_dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.rent_apartment.model.dto.integration_dto.ComponentsDto;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"error"})
@Data
public class ResultsDto {

    @JsonProperty(value = "components")
    private ComponentsDto componentsDto;

}
