package com.example.scheduler.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "FIRSTNAME")
    private String firstName;
    @Column(name = "LASTNAME")
    private String lastName;
    @Column(name = "EMAIL")
    private String email;
}
