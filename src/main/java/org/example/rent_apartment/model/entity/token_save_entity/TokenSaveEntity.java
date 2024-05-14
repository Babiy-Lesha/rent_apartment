package org.example.rent_apartment.model.entity.token_save_entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "token_save")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenSaveEntity {

    @Id
    @SequenceGenerator(name = "token_saveSequence", sequenceName = "token_save_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_saveSequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "token_encode")
    String tokenEncode;

    @Column(name = "token_decode")
    private String tokenDecode;
}
