package org.example.rent_apartment.model.dto.apartment_dto;

import lombok.Data;

@Data
public class InfoApartmentDto {
    private String nameApartment;
    private String cityApartment;
    private String streetApartment;
    private String ratingRoom;
    private Integer countRoom;
    private Integer pricePerDay;
    private Boolean haveShowerRoom;
}
