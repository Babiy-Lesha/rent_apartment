package org.example.product_apartment.service;

import org.example.product_apartment.dto.RequestBookingInfoDto;
import org.springframework.stereotype.Service;

@Service
public class DiscountCalculationServiceImpl implements DiscountCalculationService{
    @Override
    public Integer discountCalculate(RequestBookingInfoDto requestBookingInfoDto) {
        Integer discount = requestBookingInfoDto.getApartmentId().intValue() + 10;
        return  discount;
    }
}
