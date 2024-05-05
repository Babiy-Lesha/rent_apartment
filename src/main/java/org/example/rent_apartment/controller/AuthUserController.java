package org.example.rent_apartment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.rent_apartment.model.dto.user_dto.UserAuthDto;
import org.example.rent_apartment.model.dto.user_dto.UserRegistrationDto;
import org.example.rent_apartment.service.AuthUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static org.example.rent_apartment.app_const.AppConst.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "AuthUserController", description = "API для управления пользователями")
public class AuthUserController {

    private final AuthUserService registrationService;

    @PostMapping(URL_REGISTRATION)
    @Operation(summary = "регистрация пользователя")
    public String registration(@RequestBody UserRegistrationDto user) {
        return registrationService.registration(user);
    }

    @PostMapping(URL_AUTH)
    @Operation(summary = "авторизация пользователя")
    public String authorization(@RequestBody UserAuthDto user) {
        return registrationService.auth(user);
    }

    @PostMapping(URL_AUTH_ADMIN)
    @Operation(summary = "авторизация пользователя админа")
    public String authorizationAdmin(@RequestBody UserAuthDto user) {
        return registrationService.authAdmin(user);
    }

    @PostMapping(URL_LOG_AUTH)
    @Operation(summary = "разлогинивание пользователя")
    public String logAuth(@RequestHeader String token) {
        return registrationService.logAuth(token);
    }
}
