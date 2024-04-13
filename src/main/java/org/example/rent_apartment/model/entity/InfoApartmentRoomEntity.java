package org.example.rent_apartment.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "apartment_room_info")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InfoApartmentRoomEntity {

    @Id
    @SequenceGenerator(name="apartment_room_infoSequence", sequenceName="apartment_room_info_sequence", allocationSize = 1, initialValue = 8)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="apartment_room_infoSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "rating_room")
    private String ratingRoom;

    @Column(name = "count_room")
    private Integer countRoom;

    @Column(name = "price_per_day")
    private Integer pricePerDay;

    @Column(name = "have_shower_room")
    private Boolean haveShowerRoom;

    /**
     * mappedBy делает связь Bi-directional (то есть и адресс знает об апартаменте, и апартамент знает об адресе
     *
     * т.к нет cascade = CascadeType.ALL, то при удаление апартамента, будет вылетать exception,
     * но если вдруг нам все таки захочется удалить апартамент, то вначале надо будет разорвать связь, т.е для обьекта
     * InfoAddresEntity.setApartment(null) и тогда в колонке apartment_id будет значение null и можно удлаить апартамент
     *
     * так же можно добавить cascade = CascadeType.ALL (@OneToOne(mappedBy = "apartment", CascadeType.ALL) и тогда будут удалятся вместе
     */
    @OneToOne(mappedBy = "apartment")
    private InfoAddresEntity address;

}
