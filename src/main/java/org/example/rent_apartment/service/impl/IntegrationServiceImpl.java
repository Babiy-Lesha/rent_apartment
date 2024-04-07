package org.example.rent_apartment.service.impl;

import org.example.rent_apartment.model.dto.TestObject;
import org.example.rent_apartment.service.IntegrationService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IntegrationServiceImpl implements IntegrationService {

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getIntegrationTest(String value) {
        String url = "http://localhost:9111/product_api/test_get?value=%s";
        return restTemplate.exchange(String.format(url, value),
                HttpMethod.GET,
                new HttpEntity<>(null),
                String.class).getBody();
    }

    @Override
    public String postIntegrationTest(TestObject testObject) {
        String url = "http://localhost:9111/product_api/test_post";
        return restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity<>(testObject),
                String.class).getBody();
    }

    @Override
    public String getJsonUserLocationInfo(String urlGeo, String longitude, String latitude, String keyValue) {
        return restTemplate.exchange(String.format(urlGeo, latitude, longitude, keyValue),
                HttpMethod.GET,
                new HttpEntity<>(null),
                String.class).getBody();
    }
}
