package com.shop.ecommerce.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMeDto {
    private Long id;
    private String username;
    private String phoneNumber;
    private String name;
    private String surname;
    private String email;
}
