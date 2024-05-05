package org.example.rent_apartment.model.dto.photo_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Информация для добавления фотографии")
public class PhotoDto {

    @Schema(name = "photoName", required = true, example = "фотография гостиной")
    private String photoName;
}
