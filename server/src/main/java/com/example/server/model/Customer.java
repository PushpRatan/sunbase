package com.example.server.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    String street;

    @Column(nullable = false)
    String Uid;

    @Column(nullable = false)
    String address;

    @Column(nullable = false)
    String city;

    @Column(nullable = false)
    String state;

    @Column(nullable = false, unique = true)
    String phone;

    @Column(nullable = false, unique = true)
    String email;

    @CreationTimestamp
    Date joinedOn;

}
