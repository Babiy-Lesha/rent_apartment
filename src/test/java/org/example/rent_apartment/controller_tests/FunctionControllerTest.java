package org.example.rent_apartment.controller_tests;

import org.example.rent_apartment.model.dto.integration_dto.GeoResponseDto;
import org.example.rent_apartment.service.IntegrationService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.hamcrest.Matchers;

import static org.example.rent_apartment.app_const.AppConst.URL_FIND_INFO_APARTMENT;
import static org.example.rent_apartment.controller_tests.PrepareObjectControllerTest.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class FunctionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IntegrationService integrationService;

    private final String longitude = "test";
    private final String latitude = "test";

    @Test
    public void findApartmentByLocationPositive() throws Exception {
//        Mockito.when(integrationService.getJsonUserLocationInfo(Mockito.anyString(), Mockito.anyString())).thenReturn(getGeoResponseDto());
        Mockito.when(integrationService.getJsonUserLocationInfo(longitude, latitude)).thenReturn(getGeoResponseDto());


        GeoResponseDto mockResponse = integrationService.getJsonUserLocationInfo(longitude, latitude);
        System.out.println("Mock Response: " + mockResponse); // Debugging line

        mvc.perform(MockMvcRequestBuilders.post(URL_FIND_INFO_APARTMENT)
                .content(asJSONstring(getRequestApartmentInfoDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));


    }
}
