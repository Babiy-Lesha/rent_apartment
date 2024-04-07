package org.example.rent_apartment.repository;

import org.example.rent_apartment.model.entity.PhotoApartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoApartmentRepository extends JpaRepository<PhotoApartmentEntity, Long> {
}
