package org.example.rent_apartment.controller;

import lombok.RequiredArgsConstructor;
import org.example.rent_apartment.model.dto.RequestApartmentInfoDto;
import org.example.rent_apartment.model.dto.apartment_dto.InfoApartmentDto;
import org.example.rent_apartment.service.RentApartmentService;
import org.example.rent_apartment.service.AuthUserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.example.rent_apartment.app_const.AppConst.*;

@RestController
@RequiredArgsConstructor
public class FunctionController {

    private final RentApartmentService rentApartment;
    private final AuthUserService registrationService;

    @PostMapping(URL_ADD_NEW_APARTMENT)
    public String addNewApartment(@RequestHeader String token,
                                  @RequestBody InfoApartmentDto apartment) {
        registrationService.checkToken(token);
        return rentApartment.addApartment(token, apartment);
    }

    @PostMapping(URL_DELETE_ADDRESS_APARTMENT)
    public String deleteAddressApartment(@RequestParam String nameApartment) {
        return rentApartment.deleteAddressApartmentByName(nameApartment);
    }

    @PostMapping(URL_ADD_PHOTO_APARTMENT)
    public String addPhotoApp (@RequestParam String nameApartment, @RequestBody MultipartFile file) {
        return rentApartment.addPhotoApp(nameApartment, file);
    }

    @PostMapping(URL_FIND_INFO_APARTMENT)
    public List<InfoApartmentDto> showApartments (@RequestBody RequestApartmentInfoDto apartmentInfoDto) {
        return rentApartment.showApartments(apartmentInfoDto);
    }



}
