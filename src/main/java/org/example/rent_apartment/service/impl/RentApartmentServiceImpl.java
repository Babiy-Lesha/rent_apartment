package org.example.rent_apartment.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.rent_apartment.exception.ApartmentException;
import org.example.rent_apartment.mapper.RentApartmentMapper;
import org.example.rent_apartment.model.dto.integration_dto.GeoResponseDto;
import org.example.rent_apartment.model.dto.apartment_dto.RequestApartmentInfoDto;
import org.example.rent_apartment.model.dto.RequestBookingInfoDto;
import org.example.rent_apartment.model.dto.apartment_dto.InfoApartmentDto;
import org.example.rent_apartment.model.entity.InfoAddresEntity;
import org.example.rent_apartment.model.entity.InfoApartmentRoomEntity;
import org.example.rent_apartment.model.entity.PhotoApartmentEntity;
import org.example.rent_apartment.repository.InfoAddressRepository;
import org.example.rent_apartment.repository.InfoApartmentRoomRepository;
import org.example.rent_apartment.repository.PhotoApartmentRepository;
import org.example.rent_apartment.service.IntegrationService;
import org.example.rent_apartment.service.MailSenderService;
import org.example.rent_apartment.service.RentApartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
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
    private final MailSenderService mailSenderService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    public static final String TOPIC_NAME = "topickName";

    @Override
    public String addApartment(String token, InfoApartmentDto infoApartmentDto) {

        List<InfoAddresEntity> apartments = addressRepository.getApartmentByName(infoApartmentDto.getNameApartment());
        InfoAddresEntity apartmentName = apartments.isEmpty() ? null : apartments.get(0);
        if (!isNull(apartmentName)) {
            log.error("RentApartmentServiceImpl: addApartment = " + APARTMENT_EXISTS);
            throw new ApartmentException(APARTMENT_EXISTS, NOT_UNIQ_ADDRESS);
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

        List<InfoAddresEntity> apartments = addressRepository.getApartmentByName(apartmentName);
        InfoAddresEntity apartment = apartments.isEmpty() ? null : apartments.get(0);
        if (isNull(apartment)) {
            log.error("RentApartmentServiceImpl: addPhotoApp = " + APARTMENT_NO_EXISTS);
            throw new ApartmentException(APARTMENT_NO_EXISTS, NOT_UNIQ_ADDRESS);
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
        if (!isNull(apartmentInfoDto.getApartmentId())) {
            listApartments = apartmentRoomRepository.findById(apartmentInfoDto.getApartmentId())
                    .map(Collections::singletonList)
                    .orElseGet(() -> showApartmentsByUserLocation(apartmentInfoDto));
        } else {
            listApartments = showApartmentsByUserLocation(apartmentInfoDto);
        }

        /**
         * поменять актуальный пароль
         */
        List<InfoApartmentDto> infoApartmentDtoList = rentApartmentMapperList(listApartments);
//        mailSenderService.sendEmail(conversionListInfoApartmentToMessage(infoApartmentDtoList),
//                "showApartments", apartmentInfoDto.getEmailAddress());

        return infoApartmentDtoList;
    }

    @Override
    @Transactional
    public String bookingApartment(RequestBookingInfoDto requestBookingInfoDto) {
        InfoAddresEntity addressApartment = addressRepository.getReferenceById(requestBookingInfoDto.getApartmentId());
        if (isNull(addressApartment)) {
            log.error("RentApartmentServiceImpl: bookingApartment = " + APARTMENT_NO_EXISTS);
            throw new ApartmentException(APARTMENT_NO_EXISTS, NOT_UNIQ_ADDRESS);
        }

        if (addressApartment.isBookingCheck()) {
            log.error("RentApartmentServiceImpl: bookingApartment = " + "апартамент забронирован");
            throw new ApartmentException("апартамент забронирован", 603);
        }

        log.info("RentApartmentServiceImpl: bookingApartment = " + "апартамент с id " + requestBookingInfoDto.getApartmentId() + "забронирован");
        addressApartment.setBookingCheck(true);
        addressRepository.save(addressApartment);

        Integer discountBooking = 0;
        try {
            discountBooking = integrationService.getDiscountBooking(requestBookingInfoDto);
            kafkaTemplate.send(TOPIC_NAME, requestBookingInfoDto.toString());

        } catch (RestClientException e) {//ошибка что сервер не доступен
            //проверить можно ли в топик отправлять обьекты
            log.info(e.toString());
            log.info("RentApartmentServiceImpl: bookingApartment = " + "отправили в kafka " + requestBookingInfoDto);
            kafkaTemplate.send(TOPIC_NAME, requestBookingInfoDto.toString());
            return "Скидка ушла в кафку)))";
        }

        return "Скидка составляет: " + discountBooking;
    }

    private String conversionListInfoApartmentToMessage(List<InfoApartmentDto> dtoList) {
        StringBuilder builder = new StringBuilder();
        for (InfoApartmentDto apartmentDto : dtoList) {
            builder.append(apartmentDto.toString());
        }
        return builder.toString();
    }

    private List<InfoApartmentDto> rentApartmentMapperList(List<InfoApartmentRoomEntity> listApartments) {
        List<InfoApartmentDto> listApartmentDto = new ArrayList<>();
        for (InfoApartmentRoomEntity apartment : listApartments) {
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

    private void checkInputLongitudeAndLatitude(RequestApartmentInfoDto apartmentInfoDto) {
        if (isNull(apartmentInfoDto.getLatitude()) || isNull(apartmentInfoDto.getLongitude()) || apartmentInfoDto.getLatitude().isEmpty() || apartmentInfoDto.getLongitude().isEmpty()) {
            log.error("RentApartmentServiceImpl: checkInputLongitudeAndLatitude = " + NOT_INPUT_LOCATION);
            throw new ApartmentException(NOT_INPUT_LOCATION, BAD_INPUT_LOCATION);
        }
    }

    private String showCityByUserLocation(String longitude, String latitude) {
        System.out.println("showCityByUserLocation - Longitude: " + longitude + ", Latitude: " + latitude); // Debugging line
        GeoResponseDto jsonUserLocationInfo = integrationService.getJsonUserLocationInfo(longitude, latitude);
        System.out.println("GeoResponseDto: " + jsonUserLocationInfo); // Debugging line
        return getLocation(jsonUserLocationInfo);
    }

    private String getLocation(GeoResponseDto geoResponseDto) {
        if (geoResponseDto == null) {
            return "";
        }
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
