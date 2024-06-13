package org.example.rent_apartment.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address_info")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InfoAddresEntity {

    @Id
    @SequenceGenerator(name = "address_infoSequence", sequenceName = "address_info_sequence", allocationSize = 1, initialValue = 8)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_infoSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "name_apartment")
    private String nameApartment;

    @Column(name = "city_apartment")
    private String city;

    @Column(name = "street_apartment")
    private String streetApartment;

    @Column(name = "booking_check")
    private boolean bookingCheck;

    //если я удаляю адресс то и апартмен тоже удалится
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    private InfoApartmentRoomEntity apartment;

}
