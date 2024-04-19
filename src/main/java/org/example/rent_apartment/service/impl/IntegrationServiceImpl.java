package org.example.rent_apartment.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.rent_apartment.exception.ApartmentException;
import org.example.rent_apartment.model.dto.GeoResponseDto;
import org.example.rent_apartment.model.dto.TestObject;
import org.example.rent_apartment.model.entity.IntegrationEntity;
import org.example.rent_apartment.repository.IntegrationInfoRepository;
import org.example.rent_apartment.service.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.example.rent_apartment.exception.response_status.CustomStatusResponse.*;

@Service
@RequiredArgsConstructor
public class IntegrationServiceImpl implements IntegrationService {

    private static final Logger log = LoggerFactory.getLogger(IntegrationServiceImpl.class);
    private final IntegrationInfoRepository integratorInfoRepository;
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
    public GeoResponseDto getJsonUserLocationInfo(String longitude, String latitude) {
        IntegrationEntity geo = integratorInfoRepository.findById("GEO").get();
        String urlGeo = geo.getUrl();
        String keyValue = Base64Service.decodeRentApp(geo.getKeyValue());
        try {
            log.info("IntegrationServiceImpl: getJsonUserLocationInfo -> geoCoderService");
            GeoResponseDto result = restTemplate.exchange(String.format(urlGeo, latitude, longitude, keyValue),
                    HttpMethod.GET,
                    new HttpEntity<>(null),
                    GeoResponseDto.class).getBody();
            log.info("IntegrationServiceImpl: getJsonUserLocationInfo <- geoCoderService");
            return result;
        } catch (RuntimeException ex) {
            log.error("IntegrationServiceImpl: getJsonUserLocationInfo = " + BAD_REQUEST_INPUT_LOCATION);
            throw new ApartmentException(BAD_REQUEST_INPUT_LOCATION, BAD_INPUT_LOCATION);
        }
    }
}
