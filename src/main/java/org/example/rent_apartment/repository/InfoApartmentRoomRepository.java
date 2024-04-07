package org.example.rent_apartment.repository;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.example.rent_apartment.model.entity.InfoApartmentRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoApartmentRoomRepository extends JpaRepository<InfoApartmentRoomEntity, Long> {

    @Transactional
    void deleteById(@NonNull Long id);
}
