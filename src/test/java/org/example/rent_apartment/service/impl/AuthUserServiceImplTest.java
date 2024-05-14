package org.example.rent_apartment.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.rent_apartment.mapper.RentApartmentMapper;
import org.example.rent_apartment.model.dto.user_dto.UserAuthDto;
import org.example.rent_apartment.model.dto.user_dto.UserRegistrationDto;
import org.example.rent_apartment.model.entity.UserInfoEntity;
import org.example.rent_apartment.repository.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUserServiceImplTest {

    @InjectMocks
    private AuthUserServiceImpl authUserService;
    @Mock
    private UserInfoRepository userInfoRepository;
    @Mock
    private RentApartmentMapper rentApartmentMapper;
    @Mock
    private EntityManager entityManager;
    @Mock
    private CriteriaBuilder criteriaBuilder;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
    }


    @Test
    @DisplayName("Тест регистрации нового пользователя")
    void registration() {

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("Lesha", "123", "LeshaLogin", "Yekaterinburg");
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        when(rentApartmentMapper.userDtoToUserEntity(userRegistrationDto)).thenReturn(userInfoEntity);

        authUserService.registration(userRegistrationDto);

        verify(rentApartmentMapper).userDtoToUserEntity(userRegistrationDto);
        verify(userInfoRepository, times(1)).save(userInfoEntity);
    }

    @Test
    void auth() {
        String loginUser = "testUser";
        String password = "testPassword";

        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setLoginUser(loginUser);
        userInfoEntity.setPasswordUser(password);

        UserAuthDto userAuthDto = new UserAuthDto();
        userAuthDto.setLoginUser(loginUser);
        userAuthDto.setPasswordUser(password);
        when(userInfoRepository.getUserByLoginUser(loginUser)).thenReturn(userInfoEntity);

        authUserService.auth(userAuthDto);

        verify(userInfoRepository).getUserByLoginUser(loginUser);
    }

    @Test
    void authAdmin() {
    }

    @Test
    void logAuth() {
    }

    @Test
    void checkToken() {
    }
}