package org.example.rent_apartment.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "photo_app")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhotoApartmentEntity {

    @Id
    @SequenceGenerator(name="photo_appSequence", sequenceName="photo_app_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="photo_appSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "photo_name")
    private String photoName;

    @CreationTimestamp
    @Column(name = "date_add_photo")
    private LocalDateTime dateAddPhoto;

    @Column(name = "encode_photo", columnDefinition = "TEXT")
    private String encodePhoto;
}
