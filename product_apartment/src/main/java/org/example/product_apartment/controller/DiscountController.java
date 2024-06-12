package org.example.product_apartment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.product_apartment.dto.RequestBookingInfoDto;
import org.example.product_apartment.kafka_consumer.TopicConsumerListener;
import org.example.product_apartment.service.DiscountCalculationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "DiscountController", description = "API для управления скидками при бронировании")
public class DiscountController {

    private final DiscountCalculationService discountService;
    private final TopicConsumerListener topicConsumerListener;

    @PostMapping("/api/v1/discount")
    @Operation(summary = "рассчет скидки")
    public Integer discountCalculate(@RequestBody RequestBookingInfoDto requestBookingInfoDto) {
        return discountService.discountCalculate(requestBookingInfoDto);
    }

    @GetMapping("/api/getMassage")
    public List<String> getTopicMessage() {
        List<String> massagers = topicConsumerListener.getMassagers();
        return massagers;
    }
}
