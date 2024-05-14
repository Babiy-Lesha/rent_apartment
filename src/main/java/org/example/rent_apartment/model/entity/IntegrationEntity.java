package org.example.rent_apartment.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "integration_info")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IntegrationEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "url")
    private String url;

    @Column(name = "key_value")
    private String keyValue;
}
