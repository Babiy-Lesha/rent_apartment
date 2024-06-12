package org.example.product_apartment.service;

import org.example.product_apartment.dto.RequestBookingInfoDto;

public interface DiscountCalculationService {

    Integer discountCalculate(RequestBookingInfoDto requestBookingInfoDto);
}
