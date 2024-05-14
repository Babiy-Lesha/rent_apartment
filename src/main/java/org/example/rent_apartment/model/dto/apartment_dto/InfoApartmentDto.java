package org.example.rent_apartment.model.dto.apartment_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Информация по аппартаментам")
public class InfoApartmentDto {

    @Schema(name = "nameApartment", required = true, example = "Лучший апартамент")
    //required - является ли обязательным поле для заполнения
    private String nameApartment;

    @Schema(name = "cityApartment", required = true, example = "Yekaterinburg")
    private String cityApartment;

    @Schema(name = "streetApartment", required = true, example = "Vilonova")
    private String streetApartment;

    @Schema(name = "ratingRoom", required = true, example = "4.6")
    private String ratingRoom;

    @Schema(name = "countRoom", required = true, example = "3")
    private Integer countRoom;

    @Schema(name = "pricePerDay", required = true, example = "4600")
    private Integer pricePerDay;

    @Schema(name = "haveShowerRoom", example = "false")
    private Boolean haveShowerRoom;
}
