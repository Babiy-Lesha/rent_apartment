package org.example.rent_apartment.repository;

import org.example.rent_apartment.model.entity.UserInfoEntity;
import org.hibernate.annotations.DialectOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {

    UserInfoEntity getUserInfoEntityByNickName(String nickName);
    UserInfoEntity getUserInfoEntityByLoginUser(String loginUser);
    UserInfoEntity getUserInfoEntityByToken(String token);


    //нативный запрос (проблема n + 1) замена метода getUserInfoEntityByNickName
    // (запрос напрямую на базе данных, используя SQL запрос)
    @Query(nativeQuery = true, value = "SELECT * FROM user_info WHERE nick_name =  :nickName")
    UserInfoEntity getUserByNickName(String nickName);

    //JPQL запрос (без проблемы n + 1) замена метода getUserInfoEntityByLoginUser
    //работает на уровне сущностей Java, а не непосредственно на базе данных
    @Query(value = "SELECT u FROM UserInfoEntity u WHERE u.loginUser = :loginUser")
    UserInfoEntity getUserByLoginUser(String loginUser);

    @Query(value = "SELECT u FROM UserInfoEntity u WHERE u.token = :token")
    UserInfoEntity getUserByToken(String token);

    List<UserInfoEntity> getUserInfoEntitiesByTokenNotNull();



}
