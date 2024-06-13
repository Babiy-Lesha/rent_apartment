package org.example.rent_apartment.model.dto.apartment_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "Информация для показа доступных апартаментов пользователю")
@AllArgsConstructor
@NoArgsConstructor
public class RequestApartmentInfoDto {

    @Schema(name = "apartmentId", example = "15")
    private Long apartmentId;

    @Schema(name = "latitude", example = "56.860235")
    private String latitude;

    @Schema(name = "longitude", example = "60.638178")
    private String longitude;

    @Schema(name = "emailAddress", example = "babiilesha@gmail.com")
    private String emailAddress;

}
