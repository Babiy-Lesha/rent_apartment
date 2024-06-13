package org.example.rent_apartment.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booking_apartment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingApartmentEntity {

    @Id
    @SequenceGenerator(name = "booking_apartmentSequence", sequenceName = "booking_apartment_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_apartmentSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_apartment")
    private Long idApartment;

    @Column(name = "name_apartment")
    private String NameApartment;

    @Column(name = "start_date_booking")
    private String startDateBooking;

    @Column(name = "end_date_booking")
    private String endDateBooking;

    @Column(name = "price_per_day")
    private Integer pricePerDay;

}
