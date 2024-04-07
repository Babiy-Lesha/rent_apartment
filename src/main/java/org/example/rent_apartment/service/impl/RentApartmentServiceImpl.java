package org.example.rent_apartment.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.example.rent_apartment.exception.ApartmentException;
import org.example.rent_apartment.mapper.RentApartmentMapper;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.example.rent_apartment.app_const.AppConst.*;
import static org.example.rent_apartment.exception.response_status.CustomStatusResponse.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class RentApartmentServiceImpl implements RentApartmentService {

    private final InfoAddressRepository addressRepository;
    private final RentApartmentMapper rentApartmentMapper;
    private final InfoApartmentRoomRepository apartmentRoomRepository;

    private final PhotoApartmentRepository photoRepository;
    private final IntegrationInfoRepository integratorInfoRepository;
    private final IntegrationService integrationService;
    RestTemplate restTemplate = new RestTemplate();
    //    private final ApartmentMapper apartmentMapper;

    @Override
    public String addApartment(String token, InfoApartmentDto infoApartmentDto) {

        InfoAddresEntity apartmentName = addressRepository.getApartmentByName(infoApartmentDto.getNameApartment());
        if (!isNull(apartmentName)) {
            throw new ApartmentException(APARTMENT_EXISTS, NOT_UNIQ_ADDRES);
        }

//        InfoApartmentEntity addressEntity = apartmentMapper.apartmentDtoToApartmentEntity(infoApartmentDto);
        InfoAddresEntity addressEntity = rentApartmentMapper.apartmentDtoToApartmentEntity(infoApartmentDto);
        InfoApartmentRoomEntity infoApartmentRoomEntity = rentApartmentMapper.roomDtoToRoomEntity(infoApartmentDto);

        addressEntity.setApartment(infoApartmentRoomEntity);
        addressRepository.save(addressEntity);

        return REGISTRATION_APARTMENT_SUCCESS;
    }

    @Override
    public String deleteAddressApartmentByName(String nameApartment) {
        addressRepository.deleteByNameApartment(nameApartment);
        return "апартамент удален";
    }

    @Override
    public String addPhotoApp(String apartmentName, MultipartFile photo) {

        InfoAddresEntity apartment = addressRepository.getApartmentByName(apartmentName);
        if (isNull(apartment)) {
            throw new ApartmentException(APARTMENT_NO_EXISTS, NOT_UNIQ_ADDRES);
        }

        PhotoApartmentEntity photoEntity = new PhotoApartmentEntity();
        photoEntity.setPhotoName("фотография апартамента");
        String codePhoto = Base64Service.encodeRentAppPhoto(photo);
        photoEntity.setEncodePhoto(codePhoto);
        photoRepository.save(photoEntity);

        return PHOTO_ADD_SUCCESS;
    }

    @Override
    public List<InfoApartmentDto> showApartments(RequestApartmentInfoDto apartmentInfoDto) {

        /**
         * если передаются только широта и долгота, то выгружаем лист апаратментов
         */
        if(!isNull(apartmentInfoDto.getApartmentId())) {
            List<InfoApartmentDto> listApartmentDto = new ArrayList<>();
            List<InfoApartmentRoomEntity> listApartments = apartmentRoomRepository.findById(apartmentInfoDto.getApartmentId())
                    .map(Collections::singletonList)
                    .orElseGet(() -> showApartmentsByUserLocation(apartmentInfoDto));

            for(InfoApartmentRoomEntity apartment : listApartments) {
                InfoApartmentDto infoApartmentDto = rentApartmentMapper.apartmentDtoToApartmentEntity(apartment.getAddress(), apartment);
                listApartmentDto.add(infoApartmentDto);
            }
            return listApartmentDto;
        }
        return new ArrayList<>();
    }

    private List<InfoApartmentRoomEntity> showApartmentsByUserLocation(RequestApartmentInfoDto apartmentInfoDto) {
        /**
         * в отдельный метод
         */
        if (apartmentInfoDto.getLatitude().isEmpty() || apartmentInfoDto.getLongitude().isEmpty()) {
            return new ArrayList<>();
        }

        String cityByUserLocation = showCityByUserLocation(apartmentInfoDto.getLongitude(), apartmentInfoDto.getLatitude());
        if (!cityByUserLocation.isEmpty()) {
            return addressRepository.getApartmentsByCity(cityByUserLocation).stream()
                    .map(InfoAddresEntity::getApartment)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private String showCityByUserLocation(String longitude, String latitude) {
        IntegrationEntity geo = integratorInfoRepository.findById("GEO").get();
        String urlGeo = geo.getUrl();
        String key_value = Base64Service.decodeRentApp(geo.getKeyValue());
        String jsonUserLocationInfo = integrationService.getJsonUserLocationInfo(urlGeo, longitude, latitude, key_value);

        return getCityUserByLocation(jsonUserLocationInfo);
    }

    private String getCityUserByLocation(String jsonUserLocationInfo) {
        try {
            /**
             * посмотреть что там не только city передается
             */
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonUserLocationInfo);
            JsonNode cityNode = rootNode.get("results").get(0).get("components").get("city");
            if (!isNull(cityNode)) {
                return cityNode.asText();
            }
            return "";
        } catch (JsonProcessingException exception) {
            return "";
        }
    }
}
