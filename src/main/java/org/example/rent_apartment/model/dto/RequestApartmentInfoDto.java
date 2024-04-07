package org.example.rent_apartment.model.dto;

import lombok.Data;

@Data
public class RequestApartmentInfoDto {

    private Long apartmentId;
    private String latitude;
    private String longitude;

}
