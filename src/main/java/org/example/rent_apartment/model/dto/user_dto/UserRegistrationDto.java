package org.example.rent_apartment.model.dto.user_dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String nickName;
    private String passwordUser;
    private String loginUser;
    private String parentCity;
}
