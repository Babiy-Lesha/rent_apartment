package org.example.rent_apartment.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.rent_apartment.exception.ApartmentException;
import org.example.rent_apartment.exception.AuthException;
import org.example.rent_apartment.mapper.RentApartmentMapper;
import org.example.rent_apartment.model.dto.user_dto.UserAuthDto;
import org.example.rent_apartment.model.dto.user_dto.UserRegistrationDto;
import org.example.rent_apartment.model.entity.UserInfoEntity;
import org.example.rent_apartment.model.entity.token_save_entity.TokenSaveEntity;
import org.example.rent_apartment.repository.TokenSaveRepository;
import org.example.rent_apartment.repository.UserInfoRepository;
import org.example.rent_apartment.service.AuthUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;
import static org.example.rent_apartment.app_const.AppConst.*;
import static org.example.rent_apartment.exception.response_status.CustomStatusResponse.*;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {

    private static final Logger log = LoggerFactory.getLogger(AuthUserServiceImpl.class);

    private final UserInfoRepository userInfoRepository;
    private final TokenSaveRepository tokenSaveRepository;
    private final RentApartmentMapper rentApartmentMapper;
    private final EntityManager entityManager;
//    private final UserMapper userMapper;


    private UserInfoEntity userByNickName(String nickName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserInfoEntity> query = criteriaBuilder.createQuery(UserInfoEntity.class);
        Root<UserInfoEntity> root = query.from(UserInfoEntity.class);

        query.select(root).where(criteriaBuilder.equal(root.get("nickName"), nickName));

        List<UserInfoEntity> resultList = entityManager.createQuery(query).getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public String registration(UserRegistrationDto userRegistrationDto) {
//        UserInfoEntity userByNickName = userInfoRepository.getUserByNickName(userRegistrationDto.getNickName());
        UserInfoEntity userByNickName = userByNickName(userRegistrationDto.getNickName());
        if (!isNull(userByNickName)) {
            log.error("AuthUserServiceImpl: registration = " + NICKNAME_EXISTS);
            throw new AuthException(NICKNAME_EXISTS, BAD_REG);
        }

        UserInfoEntity userByLogin = userInfoRepository.getUserByLoginUser(userRegistrationDto.getLoginUser());
        if(!isNull(userByLogin)) {
            log.error("AuthUserServiceImpl: registration = " + LOGIN_EXISTS);
            throw new AuthException(LOGIN_EXISTS, BAD_REG);
        }

//        UserInfoEntity userInfoEntity = userMapper.userDtoToUserEntity(userRegistrationDto);
        UserInfoEntity userInfoEntity = rentApartmentMapper.userDtoToUserEntity(userRegistrationDto);
        userInfoRepository.save(userInfoEntity);
        log.info("AuthUserServiceImpl: registration пользователь добавлен в базу данных");
        return REGISTRATION_SUCCESS;
    }

    @Override
    public String auth(UserAuthDto userAuthDto) {
        UserInfoEntity user = getUserByLogin(userAuthDto.getLoginUser());
        String token = generateAndSaveToken(user, userAuthDto.getPasswordUser());
        return AUTH_SUCCESS + " токен: " + token;
    }

    @Override
    public String authAdmin(UserAuthDto userAuthDto) {
        UserInfoEntity user = getUserByLogin(userAuthDto.getLoginUser());
        String tokenAdmin = generateAndSaveAdminToken(user, userAuthDto.getPasswordUser());
        return "Для администратора " + AUTH_SUCCESS + " токен: " + tokenAdmin;
    }

    @Override
    public String logAuth(String token) {
        UserInfoEntity user = userInfoRepository.getUserByToken(Base64Service.encodeRentApp(token));
        if (isNull(user)) {
            log.error("AuthUserServiceImpl: logAuth = " + USER_NOT_ONLINE);
            throw new AuthException(USER_NOT_ONLINE, BAD_AUTH_LOG);
        }

        tokenSaveRepository.deleteByTokenDecode(token);

        user.setToken(null);

        userInfoRepository.save(user);
        return LOG_AUTH_SUCCESS;
    }

    public void checkToken (String token) {
        UserInfoEntity user = userInfoRepository.getUserByToken(Base64Service.encodeRentApp(token));
        if (isNull(user)) {
            log.error("AuthUserServiceImpl: checkToken = " + USER_NOT_ONLINE);
            throw new ApartmentException(USER_NOT_ONLINE, NOT_UNIQ_REG);
        }
    }

    private UserInfoEntity getUserByLogin(String loginUser) {
        UserInfoEntity user = userInfoRepository.getUserByLoginUser(loginUser);
        if (isNull(user)) {
            log.error("AuthUserServiceImpl: UserInfoEntity = " + LOGIN_NO_EXISTS);
            throw new AuthException(LOGIN_NO_EXISTS, BAD_REG);
        }
        return user;
    }

    private String generateAndSaveToken(UserInfoEntity user, String password) {
        checkPassword(user.getPasswordUser(), password);
        String token = generateNewToken();
        String encodeToken = Base64Service.encodeRentApp(token);
        saveToken(user, token, encodeToken);
        return token;
    }

    private String generateAndSaveAdminToken(UserInfoEntity user, String password) {
        checkPassword(user.getPasswordUser(), password);
        String tokenAdmin = "admin-" + generateNewToken();
        String encodeTokenAdmin = Base64Service.encodeRentApp(tokenAdmin);
        saveToken(user, tokenAdmin, encodeTokenAdmin);
        return tokenAdmin;
    }

    private void checkPassword(String userPassword, String password) {
        if (!userPassword.equals(password)) {
            log.error("AuthUserServiceImpl: checkPassword = " + INCORRECT_PASSWORD);
            throw new AuthException(INCORRECT_PASSWORD, BAD_PASS_LOG);
        }
    }

    private void saveToken(UserInfoEntity user, String token, String encodeToken) {
        TokenSaveEntity tokenSaveEntity = createTokenSaveEntity(token, encodeToken);
        tokenSaveRepository.save(tokenSaveEntity);
        user.setToken(encodeToken);
        userInfoRepository.save(user);
    }

    private TokenSaveEntity createTokenSaveEntity (String token, String encodeToken) {
        TokenSaveEntity tokenSaveEntity = new TokenSaveEntity();
        tokenSaveEntity.setTokenEncode(encodeToken);
        tokenSaveEntity.setTokenDecode(token);
        return tokenSaveEntity;
    }

    private String generateNewToken() {
        return UUID.randomUUID().toString() + "|" + LocalDateTime.now().plusDays(1L);
    }



    //    @Override
//    public String auth(UserAuthDto userAuthDto) {
//        UserInfoEntity user = userInfoRepository.getUserByLoginUser(userAuthDto.getLoginUser());
//        if(isNull(user)) {
//            throw new AuthException(LOGIN_NO_EXISTS, BAD_REG);
//        }
//
//        if (!(user.getPasswordUser().equals(userAuthDto.getPasswordUser()))) {
//            throw new AuthException(INCORRECT_PASSWORD, BAD_PASS_LOG);
//        }
//
//        String token = generateNewToken();
//        String encodeToken = Base64Service.encodeRentApp(token);
//
//        TokenSaveEntity tokenSaveEntity = createTokenSaveEntity(token, encodeToken);
//        tokenSaveRepository.save(tokenSaveEntity);
//
//        user.setToken(encodeToken);
//        userInfoRepository.save(user);
//
//        return AUTH_SUCCESS + " токен: " + token;
//    }
//
//    @Override
//    public String authAdmin(UserAuthDto userAuthDto) {
//        UserInfoEntity user = userInfoRepository.getUserByLoginUser(userAuthDto.getLoginUser());
//        if(isNull(user)) {
//            throw new AuthException(LOGIN_NO_EXISTS, BAD_REG);
//        }
//
//        if (!(user.getPasswordUser().equals(userAuthDto.getPasswordUser()))) {
//            throw new AuthException(INCORRECT_PASSWORD, BAD_PASS_LOG);
//        }
//
//        String tokenAdmin = "admin-" + generateNewToken();
//        String encodeTokenAdmin = Base64Service.encodeRentApp(tokenAdmin);
//
//        TokenSaveEntity tokenSaveEntity = createTokenSaveEntity(tokenAdmin, encodeTokenAdmin);
//        tokenSaveRepository.save(tokenSaveEntity);
//
//        user.setToken(encodeTokenAdmin);
//        userInfoRepository.save(user);
//
//        return "Для администратора " + AUTH_SUCCESS + " токен: " + tokenAdmin;
//    }

}
