package org.example.rent_apartment.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.rent_apartment.exception.ApartmentException;
import org.example.rent_apartment.mapper.RentApartmentMapper;
import org.example.rent_apartment.model.dto.GeoResponseDto;
import org.example.rent_apartment.model.dto.RequestApartmentInfoDto;
import org.example.rent_apartment.model.dto.apartment_dto.InfoApartmentDto;
import org.example.rent_apartment.model.entity.InfoAddresEntity;
import org.example.rent_apartment.model.entity.InfoApartmentRoomEntity;
import org.example.rent_apartment.model.entity.IntegrationEntity;
import org.example.rent_apartment.model.entity.PhotoApartmentEntity;
import org.example.rent_apartment.repository.InfoAddressRepository;
import org.example.rent_apartment.repository.InfoApartmentRoomRepository;
import org.example.rent_apartment.repository.IntegrationInfoRepository;
import org.example.rent_apartment.repository.PhotoApartmentRepository;
import org.example.rent_apartment.service.IntegrationService;
import org.example.rent_apartment.service.RentApartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.example.rent_apartment.app_const.AppConst.*;
import static org.example.rent_apartment.exception.response_status.CustomStatusResponse.*;

@Service
@RequiredArgsConstructor
public class RentApartmentServiceImpl implements RentApartmentService {

    private static final Logger log = LoggerFactory.getLogger(RentApartmentServiceImpl.class);

    private final InfoAddressRepository addressRepository;
    private final InfoApartmentRoomRepository apartmentRoomRepository;
    private final PhotoApartmentRepository photoRepository;

    private final RentApartmentMapper rentApartmentMapper;
    private final IntegrationService integrationService;

    @Override
    public String addApartment(String token, InfoApartmentDto infoApartmentDto) {

        InfoAddresEntity apartmentName = addressRepository.getApartmentByName(infoApartmentDto.getNameApartment());
        if (!isNull(apartmentName)) {
            log.error("RentApartmentServiceImpl: addApartment = " + APARTMENT_EXISTS);
            throw new ApartmentException(APARTMENT_EXISTS, NOT_UNIQ_ADDRES);
        }

        InfoAddresEntity addressEntity = rentApartmentMapper.apartmentDtoToApartmentEntity(infoApartmentDto);
        InfoApartmentRoomEntity infoApartmentRoomEntity = rentApartmentMapper.roomDtoToRoomEntity(infoApartmentDto);

        addressEntity.setApartment(infoApartmentRoomEntity);
        addressRepository.save(addressEntity);
        log.info("RentApartmentServiceImpl: addApartment = апартамент добавлен в базу данных");
        return REGISTRATION_APARTMENT_SUCCESS;
    }

    public String deleteAddressApartmentById(Long id) {
        addressRepository.deleteById(id);
        log.info("RentApartmentServiceImpl: deleteAddressApartmentById = апартамент по " + id + " удален из базы данных");
        return APARTMENT_DELETE_SUCCESS;
    }

    @Override
    public String addPhotoApp(String apartmentName, MultipartFile photo) {

        InfoAddresEntity apartment = addressRepository.getApartmentByName(apartmentName);
        if (isNull(apartment)) {
            log.error("RentApartmentServiceImpl: addPhotoApp = " + APARTMENT_NO_EXISTS);
            throw new ApartmentException(APARTMENT_NO_EXISTS, NOT_UNIQ_ADDRES);
        }

        PhotoApartmentEntity photoEntity = new PhotoApartmentEntity();
        photoEntity.setPhotoName("фотография апартамента");
        String encodePhoto = Base64Service.encodeRentAppPhoto(photo);
        photoEntity.setEncodePhoto(encodePhoto);
        photoRepository.save(photoEntity);
        log.info("RentApartmentServiceImpl: addPhotoApp = фото добавлено в базу данных");
        return PHOTO_ADD_SUCCESS;
    }

    @Override
    public List<InfoApartmentDto> showApartments(RequestApartmentInfoDto apartmentInfoDto) {
        List<InfoApartmentRoomEntity> listApartments;
        if(!isNull(apartmentInfoDto.getApartmentId())) {
            listApartments = apartmentRoomRepository.findById(apartmentInfoDto.getApartmentId())
                    .map(Collections::singletonList)
                    .orElseGet(() -> showApartmentsByUserLocation(apartmentInfoDto));
        } else {
            listApartments = showApartmentsByUserLocation(apartmentInfoDto);
        }
        return rentApartmentMapperList(listApartments);
    }

    private List<InfoApartmentDto> rentApartmentMapperList (List<InfoApartmentRoomEntity> listApartments) {
        List<InfoApartmentDto> listApartmentDto = new ArrayList<>();
        for(InfoApartmentRoomEntity apartment : listApartments) {
            InfoApartmentDto infoApartmentDto = rentApartmentMapper.apartmentDtoToApartmentEntity(apartment.getAddress(), apartment);
            listApartmentDto.add(infoApartmentDto);
        }
        return listApartmentDto;
    }

    private List<InfoApartmentRoomEntity> showApartmentsByUserLocation(RequestApartmentInfoDto apartmentInfoDto) {
        checkInputLongitudeAndLatitude(apartmentInfoDto);

        String cityByUserLocation = showCityByUserLocation(apartmentInfoDto.getLongitude(), apartmentInfoDto.getLatitude());
        if (!cityByUserLocation.isEmpty()) {
            return addressRepository.getApartmentsByCity(cityByUserLocation).stream()
                    .map(InfoAddresEntity::getApartment)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private void checkInputLongitudeAndLatitude (RequestApartmentInfoDto apartmentInfoDto) {
        if (isNull(apartmentInfoDto.getLatitude()) || isNull(apartmentInfoDto.getLongitude()) || apartmentInfoDto.getLatitude().isEmpty() || apartmentInfoDto.getLongitude().isEmpty()) {
            log.error("RentApartmentServiceImpl: checkInputLongitudeAndLatitude = " + NOT_INPUT_LOCATION);
            throw new ApartmentException(NOT_INPUT_LOCATION, BAD_INPUT_LOCATION);
        }
    }

    private String showCityByUserLocation(String longitude, String latitude) {
        GeoResponseDto jsonUserLocationInfo = integrationService.getJsonUserLocationInfo(longitude, latitude);
        return getLocation(jsonUserLocationInfo);
    }

    private String getLocation (GeoResponseDto geoResponseDto) {
        String city = geoResponseDto.getResults().get(0).getComponentsDto().getCity();
        String town = geoResponseDto.getResults().get(0).getComponentsDto().getTown();
        if (isNull(city) && isNull(town)) {
            log.info("RentApartmentServiceImpl: getLocation = получили локацию и не city и не town");
            return "";
        }
        if (isNull(city)) {
            return town;
        }
        return city;
    }
}
