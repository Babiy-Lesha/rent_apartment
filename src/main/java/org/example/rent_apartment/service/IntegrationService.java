package org.example.rent_apartment.service;

import org.example.rent_apartment.model.dto.TestObject;

public interface IntegrationService {

    String getIntegrationTest(String value);

    String postIntegrationTest(TestObject testObject);

    String getJsonUserLocationInfo(String url, String longitude, String latitude, String keyValue);

}
