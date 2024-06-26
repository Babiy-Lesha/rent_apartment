package org.example.rent_apartment.repository;

import jakarta.transaction.Transactional;
import org.example.rent_apartment.model.entity.InfoAddresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfoAddressRepository extends JpaRepository<InfoAddresEntity, Long> {

    InfoAddresEntity getInfoAddresEntityById(Long id);

    @Query("SELECT app FROM InfoAddresEntity app  WHERE app.nameApartment = :nameApartment")
    List<InfoAddresEntity> getApartmentByName(@Param("nameApartment") String nameApartment);

    @Query("SELECT app FROM InfoAddresEntity app WHERE app.city = :city")
    List<InfoAddresEntity> getApartmentsByCity(@Param("city") String city);

    @Transactional
    void deleteByNameApartment(String nameApartment);

}
