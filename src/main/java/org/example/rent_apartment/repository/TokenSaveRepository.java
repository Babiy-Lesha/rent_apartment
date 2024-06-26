package org.example.rent_apartment.repository;

import org.example.rent_apartment.model.entity.token_save_entity.TokenSaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenSaveRepository extends JpaRepository<TokenSaveEntity, Long> {

    void deleteByTokenDecode(String token);
}
