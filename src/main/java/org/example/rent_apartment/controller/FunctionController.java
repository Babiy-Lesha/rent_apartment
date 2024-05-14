package org.example.rent_apartment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.rent_apartment.model.dto.RequestApartmentInfoDto;
import org.example.rent_apartment.model.dto.RequestBookingInfoDto;
import org.example.rent_apartment.model.dto.apartment_dto.InfoApartmentDto;
import org.example.rent_apartment.service.AuthUserService;
import org.example.rent_apartment.service.RentApartmentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.example.rent_apartment.app_const.AppConst.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "FunctionController", description = "API для управления апартаментами")
public class FunctionController {

    private final RentApartmentService rentApartment;
    private final AuthUserService registrationService;

    @PostMapping(URL_ADD_NEW_APARTMENT)
    @Operation(summary = "добавление апартаментов")
    public String addNewApartment(@RequestHeader String token,
                                  @RequestBody InfoApartmentDto apartment) {
        registrationService.checkToken(token);
        return rentApartment.addApartment(token, apartment);
    }

    @PostMapping(URL_DELETE_ADDRESS_APARTMENT)
    @Operation(summary = "удаление апартамента по адресу")
    public String deleteAddressApartment(@RequestParam Long id) {
        return rentApartment.deleteAddressApartmentById(id);
    }

    @PostMapping(URL_ADD_PHOTO_APARTMENT)
    @Operation(summary = "добавление фотографии")
    public String addPhotoApp(@RequestParam String nameApartment, @RequestBody MultipartFile file) {
        return rentApartment.addPhotoApp(nameApartment, file);
    }

    @PostMapping(URL_FIND_INFO_APARTMENT)
    @Operation(summary = "предоставление пользователю доступных апартаментов по локации/id")
    public List<InfoApartmentDto> showApartments(@RequestBody RequestApartmentInfoDto apartmentInfoDto) {
        return rentApartment.showApartments(apartmentInfoDto);
    }

    @PostMapping("Бронирование")
    @Operation(summary = "бронирование")
    public String bookingApartment(@RequestBody RequestBookingInfoDto requestBookingDto) {
        return rentApartment.bookingApartment(requestBookingDto);
    }

}
