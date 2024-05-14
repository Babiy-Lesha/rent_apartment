package org.example.rent_apartment.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_info")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoEntity {

    @Id
    @SequenceGenerator(name = "user_infoSequence", sequenceName = "user_info_sequence", allocationSize = 1, initialValue = 9)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_infoSequence")
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "date_registration")
    private LocalDateTime dateRegistration;

    @Column(name = "parent_city")
    private String parentCity;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "password_user")
    private String passwordUser;

    @Column(name = "login_user")
    private String loginUser;

    @Column(name = "token")
    private String token;

}
