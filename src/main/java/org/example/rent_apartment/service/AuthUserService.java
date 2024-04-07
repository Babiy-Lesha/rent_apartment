package org.example.rent_apartment.service;

import org.example.rent_apartment.model.dto.user_dto.UserAuthDto;
import org.example.rent_apartment.model.dto.user_dto.UserRegistrationDto;

public interface AuthUserService {

    String registration(UserRegistrationDto userRegistrationDto);

    String auth(UserAuthDto userAuthDto);

    String logAuth(String token);

    void checkToken (String token);

    String authAdmin(UserAuthDto userAuthDto);
}
