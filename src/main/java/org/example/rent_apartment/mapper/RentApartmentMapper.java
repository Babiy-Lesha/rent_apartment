package org.example.rent_apartment.mapper;

import org.example.rent_apartment.model.dto.apartment_dto.InfoApartmentDto;
import org.example.rent_apartment.model.dto.photo_dto.PhotoDto;
import org.example.rent_apartment.model.dto.user_dto.UserRegistrationDto;
import org.example.rent_apartment.model.entity.InfoAddresEntity;
import org.example.rent_apartment.model.entity.InfoApartmentRoomEntity;
import org.example.rent_apartment.model.entity.PhotoApartmentEntity;
import org.example.rent_apartment.model.entity.UserInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RentApartmentMapper {

    @Mapping(target = "city", source = "cityApartment")
    InfoAddresEntity apartmentDtoToApartmentEntity(InfoApartmentDto apartment);

    InfoApartmentRoomEntity roomDtoToRoomEntity(InfoApartmentDto apartment);

    UserInfoEntity userDtoToUserEntity(UserRegistrationDto userDto);

    PhotoApartmentEntity photoDtoToPhotoEntity(PhotoDto photoDto);

    @Mapping(target = "cityApartment", source = "infoAddresEntity.city")
    InfoApartmentDto apartmentDtoToApartmentEntity(InfoAddresEntity infoAddresEntity, InfoApartmentRoomEntity infoApartmentRoomEntity);

}
