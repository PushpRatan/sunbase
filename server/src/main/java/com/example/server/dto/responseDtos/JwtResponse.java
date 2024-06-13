package com.example.server.dto.responseDtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class JwtResponse {

    String jwtToken;

    String username;

}
