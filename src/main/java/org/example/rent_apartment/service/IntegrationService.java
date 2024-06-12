package org.example.rent_apartment.service;

import org.example.rent_apartment.model.dto.RequestBookingInfoDto;
import org.example.rent_apartment.model.dto.integration_dto.GeoResponseDto;
import org.example.rent_apartment.model.dto.integration_dto.TestObject;

public interface IntegrationService {

    String getIntegrationTest(String value);

    String postIntegrationTest(TestObject testObject);

    GeoResponseDto getJsonUserLocationInfo(String longitude, String latitude);

    Integer getDiscountBooking(RequestBookingInfoDto requestBookingInfoDto);

}
