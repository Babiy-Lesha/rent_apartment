package org.example.rent_apartment.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.example.rent_apartment.exception.ApartmentException;
import org.example.rent_apartment.model.dto.RequestBookingInfoDto;
import org.example.rent_apartment.model.dto.integration_dto.GeoResponseDto;
import org.example.rent_apartment.model.dto.integration_dto.TestObject;
import org.example.rent_apartment.model.entity.IntegrationEntity;
import org.example.rent_apartment.repository.IntegrationInfoRepository;
import org.example.rent_apartment.service.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.example.rent_apartment.exception.response_status.CustomStatusResponse.BAD_INPUT_LOCATION;
import static org.example.rent_apartment.exception.response_status.CustomStatusResponse.BAD_REQUEST_INPUT_LOCATION;

@Service
@RequiredArgsConstructor
public class IntegrationServiceImpl implements IntegrationService {

    private static final Logger log = LoggerFactory.getLogger(IntegrationServiceImpl.class);
    private final IntegrationInfoRepository integratorInfoRepository;

    HttpClient httpClient = HttpClients.createDefault();
    ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
    RestTemplate restTemplate = new RestTemplate(requestFactory);

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

    @Override
    public Integer getDiscountBooking(RequestBookingInfoDto requestBookingInfoDto) {
        String urlDiscountBooking = "http://localhost:9099/api/v1/discount";
        return restTemplate.exchange(urlDiscountBooking,
                HttpMethod.POST,
                new HttpEntity<>(requestBookingInfoDto),
                Integer.class).getBody();
    }
}
