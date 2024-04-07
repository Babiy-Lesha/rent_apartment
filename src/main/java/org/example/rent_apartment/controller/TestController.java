package org.example.rent_apartment.controller;

import lombok.RequiredArgsConstructor;
import org.example.rent_apartment.model.dto.TestObject;
import org.example.rent_apartment.service.IntegrationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rent_apartment_test")
@RequiredArgsConstructor
public class TestController {

    private final IntegrationService integrationService;

    @GetMapping("/integration_to_get_product")
    public String getIntegrationWithProduct (@RequestParam String value) {
        return integrationService.getIntegrationTest(value);
    }

    @PostMapping("/integration_to_post_product")
    public String postIntegrationWithProduct (@RequestBody TestObject testObject) {
        return integrationService.postIntegrationTest(testObject);
    }
}
