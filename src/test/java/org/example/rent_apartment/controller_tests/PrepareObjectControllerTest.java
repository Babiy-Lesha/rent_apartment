package org.example.rent_apartment.controller_tests;

import org.example.rent_apartment.model.dto.apartment_dto.RequestApartmentInfoDto;
import org.example.rent_apartment.model.dto.integration_dto.ComponentsDto;
import org.example.rent_apartment.model.dto.integration_dto.GeoResponseDto;
import org.example.rent_apartment.model.dto.integration_dto.ResultsDto;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class PrepareObjectControllerTest {

    public static RequestApartmentInfoDto getRequestApartmentInfoDto() {
        return new RequestApartmentInfoDto(null, "test", "test", "email");
    }

    public static GeoResponseDto getGeoResponseDto() {
        List<ResultsDto> dtoList = new ArrayList<>();
        ResultsDto resultsDto = new ResultsDto(new ComponentsDto(null, "Yekaterinburg"));
        dtoList.add(resultsDto);
        return new GeoResponseDto(dtoList);
    }

    public static String asJSONstring(final Object o) {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


}
