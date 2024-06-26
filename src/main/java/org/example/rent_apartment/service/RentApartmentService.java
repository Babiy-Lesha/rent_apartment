package org.example.rent_apartment.service;

import org.example.rent_apartment.model.dto.apartment_dto.RequestApartmentInfoDto;
import org.example.rent_apartment.model.dto.RequestBookingInfoDto;
import org.example.rent_apartment.model.dto.apartment_dto.InfoApartmentDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RentApartmentService {

    String addApartment(String token, InfoApartmentDto infoApartmentDto);

    String deleteAddressApartmentById(Long id);

    String addPhotoApp(String apartmentName, MultipartFile photo);

    List<InfoApartmentDto> showApartments(RequestApartmentInfoDto apartmentInfoDto);

    String bookingApartment(RequestBookingInfoDto requestBookingInfoDto);

}
