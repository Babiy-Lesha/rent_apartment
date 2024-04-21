package org.example.rent_apartment.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.rent_apartment.model.entity.UserInfoEntity;
import org.example.rent_apartment.repository.TokenSaveRepository;
import org.example.rent_apartment.repository.UserInfoRepository;
import org.example.rent_apartment.service.impl.Base64Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@EnableScheduling
@RequiredArgsConstructor
@Service
public class TokenValidScheduler {

    private static final Logger log = LoggerFactory.getLogger(TokenValidScheduler.class);

    private final UserInfoRepository userInfoRepository;
    private final TokenSaveRepository tokenSaveRepository;
    private final static int DAYS_SAVE_TOKEN = 29;

    @Scheduled(fixedRate = 120000)
    @Transactional
    public void checkValidTokens() {
        log.info("планировщик по токену начал работать: " + LocalDateTime.now());

        List<UserInfoEntity> userList = userInfoRepository.getUserInfoEntitiesByTokenNotNull();

        if (userList.isEmpty()) {
            log.info("нет активных сессий пользователей");
        }

        for (UserInfoEntity user : userList) {
            if (!compareDateToken(timeToken(user.getToken()))) {
                String tokenUser = Base64Service.decodeRentApp(user.getToken());
                tokenSaveRepository.deleteByTokenDecode(tokenUser);

                user.setToken(null);
                userInfoRepository.save(user);
                log.info("Токен пользователя: " + user.getLoginUser() + "истек и был удален ");
            }
        }
        log.info("планировщик по токену закончил работать: " + LocalDateTime.now());
    }

    private LocalDateTime timeToken(String encodeToken) {
        String token = Base64Service.decodeRentApp(encodeToken);
        int index = token.indexOf("|") + 1;
        String dateTokenFormatString = token.substring(index);

        return LocalDateTime.parse(dateTokenFormatString);
    }

    private boolean compareDateToken(LocalDateTime timeToken) {
        LocalDateTime expirationTime = timeToken.plusDays(DAYS_SAVE_TOKEN);
        return expirationTime.isAfter(LocalDateTime.now());
    }

}
