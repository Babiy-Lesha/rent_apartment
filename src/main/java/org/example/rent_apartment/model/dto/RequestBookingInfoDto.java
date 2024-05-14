package org.example.rent_apartment.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Информация для бронирования")
@Data
public class RequestBookingInfoDto {
    @Schema(name = "apartmentId", required = true, example = "8")
    private Long apartmentId;

}
