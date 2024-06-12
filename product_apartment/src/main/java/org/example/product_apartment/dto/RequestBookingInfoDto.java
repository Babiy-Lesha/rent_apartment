package org.example.product_apartment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Информация для бронирования")
@Data
public class RequestBookingInfoDto {

    @Schema(name = "apartmentId", required = true, example = "8")
    private Long apartmentId;

    @Schema(name = "startDateBooking", required = true, example = "10.02.2024")
    private String startDateBooking;

    @Schema(name = "endDateBooking", required = true, example = "24.02.2024")
    private String endDateBooking;


}
