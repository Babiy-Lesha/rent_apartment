package org.example.rent_apartment.model.dto.user_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация для регистрации полльзователя")
public class UserRegistrationDto {

    @Schema(name = "nickName", required = true, example = "Roma")
    private String nickName;

    @Schema(name = "passwordUser", required = true, example = "qwerty10")
    private String passwordUser;

    @Schema(name = "loginUser", required = true, example = "RomaLogin")
    private String loginUser;

    @Schema(name = "parentCity", required = true, example = "Yekaterinburg")
    private String parentCity;
}
