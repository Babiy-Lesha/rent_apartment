package org.example.rent_apartment.model.dto.integration_dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"error"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentsDto {

    @JsonProperty(value = "town")
    private String town;

    @JsonProperty(value = "city")
    private String city;
}
