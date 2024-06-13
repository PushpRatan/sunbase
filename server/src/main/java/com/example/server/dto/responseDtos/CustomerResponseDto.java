package com.example.server.dto.responseDtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CustomerResponseDto {
    String Uid;

    String message;

    Date joinedOn;

    String firstName;

    String lastName;

    String street;

    String address;

    String city;

    String state;

    String email;

    String phone;

}
